package com.hellomeen.boardstudy.board.repository;

import com.hellomeen.boardstudy.board.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
