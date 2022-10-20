package com.example.board.repository;

import com.example.board.domain.Alarm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class AlarmRepository {
    private final EntityManager em;

    public void save(Alarm alarm) {
        em.persist(alarm);
    }

    public Optional<List<Alarm>> findByUsername(String username) {
        return Optional.ofNullable(em.createQuery("select a from Alarm a " +
                        " join fetch a.receivedUser" +
                        " where a.receivedUser.username=:username", Alarm.class)
                .setParameter("username", username)
                .getResultList());
    }

    public void deleteById(Long alarmId) {
        Alarm alarm = em.find(Alarm.class, alarmId);
        em.remove(alarm);
    }
}
