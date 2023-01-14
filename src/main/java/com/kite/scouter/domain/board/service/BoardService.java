package com.kite.scouter.domain.board.service;

import com.kite.scouter.domain.board.dto.BoardDto.ResponseSearchBoardsByCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardService {

  Page<ResponseSearchBoardsByCategory> getBoardsByCategory (Pageable pageable,Long categoryId);

}
