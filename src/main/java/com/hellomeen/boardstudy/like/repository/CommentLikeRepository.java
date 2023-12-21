package com.hellomeen.boardstudy.like.repository;

import com.hellomeen.boardstudy.comment.entity.Comment;
import com.hellomeen.boardstudy.like.entity.CommentLike;
import com.hellomeen.boardstudy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByUserAndCommentId(User user, Long commentId);
    Long countByComment(Comment comment);
}
