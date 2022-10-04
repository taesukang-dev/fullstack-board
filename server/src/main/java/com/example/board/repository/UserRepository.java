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

    public UserDto save(User user) {
        em.persist(user);
        return UserDto.fromUser(user);
    }

    public Optional<UserDto> findByUsername(String username) {
        try {
            User user = em.createQuery("select u from User u where u.username = :username", User.class)
                    .setParameter("username", username).getSingleResult();
            return Optional.of(UserDto.fromUser(user));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

}
