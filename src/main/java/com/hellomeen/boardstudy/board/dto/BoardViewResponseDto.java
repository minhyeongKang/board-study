package com.hellomeen.boardstudy.board.dto;

import com.hellomeen.boardstudy.comment.dto.CommentViewResponseDto;
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
    private Long likes;
    private List<CommentViewResponseDto> comments;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
}
