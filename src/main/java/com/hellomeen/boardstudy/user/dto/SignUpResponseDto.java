package com.hellomeen.boardstudy.user.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SignUpResponseDto {

    private final String username;
    private final String nickname;
    private final String email;

    @Builder
    public SignUpResponseDto(String username, String nickname, String email) {
        this.username = username;
        this.nickname = nickname;
        this.email = email;
    }
}
