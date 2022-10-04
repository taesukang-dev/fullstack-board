package com.example.board.repository;

import com.example.board.domain.Comment;
import com.example.board.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@RequiredArgsConstructor
@Repository
public class UserRepository {
    private final EntityManager em;

    public void save(Comment comment) {
        em.persist(comment);
    }

    public void findByUsername(String username) {
        em.find(User.class, username);
    }

}
