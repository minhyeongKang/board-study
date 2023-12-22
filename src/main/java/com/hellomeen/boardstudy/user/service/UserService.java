package com.hellomeen.boardstudy.user.service;

import com.hellomeen.boardstudy.global.entity.UserRoleEnum;
import com.hellomeen.boardstudy.global.jwt.JwtEntity;
import com.hellomeen.boardstudy.global.jwt.JwtUtil;
import com.hellomeen.boardstudy.global.jwt.TokenRepository;
import com.hellomeen.boardstudy.user.dto.LoginResponseDto;
import com.hellomeen.boardstudy.user.dto.SignUpResponseDto;
import com.hellomeen.boardstudy.user.dto.signUpRequestDto;
import com.hellomeen.boardstudy.user.entity.User;
import com.hellomeen.boardstudy.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final JwtUtil jwtUtil;
    private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

    public SignUpResponseDto signUp(signUpRequestDto signUpRequestDto) {

        String nickname = signUpRequestDto.getNickname();
        Optional<User> checkNickname = userRepository.findByNickname(nickname);
        if (checkNickname.isPresent()) {
            throw new IllegalArgumentException("중복된 닉네임이 존재합니다.");
        }

        String email = signUpRequestDto.getEmail();
        Optional<User> checkEmail = userRepository.findByEmail(email);
        if (checkEmail.isPresent()) {
            throw new IllegalArgumentException("중복된 이메일이 존재합니다.");
        }

        String password = passwordEncoder.encode(signUpRequestDto.getPassword());

        UserRoleEnum roleEnum = UserRoleEnum.USER;
        if (signUpRequestDto.isAdmin()) {
            if (!ADMIN_TOKEN.equals(signUpRequestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호를 다시 확인해 주세요.");
            }
            roleEnum = UserRoleEnum.ADMIN;
        }

        User user = User.builder()
                .username(signUpRequestDto.getUsername())
                .nickname(nickname)
                .email(email)
                .password(password)
                .intro(signUpRequestDto.getIntro())
                .role(roleEnum)
                .build();
        userRepository.save(user);


        return SignUpResponseDto.builder()
                .username(signUpRequestDto.getUsername())
                .nickname(nickname)
                .email(email)
                .build();
    }

    @Transactional
    public LoginResponseDto logout(User user) {
        JwtEntity jwt = tokenRepository.findByUserId(user.getId());

        if (jwtUtil != null) {
            String token = jwt.getToken();
            jwtUtil.deleteAccessToken(token);
            jwtUtil.deleteRefreshToken(token);
            tokenRepository.delete(jwt);

            return new LoginResponseDto(user);
        } else {
            throw new IllegalArgumentException("로그아웃에 실패했습니다.");
        }
    }

}
