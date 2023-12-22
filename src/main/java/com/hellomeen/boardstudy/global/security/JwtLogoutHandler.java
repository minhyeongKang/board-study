package com.hellomeen.boardstudy.global.security;

import com.hellomeen.boardstudy.global.jwt.JwtUtil;
import com.hellomeen.boardstudy.global.jwt.TokenRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
@Slf4j(topic = "JWT 로그아웃 처리")
public class JwtLogoutHandler implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;

    public JwtLogoutHandler(TokenRepository tokenRepository, JwtUtil jwtUtil) {
        this.tokenRepository = tokenRepository;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String accessToken = jwtUtil.getJwtFromHeader(request, JwtUtil.ACCESS_TOKEN_HEADER);
        String refreshToken = jwtUtil.getJwtFromHeader(request, JwtUtil.REFRESH_TOKEN_HEADER);

        if (StringUtils.hasText(accessToken)) {
            jwtUtil.deleteAccessToken(accessToken);
        }

        if (StringUtils.hasText(refreshToken)) {
            jwtUtil.deleteRefreshToken(refreshToken);
            tokenRepository.deleteByToken(refreshToken);
        }
    }
}
