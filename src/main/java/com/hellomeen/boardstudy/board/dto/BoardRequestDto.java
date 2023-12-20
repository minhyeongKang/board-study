package com.hellomeen.boardstudy.board.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardRequestDto {

    private String title;
    private String content;
}
