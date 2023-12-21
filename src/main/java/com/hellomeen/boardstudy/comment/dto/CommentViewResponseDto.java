package com.hellomeen.boardstudy.comment.dto;

import com.hellomeen.boardstudy.comment.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentViewResponseDto {

    private Long id;
    private String nickname;
    private String content;
    private Long likes;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentViewResponseDto(Comment comment, Long likes) {
        this.id = comment.getId();
        this.content = comment.getComment();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
        this.nickname = comment.getUser() == null ? "탈퇴한 사용자" : comment.getUser().getNickname();
        this.likes = likes;
    }
}
