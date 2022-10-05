package com.example.board.service.fixture;

import com.example.board.domain.Post;
import com.example.board.domain.User;

public class PostFixture {
    public static Post get(String username, Long postId, Long userId) {
        User user = User.makeFixture(userId, username, "test");
        return Post.makeFixture(postId, user);
    }
}
