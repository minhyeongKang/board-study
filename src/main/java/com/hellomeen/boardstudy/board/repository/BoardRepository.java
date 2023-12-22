package com.hellomeen.boardstudy.board.repository;

import com.hellomeen.boardstudy.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {
    List<Board> findAllByOrderByCreatedAtDesc();
}
