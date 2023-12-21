package com.hellomeen.boardstudy.board.service;

import com.hellomeen.boardstudy.board.dto.BoardRequestDto;
import com.hellomeen.boardstudy.board.dto.BoardResponseDto;
import com.hellomeen.boardstudy.board.dto.BoardViewResponseDto;
import com.hellomeen.boardstudy.board.entity.Board;
import com.hellomeen.boardstudy.board.repository.BoardRepository;
import com.hellomeen.boardstudy.comment.dto.CommentResponseDto;
import com.hellomeen.boardstudy.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto, User user) {
        Board board = Board.builder()
                .title(boardRequestDto.getTitle())
                .content(boardRequestDto.getContent())
                .user(user)
                .build();
        boardRepository.save(board);

        return new BoardResponseDto(board);
    }

    @Transactional
    public BoardResponseDto updateBoard(Long boardId, BoardRequestDto boardRequestDto, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        if (!user.getId().equals(board.getUser().getId())) {
            throw new RejectedExecutionException("게시글의 작성자만 수정할 수 있습니다.");
        }
        board.update(boardRequestDto);

        return new BoardResponseDto(board);
    }

    public BoardResponseDto deleteBoard(Long boardId, User user) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        if (!user.getId().equals(board.getUser().getId())) {
            throw new RejectedExecutionException("게시글의 작성자만 삭제할 수 있습니다.");
        }
        boardRepository.delete(board);

        return new BoardResponseDto(board);
    }

    public BoardViewResponseDto getBoards(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        List<CommentResponseDto> comments = board.getCommentList()
                .stream()
                .map(CommentResponseDto::new)
                .toList();
        return BoardViewResponseDto.builder()
                .id(board.getId())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .comments(comments)
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();
    }
}
