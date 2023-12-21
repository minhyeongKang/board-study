package com.hellomeen.boardstudy.board.dto;

import com.hellomeen.boardstudy.comment.dto.CommentResponseDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class BoardViewResponseDto {

    private Long id;
    private String nickname;
    private String title;
    private String content;
    //private String likes;
    private List<CommentResponseDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
