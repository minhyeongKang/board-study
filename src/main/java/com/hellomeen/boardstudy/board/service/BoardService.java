package com.hellomeen.boardstudy.board.service;

import com.hellomeen.boardstudy.board.dto.BoardRequestDto;
import com.hellomeen.boardstudy.board.dto.BoardResponseDto;
import com.hellomeen.boardstudy.board.dto.BoardViewResponseDto;
import com.hellomeen.boardstudy.board.entity.Board;
import com.hellomeen.boardstudy.board.repository.BoardRepository;
import com.hellomeen.boardstudy.comment.dto.CommentViewResponseDto;
import com.hellomeen.boardstudy.like.entity.BoardLike;
import com.hellomeen.boardstudy.like.repository.BoardLikeRepository;
import com.hellomeen.boardstudy.like.repository.CommentLikeRepository;
import com.hellomeen.boardstudy.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;

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

    @Transactional(readOnly = true)
    public BoardViewResponseDto getBoard(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NullPointerException("게시글이 없습니다."));
        List<CommentViewResponseDto> comments = board.getCommentList()
                .stream()
                .map(comment -> {
                    Long commentLikes = commentLikeRepository.countByComment(comment);
                    return new CommentViewResponseDto(comment, commentLikes);
                })
                .toList();
        return BoardViewResponseDto.builder()
                .id(board.getId())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .content(board.getContent())
                .likes(boardLikeRepository.countByBoard(board))
                .comments(comments)
                .createdAt(board.getCreatedAt())
                .modifiedAt(board.getModifiedAt())
                .build();
    }

    @Transactional(readOnly = true)
    public List<BoardViewResponseDto> getBoards(String type, User user) {
        List<Board> boardList;

        if (type.equals("all")) {
            boardList = boardRepository.findAll();
        } else if (type.equals("recent")) {
            boardList = boardRepository.findAllByOrderByCreatedAtDesc();
        } else if (type.equals("like")) {
            boardList = findByLike(user);
        } else {
            throw new IllegalArgumentException();
        }
        return findAllByBoard(boardList);
    }

    private List<BoardViewResponseDto> findAllByBoard(List<Board> boards) {
        List<BoardViewResponseDto> boardViewResponseDto = new ArrayList<>();
        for (Board board : boards) {
            boardViewResponseDto
                    .add(BoardViewResponseDto.builder()
                            .id(board.getId())
                            .title(board.getTitle())
                            .content(board.getContent())
                            .nickname(board.getUser().getNickname())
                            .likes(boardLikeRepository.countByBoard(board))
                            .createdAt(board.getCreatedAt())
                            .build());

        }
        return boardViewResponseDto;
    }

    private List<Board> findByLike(final User user) {
        return boardLikeRepository.findByUser(user)
                .stream()
                .map(BoardLike::getBoard)
                .toList();
    }
}
