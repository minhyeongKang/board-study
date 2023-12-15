package com.hellomeen.boardstudy.user.repository;

import com.hellomeen.boardstudy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByNickname(String nickname);
    Optional<User> findByEmail(String email);
}
