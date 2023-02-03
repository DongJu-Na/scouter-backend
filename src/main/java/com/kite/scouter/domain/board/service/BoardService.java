package com.kite.scouter.domain.board.service;

import com.kite.scouter.domain.board.dto.BoardDto.CreateBoard;
import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardById;
import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardsByCategory;
import com.kite.scouter.domain.board.dto.BoardDto.UpdateBoard;
import com.kite.scouter.domain.board.model.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

  Board getBoardById (Long boardId);

  Page<ResponseBoardsByCategory> getBoardsByCategory (Pageable pageable,Long categoryId);

  ResponseBoardById getBoardByBoardId (Long boardId);

  Long createBoard(CreateBoard createBoard);

  void updateBoard(Long boardId, UpdateBoard updateBoard);

  void updateBoardView(Long boardId);

  void deleteBoard(Long boardId);
}
