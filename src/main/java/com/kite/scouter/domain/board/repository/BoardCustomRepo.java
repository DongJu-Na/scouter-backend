package com.kite.scouter.domain.board.repository;

import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardsByCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardCustomRepo {

  Page<ResponseBoardsByCategory> getBoardsByCategory(Pageable pageable, Long categoryId);

}
