package com.example.board.domain;

import lombok.*;

import javax.persistence.Embeddable;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Embeddable
public class AlarmArgs {
    private Long postId;
    private String fromUsername;
}
