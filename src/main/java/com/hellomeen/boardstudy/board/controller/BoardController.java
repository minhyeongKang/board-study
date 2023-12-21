package com.hellomeen.boardstudy.board.controller;

import com.hellomeen.boardstudy.board.dto.BoardRequestDto;
import com.hellomeen.boardstudy.board.dto.BoardResponseDto;
import com.hellomeen.boardstudy.board.dto.BoardViewResponseDto;
import com.hellomeen.boardstudy.board.service.BoardService;
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
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/boards")
    public ApiResponseDto<BoardResponseDto> createBoard(
            @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.createBoard(requestDto, userDetails.getUser());
        return new  ApiResponseDto<>(HttpStatus.CREATED.value(), "게시글 작성 완료", responseDto);
    }

    @PutMapping("/boards/{boardId}")
    public ApiResponseDto<BoardResponseDto> updateBoard(
            @PathVariable Long boardId,
            @RequestBody BoardRequestDto requestDto,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.updateBoard(boardId, requestDto, userDetails.getUser());
        return new ApiResponseDto<>(HttpStatus.OK.value(), "게시글 수정 완료", responseDto);
    }

    @DeleteMapping("/boards/{boardId}")
    public ApiResponseDto<BoardResponseDto> deleteBoard(
            @PathVariable Long boardId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        BoardResponseDto responseDto = boardService.deleteBoard(boardId, userDetails.getUser());
        return new ApiResponseDto<>(HttpStatus.OK.value(), "게시글 삭제 완료", responseDto);
    }

    @GetMapping("/boards/{boardId}")
    public ApiResponseDto<BoardViewResponseDto> getBoard(@PathVariable Long boardId) {
        BoardViewResponseDto responseDto = boardService.getBoard(boardId);
        return new ApiResponseDto<>(HttpStatus.OK.value(), boardId + "번 글 조회 성공", responseDto);
    }
}
