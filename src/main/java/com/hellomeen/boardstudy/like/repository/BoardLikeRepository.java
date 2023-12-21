package com.hellomeen.boardstudy.like.repository;

import com.hellomeen.boardstudy.like.entity.BoardLike;
import com.hellomeen.boardstudy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BoardLikeRepository extends JpaRepository<BoardLike, Long> {
    Optional<BoardLike> findByUserAndBoardId(User loginUser, Long boardId);
}
