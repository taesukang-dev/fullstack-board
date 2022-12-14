package com.example.board.controller;

import com.example.board.dto.request.AlarmRequest;
import com.example.board.dto.response.AlarmResponse;
import com.example.board.dto.response.Response;
import com.example.board.dto.security.UserPrincipal;
import com.example.board.repository.EmitterRepository;
import com.example.board.service.AlarmService;
import com.example.board.service.RedisAlarmSubscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/alarm")
public class AlarmController {

    private final AlarmService alarmService;
    private final RedisMessageListenerContainer redisMessageListener;
    private final RedisAlarmSubscriber redisAlarmSubscriber;
    private final EmitterRepository emitterRepository;

    @GetMapping("/subscribe")
    public SseEmitter subscribe(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // TODO : Util로 빼기
        ChannelTopic topic = new ChannelTopic("Emitter:UID" + userPrincipal.getId());
        redisMessageListener.addMessageListener(redisAlarmSubscriber, topic);
        emitterRepository.putTopic(userPrincipal.getId(), topic);
        return alarmService.connectAlarm(userPrincipal.getId());
    }

    @PostMapping
    public void create(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @RequestBody AlarmRequest alarmRequest) {
        alarmService.create(userPrincipal.getUsername(), alarmRequest.getReceivedUsername(), alarmRequest.getPostId());
    }

    @GetMapping
    public Response<List<AlarmResponse>> alarmList(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        return Response.success(alarmService.alarmList(userPrincipal.getUsername())
                .stream().map(AlarmResponse::fromAlarmDto).collect(Collectors.toList()));
    }

    @DeleteMapping("/{alarmId}")
    public Response<Void> deleteAlarm(
            @AuthenticationPrincipal UserPrincipal userPrincipal,
            @PathVariable Long alarmId) {
        alarmService.deleteAlarm(alarmId, userPrincipal.getId());
        return Response.success();
    }
}
