package com.hellomeen.boardstudy.comment.entity;

import com.hellomeen.boardstudy.board.entity.Board;
import com.hellomeen.boardstudy.global.entity.Timestamped;
import com.hellomeen.boardstudy.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "comment")
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String comment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    @Builder
    private Comment(String comment, User user, Board board) {
        this.comment = comment;
        this.user = user;
        this.board = board;
    }
}
