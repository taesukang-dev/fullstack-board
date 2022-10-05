package com.example.board.repository;

import com.example.board.domain.Comment;
import com.example.board.domain.User;
import com.example.board.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public User save(User user) {
        em.persist(user);
        return user;
    }

    public Optional<User> findByUsername(String username) {
        try {
            User user = em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult();
            return Optional.of(user);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

}
