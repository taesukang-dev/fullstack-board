package com.example.board.dto.response;

import com.example.board.domain.AlarmArgs;
import com.example.board.dto.AlarmDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AlarmResponse {
    private Long id;
    private String alarmType;
    private AlarmArgs args;
    private String registeredAt;

    public static AlarmResponse fromAlarmDto(AlarmDto alarmDto) {
        return new AlarmResponse(alarmDto.getId(), alarmDto.getAlarmType().getName(), alarmDto.getArgs(), alarmDto.getRegisteredAt().toLocalDateTime().toString());
    }


}
