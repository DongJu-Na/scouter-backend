package com.kite.scouter.domain.board.dto;

import com.kite.scouter.domain.board.model.Board;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class BoardDto {

  @Getter
  public static class ResponseBoardsByCategory {
    private Long boardId;
    private String boardTitle;
    private LocalDateTime createdDt;
    private String boardUserNickName;
    private Long boardViewCnt;
    private int replyCount;

    private ResponseBoardsByCategory(final Board board) {
      this.boardId = board.getBoardId();
      this.boardTitle = board.getBoardTitle();
      this.createdDt = board.getCreatedDt();
      this.boardUserNickName = board.getUser().getNickName();
      this.boardViewCnt = board.getBoardViewCnt();
      this.replyCount = board.getReplyList().size();
    }

    public static ResponseBoardsByCategory from(final Board board) {
      return new ResponseBoardsByCategory(board);
    }

    public static List<ResponseBoardsByCategory> from(final List<Board> board) {
      return board.stream().map(ResponseBoardsByCategory::from).collect(Collectors.toList());
    }
  }

  @Getter
  public static class ResponseBoardById {
    private Long boardId;
    private String boardTitle;
    private String boardContent;
    private LocalDateTime createdDt;
    private String boardUserNickName;
    private String boardUserEmail;
    private Long boardViewCnt;
    private int replyCount;

    private ResponseBoardById(final Board board){
      this.boardId = board.getBoardId();
      this.boardTitle = board.getBoardTitle();
      this.boardContent = board.getBoardContent();
      this.createdDt = board.getCreatedDt();
      this.boardUserNickName = board.getUser().getNickName();
      this.boardUserEmail = board.getUser().getEmail();
      this.boardViewCnt = board.getBoardViewCnt();
      this.replyCount = board.getReplyList().size();
    }

    public static ResponseBoardById from(Board board) {
      return new ResponseBoardById(board);
    }
  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @NoArgsConstructor
  @Getter
  public static class CreateBoard {

    @NotBlank
    private String boardTitle;
    @NotBlank
    @Lob
    private String boardContent;
    @NotNull
    private Long categoryId;

  }

  @AllArgsConstructor(access = AccessLevel.PRIVATE)
  @NoArgsConstructor
  @Getter
  public static class UpdateBoard {

    @NotBlank
    private String boardTitle;
    @NotBlank
    @Lob
    private String boardContent;

  }

}
