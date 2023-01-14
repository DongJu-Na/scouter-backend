package com.kite.scouter.domain.board.service.impl;

import com.kite.scouter.domain.board.dto.BoardDto.ResponseSearchBoardsByCategory;
import com.kite.scouter.domain.board.repository.BoardRepository;
import com.kite.scouter.domain.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

  private final BoardRepository boardRepository;

  @Override
  public Page<ResponseSearchBoardsByCategory> getBoardsByCategory(
      Pageable pageable,
      Long categoryId) {
    return null;
  }
}
