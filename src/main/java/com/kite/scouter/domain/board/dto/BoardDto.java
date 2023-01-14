package com.kite.scouter.domain.board.dto;

import com.kite.scouter.domain.board.model.Board;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

public class BoardDto {

  @Getter
  public static class ResponseSearchBoardsByCategory {
    private Long boardId;
    private String boardTitle;
    private String boardUserNickName;
    private Long boardViewCnt;

    private ResponseSearchBoardsByCategory(final Board board) {
      this.boardId = board.getBoardId();
      this.boardTitle = board.getBoardTitle();
      this.boardUserNickName = board.getUser().getNickName();
      this.boardViewCnt = board.getBoardViewCnt();
    }

    public static ResponseSearchBoardsByCategory of(final Board board) {
      return new ResponseSearchBoardsByCategory(board);
    }

    public static List<ResponseSearchBoardsByCategory> of(final List<Board> board) {
      return board.stream().map(ResponseSearchBoardsByCategory::of).collect(Collectors.toList());
    }
  }

}
