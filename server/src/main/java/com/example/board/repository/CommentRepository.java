package com.example.board.repository;

import com.example.board.domain.Comment;
import com.example.board.domain.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class CommentRepository {

    private final EntityManager em;

    public Optional<Comment> findById(Long commentId) {
        try {
            return Optional.of(em.find(Comment.class, commentId));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Comment>> findByPostId(Long postId) {
        try {
            return Optional.of(em.createQuery("select distinct c from Comment c " +
                            " join fetch c.user" +
                            " join fetch c.post" +
                            " where c.post.id =:postId" +
                            " and c.parent is null")
                    .setParameter("postId", postId)
                    .getResultList());
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Comment save(Comment comment) {
        em.persist(comment);
        return comment;
    }

    public void delete(Comment comment) {
        em.remove(comment);
    }

}
