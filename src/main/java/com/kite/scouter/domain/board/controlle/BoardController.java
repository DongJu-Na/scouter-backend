package com.kite.scouter.domain.board.controlle;

import com.kite.scouter.domain.board.model.Board;
import com.kite.scouter.domain.board.service.BoardService;
import com.kite.scouter.domain.category.model.Category;
import com.kite.scouter.domain.category.repository.CategoryRepository;
import com.kite.scouter.global.core.CommonResponse;
import com.kite.scouter.global.core.PageWrapper;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/board")
@RequiredArgsConstructor
public class BoardController {

  private final BoardService boardService;

  @GetMapping
  public CommonResponse<PageWrapper> get(
      @RequestParam(value = "categoryId") final Long categoryId,
      @RequestParam(value = "page", defaultValue = "0", required = false) final int page,
      @RequestParam(value = "size", defaultValue = "10", required = false) final int size
  ) {
    return CommonResponse.from(PageWrapper.of(boardService.getBoardsByCategory(PageRequest.of(page, size),categoryId)));
  }

}
