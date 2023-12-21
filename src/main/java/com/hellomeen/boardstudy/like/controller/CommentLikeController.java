package com.hellomeen.boardstudy.like.controller;

import com.hellomeen.boardstudy.global.dto.ApiResponseDto;
import com.hellomeen.boardstudy.global.security.UserDetailsImpl;
import com.hellomeen.boardstudy.like.service.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class CommentLikeController {

    private final CommentLikeService commentLikeService;

    @PostMapping("/comments/{commentId}/likes")
    public ApiResponseDto<Void> likeBoard(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentLikeService.likeComment(commentId, userDetails.getUser());
        return new  ApiResponseDto<>(HttpStatus.OK.value(), "좋아요 성공");
    }

    @DeleteMapping("/comments/{commentId}/likes")
    public ApiResponseDto<Void> unlikeBoard(
            @PathVariable("commentId") Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentLikeService.unlikeComment(commentId, userDetails.getUser());
        return new ApiResponseDto<>(HttpStatus.OK.value(), "좋아요 취소");
    }
}
