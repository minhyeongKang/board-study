package com.hellomeen.boardstudy.comment.repository;

import com.hellomeen.boardstudy.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
