package com.example.board.service;

import com.example.board.dto.AlarmDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@SpringBootTest
class AlarmServiceTest {
    @Autowired AlarmService alarmService;

    @Test
    void 알람생성_후_조회() throws Exception {
        // given
        alarmService.create("test", "test111", 177L);
        // when
        // then
        List<AlarmDto> test = alarmService.alarmList("test111");
        Assertions.assertThat(test.size()).isEqualTo(1);
        Assertions.assertThat(test.get(0).getArgs().getFromUsername()).isEqualTo("test");
    }



}