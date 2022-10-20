package com.example.board.dto;

import com.example.board.domain.Alarm;
import com.example.board.domain.AlarmArgs;
import com.example.board.domain.AlarmType;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmDto {
    private Long id;
    private AlarmType alarmType;
    private AlarmArgs args;
    private Timestamp registeredAt;

    public static AlarmDto fromAlarm(Alarm alarm) {
        return new AlarmDto(alarm.getId(), alarm.getAlarmType(), alarm.getAlarmArgs(), alarm.getRegisterAt());
    }
}
