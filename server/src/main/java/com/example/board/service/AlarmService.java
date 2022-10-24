package com.example.board.service;

import com.example.board.domain.Alarm;
import com.example.board.domain.AlarmArgs;
import com.example.board.domain.AlarmType;
import com.example.board.domain.User;
import com.example.board.dto.AlarmDto;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
import com.example.board.repository.AlarmRepository;
import com.example.board.repository.EmitterRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final EmitterRepository emitterRepository;
    private final static Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final static String ALARM_NAME = "alarm";

    public void send(Long alarmId, Long userId) {
        emitterRepository.get(userId).ifPresentOrElse(sseEmitter -> {
            try {
                sseEmitter.send(SseEmitter.event()
                        .id(alarmId.toString()).name(ALARM_NAME).data("new alarm"));

            } catch (IOException e) {
                emitterRepository.delete(userId);
                throw new BoardApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
            }
        }, () -> log.info("No emitter founded"));
    }

    public SseEmitter connectAlarm(Long userId) {
        SseEmitter sseEmitter = new SseEmitter(DEFAULT_TIMEOUT);
        emitterRepository.save(userId, sseEmitter);
        sseEmitter.onCompletion(() -> emitterRepository.delete(userId));
        sseEmitter.onTimeout(() -> emitterRepository.delete(userId));
        try {
            sseEmitter.send(SseEmitter.event()
                    .id("")
                    .name(ALARM_NAME)
                    .data("connect completed"));
        } catch (IOException e) {
            throw new BoardApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
        return sseEmitter;
    }

    @Transactional
    public AlarmDto create(String fromUser, String username, Long postId) {
        if (fromUser.equals(username)) { return null; }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND));
        Alarm alarm = Alarm.of(user, AlarmType.COMMENT,
                AlarmArgs.builder()
                        .fromUsername(fromUser)
                        .postId(postId)
                        .build());
        alarmRepository.save(alarm);
        return AlarmDto.fromAlarm(alarm);
    }

    public List<AlarmDto> alarmList(String username) {
        return alarmRepository.findByUsername(username).orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND))
                .stream().map(AlarmDto::fromAlarm)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAlarm(Long alarmId, Long userId) {alarmRepository.deleteById(alarmId);}
}
