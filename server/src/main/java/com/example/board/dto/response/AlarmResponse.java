package com.example.board.dto.response;

import com.example.board.domain.AlarmArgs;
import com.example.board.domain.AlarmType;
import com.example.board.dto.AlarmDto;
import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmResponse {
    private Long id;
    private AlarmType alarmType;
    private AlarmArgs args;
    private Timestamp registeredAt;

    public static AlarmResponse fromAlarmDto(AlarmDto alarmDto) {
        return new AlarmResponse(alarmDto.getId(), alarmDto.getAlarmType(), alarmDto.getArgs(), alarmDto.getRegisteredAt());
    }


}
