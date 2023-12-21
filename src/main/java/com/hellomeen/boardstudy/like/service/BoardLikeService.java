package com.hellomeen.boardstudy.like.service;

import com.hellomeen.boardstudy.board.entity.Board;
import com.hellomeen.boardstudy.board.repository.BoardRepository;
import com.hellomeen.boardstudy.like.entity.BoardLike;
import com.hellomeen.boardstudy.like.repository.BoardLikeRepository;
import com.hellomeen.boardstudy.user.entity.User;
import com.hellomeen.boardstudy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardLikeService {

    private final BoardRepository boardRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final UserRepository userRepository;

    public void likeBoard(Long boardId, User user) {
        User loginUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));

        Optional<BoardLike> findBoardLike = boardLikeRepository.findByUserAndBoardId(loginUser, boardId);
        if (findBoardLike.isPresent()) {
            throw new IllegalArgumentException("이미 좋아요를 누른 게시글 입니다.");
        }

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new NullPointerException("해당 게시글이 없습니다."));

        BoardLike boardLike = BoardLike.builder()
                .board(board)
                .user(loginUser)
                .build();

        boardLikeRepository.save(boardLike);
    }

    public void unlikeBoard(Long boardId, User user) {
        User loginUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new NullPointerException("해당 유저가 없습니다."));

        Optional<BoardLike> findBoardLike =
                boardLikeRepository.findByUserAndBoardId(loginUser, boardId);
        if (findBoardLike.isEmpty()) {
            throw new IllegalArgumentException("게시글의 좋아요가 존재하지 않습니다.");
        }

        BoardLike boardLike = findBoardLike.get();
        boardLikeRepository.delete(boardLike);
    }
}
