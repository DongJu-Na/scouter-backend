package com.kite.scouter.domain.board.service.impl;

import com.kite.scouter.domain.board.dto.BoardDto.CreateBoard;
import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardById;
import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardsByCategory;
import com.kite.scouter.domain.board.dto.BoardDto.UpdateBoard;
import com.kite.scouter.domain.board.model.Board;
import com.kite.scouter.domain.board.repository.BoardRepository;
import com.kite.scouter.domain.board.service.BoardService;
import com.kite.scouter.domain.category.model.Category;
import com.kite.scouter.domain.category.service.CategoryService;
import com.kite.scouter.domain.user.model.User;
import com.kite.scouter.domain.user.service.UserService;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepo;

  private final UserService userService;

  private final CategoryService categoryService;


  @Override
  public Board getBoardById(Long boardId) {
    return boardRepo.findByBoardIdAndEnableTrue(boardId)
        .orElseThrow(() -> BadRequestException.of(ResponseCode.BY0001,"board.id.not.find"));
  }

  @Override
  public Page<ResponseBoardsByCategory> getBoardsByCategory(final Pageable pageable, final Long categoryId) {
    return boardRepo.getBoardsByCategory(pageable, categoryId);
  }

  @Override
  public ResponseBoardById getBoardByBoardId(final Long boardId) {
    return ResponseBoardById.from(getBoardById(boardId));
  }

  @Override
  public Long createBoard(final CreateBoard createBoard) {
    User user = userService.getUserBySelf();
    Category category = categoryService.getCategoryById(createBoard.getCategoryId());
    Board board = Board.builder()
        .boardTitle(createBoard.getBoardTitle())
        .boardContent(createBoard.getBoardContent())
        .category(category)
        .user(user)
        .build();
    return boardRepo.save(board).getBoardId();
  }

  @Override
  public void updateBoard(final Long boardId, final UpdateBoard updateBoard) {
    Board board = getBoardById(boardId);
    board.changeTitle(updateBoard.getBoardTitle());
    board.changeContent(updateBoard.getBoardContent());
    boardRepo.save(board);
  }

  @Override
  public void updateBoardView(final Long boardId) {
    Board board = getBoardById(boardId);
    board.changeViewCountPlusOne();
    boardRepo.save(board);
  }

  @Override
  public void deleteBoard(Long boardId) {
    Board board = getBoardById(boardId);
    board.changeEnable(false);
    boardRepo.save(board);
  }

}
