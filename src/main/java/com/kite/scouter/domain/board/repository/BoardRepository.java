package com.kite.scouter.domain.board.repository;

import com.kite.scouter.domain.board.model.Board;
import com.kite.scouter.domain.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board,Long> {

}
