package com.kite.scouter.domain.reply.dto;

import com.kite.scouter.domain.reply.model.Reply;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class ReplyDto {

  @Getter
  public static class ResponseReplysByBoardId {
    private Long replyId;
    private LocalDateTime createdDt;
    private String replyContent;
    private String replyUserNickName;
    private String replyUserEmail;

    private ResponseReplysByBoardId(final Reply reply) {
      this.replyId = reply.getReplyId();
      this.replyContent = reply.getReplyContent();
      this.createdDt = reply.getCreatedDt();
      this.replyUserNickName = reply.getUser().getNickName();
      this.replyUserEmail = reply.getUser().getEmail();
    }

    public static ResponseReplysByBoardId from(final Reply reply) {
      return new ResponseReplysByBoardId(reply);
    }

    public static List<ResponseReplysByBoardId> from(final List<Reply> replys) {
      return replys.stream().map(ResponseReplysByBoardId::from).collect(Collectors.toList());
    }
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @NoArgsConstructor
  @Getter
  public static class CreateReply {

    @NotBlank
    private String replyContent;
    @NotNull
    private Long boardId;

  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @NoArgsConstructor
  @Getter
  public static class UpdateReply {

    @NotBlank
    private String replyContent;

  }

}
