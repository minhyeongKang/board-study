package com.hellomeen.boardstudy.user.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;

@Getter
public class signUpRequestDto {

    private final String username;

    @Pattern(regexp = "^[a-zA-Z0-9]{4,10}$", message = "닉네임은 영문자와 숫자 중 4글자 이상 10글자 이하의 조합입니다.")
    private final String nickname;

    @Pattern(regexp = "^[a-zA-Z0-9]{8,15}$", message = "비밀번호는 영문자와 숫자 중 8글자 이상 15글자 이하의 조합입니다.")
    private final String password;

    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$\n")
    private final String email;

    private final String intro;

    private final boolean admin = false;

    private final String adminToken = "";

    @Builder
    public signUpRequestDto(String username, String nickname, String password, String email, String intro) {
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.intro = intro;
    }
}
