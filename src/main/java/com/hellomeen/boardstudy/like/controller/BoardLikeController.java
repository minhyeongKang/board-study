package com.hellomeen.boardstudy.like.controller;

import com.hellomeen.boardstudy.global.dto.ApiResponseDto;
import com.hellomeen.boardstudy.global.security.UserDetailsImpl;
import com.hellomeen.boardstudy.like.service.BoardLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BoardLikeController {

    private final BoardLikeService boardLikeService;

    @PostMapping("/boards/{boardId}/likes")
    public ApiResponseDto<Void> likeBoard(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardLikeService.likeBoard(boardId, userDetails.getUser());
        return new  ApiResponseDto<>(HttpStatus.OK.value(), "좋아요 성공");
    }

    @DeleteMapping("/boards/{boardId}/likes")
    public ApiResponseDto<Void> unlikeBoard(
            @PathVariable("boardId") Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        boardLikeService.unlikeBoard(boardId, userDetails.getUser());
        return new ApiResponseDto<>(HttpStatus.OK.value(), "좋아요 취소");
    }
}
