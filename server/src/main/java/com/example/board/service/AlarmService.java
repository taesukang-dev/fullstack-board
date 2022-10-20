package com.example.board.service;

import com.example.board.domain.Alarm;
import com.example.board.domain.AlarmArgs;
import com.example.board.domain.AlarmType;
import com.example.board.domain.User;
import com.example.board.dto.AlarmDto;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
import com.example.board.repository.AlarmRepository;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;

    @Transactional
    public void create(String fromUser, String username, Long postId) {
        if (fromUser.equals(username)) {
            return ;
        }
        User user = userRepository.findByUsername(username).orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND));
        alarmRepository.save(
                Alarm.of(user, AlarmType.COMMENT,
                        AlarmArgs.builder()
                                .fromUsername(fromUser)
                                .postId(postId)
                                .build())
        );
    }

    public List<AlarmDto> alarmList(String username) {
        return alarmRepository.findByUsername(username).orElseThrow(() -> new BoardApplicationException(ErrorCode.USER_NOT_FOUND))
                .stream().map(AlarmDto::fromAlarm)
                .collect(Collectors.toList());
    }

    @Transactional
    public void deleteAlarm(Long alarmId) {
        alarmRepository.deleteById(alarmId);
    }
}
