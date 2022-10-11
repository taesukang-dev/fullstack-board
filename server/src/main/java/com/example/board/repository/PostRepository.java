package com.example.board.repository;

import com.example.board.domain.Post;
import com.example.board.exception.BoardApplicationException;
import com.example.board.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    private final EntityManager em;

    public Optional<Post> findById(Long postId) {
        try {
            return Optional.of(em.createQuery("select p from Post p" +
                            " join fetch p.user" +
                            " where p.id =:postId", Post.class)
                    .setParameter("postId", postId)
                    .getSingleResult());
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public Optional<List<Post>> findAllWithPaging(int start) {
        try {
            return Optional.of(em.createQuery("select distinct p from Post p" +
                            " join fetch p.user " +
                            " order by p.id desc", Post.class)
                    .setFirstResult(start * 10)
                    .setMaxResults(10)
                    .getResultList());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Long save(Post post) {
        em.persist(post);
        return post.getId();
    }

    public void remove(Post post) {
        em.remove(post);
    }
}
