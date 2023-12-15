package com.hellomeen.boardstudy.user.entity;

import com.hellomeen.boardstudy.global.entity.Timestamped;
import com.hellomeen.boardstudy.global.entity.UserRoleEnum;
import com.hellomeen.boardstudy.global.jwt.JwtEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "USER")
public class User extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String intro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRoleEnum role;

    @OneToOne(mappedBy = "user", cascade = CascadeType.REMOVE)
    JwtEntity jwtEntity;

    @Builder
    public User(String username, String nickname, String email, String password, String intro, UserRoleEnum role) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.intro = intro;
        this.role = role;
    }
}
