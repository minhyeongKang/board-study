package com.hellomeen.boardstudy.comment.controller;

import com.hellomeen.boardstudy.comment.dto.CommentRequestDto;
import com.hellomeen.boardstudy.comment.dto.CommentResponseDto;
import com.hellomeen.boardstudy.comment.service.CommentService;
import com.hellomeen.boardstudy.global.dto.ApiResponseDto;
import com.hellomeen.boardstudy.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments/{boardId}")
    public ApiResponseDto<CommentResponseDto> createComment(
            @PathVariable Long boardId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.createComment(boardId, requestDto, userDetails.getUser());
        return new ApiResponseDto<>(HttpStatus.CREATED.value(), boardId + "번 글 댓글 생성 완료", responseDto);
    }

    @PutMapping("/comments/{commentId}")
    public ApiResponseDto<CommentResponseDto> updateComment(
            @PathVariable Long commentId,
            @RequestBody CommentRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        CommentResponseDto responseDto = commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return new ApiResponseDto<>(HttpStatus.OK.value(), commentId + "번 댓글 수정 완료", responseDto);

    }
}
