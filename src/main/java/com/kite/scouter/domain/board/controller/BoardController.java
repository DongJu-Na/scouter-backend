package com.kite.scouter.domain.board.controller;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import com.kite.scouter.domain.board.dto.BoardDto;
import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardById;
import com.kite.scouter.domain.board.service.BoardService;
import com.kite.scouter.global.core.CommonResponse;
import com.kite.scouter.global.core.PageWrapper;
import com.kite.scouter.global.core.SuccessData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping
  @ResponseStatus(OK)
  public CommonResponse<PageWrapper> getBoardsByCategory(
      @RequestParam(value = "categoryId") final Long categoryId,
      @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
      @RequestParam(value = "size", defaultValue = "10", required = false) final int size
  ) {
    return CommonResponse.from(PageWrapper.of(boardService.getBoardsByCategory(PageRequest.of(page, size),categoryId)));
  }

  @GetMapping("/{boardId}")
  @ResponseStatus(OK)
  public CommonResponse<ResponseBoardById> getBoardByBoardId(@PathVariable final Long boardId) {
    return CommonResponse.from(boardService.getBoardByBoardId(boardId));
  }

  @PostMapping
  @ResponseStatus(CREATED)
  public CommonResponse<Long> createBoard(@RequestBody @Valid final BoardDto.CreateBoard createBoard) {
    return CommonResponse.from(boardService.createBoard(createBoard));
  }

  @PutMapping("/{boardId}")
  @ResponseStatus(CREATED)
  public CommonResponse<SuccessData> updateBoard(
      @PathVariable final Long boardId,
      @RequestBody @Valid final BoardDto.UpdateBoard updateBoard
  ) {
    boardService.updateBoard(boardId,updateBoard);
    return CommonResponse.from(SuccessData.of());
  }

  @DeleteMapping("/{boardId}")
  @ResponseStatus(CREATED)
  public CommonResponse<SuccessData> deleteBoard(@PathVariable final Long boardId) {
    boardService.deleteBoard(boardId);
    return CommonResponse.from(SuccessData.of());
  }

}
