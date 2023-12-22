package com.hellomeen.boardstudy.user.controller;

import com.hellomeen.boardstudy.global.dto.ApiResponseDto;
import com.hellomeen.boardstudy.global.jwt.JwtUtil;
import com.hellomeen.boardstudy.global.jwt.TokenRepository;
import com.hellomeen.boardstudy.user.dto.SignUpResponseDto;
import com.hellomeen.boardstudy.user.dto.signUpRequestDto;
import com.hellomeen.boardstudy.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
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
    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    @PostMapping("/signup")
    public ApiResponseDto<SignUpResponseDto> signUp(@RequestBody signUpRequestDto requestDto) {
        SignUpResponseDto responseDto = userService.signUp(requestDto);
        return new ApiResponseDto<>(HttpStatus.CREATED.value(), "회원가입 성공", responseDto);
    }

    @PostMapping("/logout")
    @Transactional
    public ApiResponseDto<String> logout(HttpServletRequest request) {
        String accessToken = jwtUtil.getJwtFromHeader(request, JwtUtil.ACCESS_TOKEN_HEADER);
        String refreshToken = jwtUtil.getJwtFromHeader(request, JwtUtil.REFRESH_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken)) {
            jwtUtil.deleteAccessToken(accessToken);
        }

        if (StringUtils.hasText(refreshToken)) {
            jwtUtil.deleteRefreshToken(refreshToken);
            tokenRepository.deleteByToken(refreshToken);
        }

        return new ApiResponseDto<>(HttpStatus.OK.value(), "로그아웃 성공", null);

//    @DeleteMapping("/logout")
//    public ApiResponseDto<LoginResponseDto> logout(
//            @AuthenticationPrincipal UserDetailsImpl userDetails) {
//        LoginResponseDto responseDto = userService.logout(userDetails.getUser());
//        return new ApiResponseDto<>(HttpStatus.OK.value(), "로그아웃 성공", responseDto);
//    }
    }
}
