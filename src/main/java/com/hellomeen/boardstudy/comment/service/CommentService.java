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
}
