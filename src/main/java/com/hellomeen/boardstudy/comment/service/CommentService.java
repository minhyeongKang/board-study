package com.hellomeen.boardstudy.comment.service;

import com.hellomeen.boardstudy.board.entity.Board;
import com.hellomeen.boardstudy.board.repository.BoardRepository;
import com.hellomeen.boardstudy.comment.dto.CommentRequestDto;
import com.hellomeen.boardstudy.comment.dto.CommentResponseDto;
import com.hellomeen.boardstudy.comment.entity.Comment;
import com.hellomeen.boardstudy.comment.repository.CommentRepository;
import com.hellomeen.boardstudy.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.RejectedExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public CommentResponseDto createComment(Long boardId, CommentRequestDto commentRequestDto, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        Comment comment = Comment.builder()
                .comment(commentRequestDto.getComment())
                .user(user)
                .board(board)
                .build();
        commentRepository.save(comment);

        return new CommentResponseDto(comment);
    }

    @Transactional
    public CommentResponseDto updateComment(Long commentId, CommentRequestDto commentRequestDto, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new NullPointerException("해당 댓글이 없습니다."));
        if (!user.getId().equals(comment.getUser().getId())) {
            throw new RejectedExecutionException("댓글의 작성자만 수정할 수 있습니다.");
        }
        comment.update(commentRequestDto);

        return new CommentResponseDto(comment);
    }
}
