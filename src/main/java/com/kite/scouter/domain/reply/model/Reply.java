package com.kite.scouter.domain.reply.model;

import com.kite.scouter.domain.board.model.Board;
import com.kite.scouter.domain.user.model.User;
import com.kite.scouter.global.core.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reply extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long replyId;

  private String replyContent;

  private Boolean enable = true;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "replyUser")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "replyBoard")
  private Board board;

  @Builder
  private Reply(
      final String replyContent,
      final User user,
      final Board board) {
    this.replyContent = replyContent;
    this.user = user;
    this.board = board;
  }

  public void changeEnable(final Boolean enable) {
    this.enable = enable;
  }
  public void changeContent(final String replyContent) {this.replyContent = replyContent;}

}
