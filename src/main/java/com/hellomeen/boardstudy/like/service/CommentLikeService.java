package com.hellomeen.boardstudy.like.service;

import com.hellomeen.boardstudy.comment.entity.Comment;
import com.hellomeen.boardstudy.comment.repository.CommentRepository;
import com.hellomeen.boardstudy.like.entity.CommentLike;
import com.hellomeen.boardstudy.like.repository.CommentLikeRepository;
import com.hellomeen.boardstudy.user.entity.User;
import com.hellomeen.boardstudy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentLikeService {

    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;

    public void likeComment(Long commentId, User user) {
        User loginUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));

        Optional<CommentLike> findCommentLike = commentLikeRepository.findByUserAndCommentId(loginUser, commentId);
        if (findCommentLike.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 누른 댓글 입니다.");
        }

        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));

        CommentLike commentLike = CommentLike.builder()
                .comment(comment)
                .user(loginUser)
                .build();

        commentLikeRepository.save(commentLike);
    }

    public void unlikeComment(Long commentId, User user) {
        User loginUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));

        Optional<CommentLike> findCommentLike = commentLikeRepository.findByUserAndCommentId(loginUser, commentId);
        if (findCommentLike.isEmpty()) {
            throw new IllegalArgumentException("댓글의 좋아요가 존재하지 않습니다.");
        }

        CommentLike commentLike = findCommentLike.get();
        commentLikeRepository.delete(commentLike);
    }
}
