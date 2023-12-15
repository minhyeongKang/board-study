package com.hellomeen.boardstudy.global.jwt;

import com.hellomeen.boardstudy.user.entity.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Entity
@RequiredArgsConstructor
@Getter
@Table(name = "JWT_TOKEN")
public class JwtEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    @OneToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @Builder
    public JwtEntity(String token, User user) {
        this.token = token;
        this.user = user;
    }
}