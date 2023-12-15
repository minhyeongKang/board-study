package com.hellomeen.boardstudy.user.controller;

import com.hellomeen.boardstudy.global.dto.ApiResponseDto;
import com.hellomeen.boardstudy.user.dto.SignUpResponseDto;
import com.hellomeen.boardstudy.user.dto.signUpRequestDto;
import com.hellomeen.boardstudy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Controller
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ApiResponseDto<SignUpResponseDto> signUp(@RequestBody signUpRequestDto requestDto) {
        SignUpResponseDto responseDto = userService.signUp(requestDto);
        return new ApiResponseDto<>(HttpStatus.CREATED.value(), "회원가입 성공", responseDto);
    }
}
