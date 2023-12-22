package com.hellomeen.boardstudy.user.dto;

import com.hellomeen.boardstudy.user.entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginResponseDto {

    private final String username;
    private final String nickname;
    private final String email;

    @Builder
    public LoginResponseDto(User user) {
        this.username = user.getUsername();
        this.nickname = user.getNickname();
        this.email = user.getEmail();
    }
}
