package com.kite.scouter.domain.board.model;

import com.kite.scouter.domain.category.model.Category;
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
public class Board extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long boardId;

  private String boardTitle;

  private String boardContent;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "boardCategory")
  private Category category;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "boardUser")
  private User user;

  private Long boardViewCnt;

  @Builder
  private Board(
      final String boardTitle,
      final String boardContent,
      final Category category,
      final User user) {
    this.boardTitle = boardTitle;
    this.boardContent = boardContent;
    this.category = category;
    this.user = user;
  }

}
