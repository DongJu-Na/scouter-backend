package com.kite.scouter.domain.board.repository;

import com.kite.scouter.domain.board.model.Board;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long>, BoardCustomRepo {

  Optional<Board> findByBoardId(Long boardId);
  Optional<Board> findByBoardIdAndEnableTrue(Long boardId);
  Optional<Board> findByBoardTitle(String boardTitle);
}
