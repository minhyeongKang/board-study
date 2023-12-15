package com.hellomeen.boardstudy.global.jwt;

import com.hellomeen.boardstudy.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<JwtEntity,Long> {
    User findByToken(String refreshToken);
    JwtEntity findByUserId(Long userId);
}
