package com.kite.scouter.domain.reply.model;

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

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "replyUser")
  private User user;

  @Builder
  private Reply(
      final String replyContent,
      final User replyUser) {
    this.replyContent = replyContent;
    this.user = user;
  }

}
