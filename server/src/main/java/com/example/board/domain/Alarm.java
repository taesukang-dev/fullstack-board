package com.example.board.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.Instant;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "deleted_at is null")
@SQLDelete(sql = "UPDATE Alarm SET DELETED_AT = NOW() where id = ?")
@Table(name = "\"Alarm\"", indexes = {
        @Index(name = "user_id_idx", columnList = "user_id")
})
@Entity
public class Alarm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User receivedUser;

    @Enumerated(EnumType.STRING)
    private AlarmType alarmType;

    @Embedded
    private AlarmArgs alarmArgs;

    @Column(name = "registered_at")
    private Timestamp registerAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    @Column(name = "deleted_at")
    private Timestamp deletedAt;

    public Alarm(User receivedUser, AlarmType alarmType, AlarmArgs alarmArgs) {
        this.receivedUser = receivedUser;
        this.alarmType = alarmType;
        this.alarmArgs = alarmArgs;
    }

    public static Alarm of(User user, AlarmType alarmType, AlarmArgs alarmArgs) {
        return new Alarm(user, alarmType, alarmArgs);
    }

    @PrePersist void registeredAt() { this.registerAt = Timestamp.from(Instant.now()); }
    @PreUpdate void updatedAt() { this.updatedAt = Timestamp.from(Instant.now()); }
}
