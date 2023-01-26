package com.kite.scouter.domain.board.repository;

import com.kite.scouter.domain.board.dto.BoardDto.ResponseBoardsByCategory;
import com.kite.scouter.domain.board.model.Board;
import com.kite.scouter.global.utils.ObjectUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import static com.kite.scouter.domain.board.model.QBoard.board;

@Repository
public class BoardCustomRepoImpl extends QuerydslRepositorySupport implements BoardCustomRepo {

  private final JPAQueryFactory jpaQueryFactory;

  public BoardCustomRepoImpl(JPAQueryFactory jpaQueryFactory) {
    super(Board.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }
  private BooleanExpression searchByCategory(final Long categoryId) {
    if(!ObjectUtil.isEmpty(categoryId)){
      return board.category.categoryId.eq(categoryId);
    }
    return null;
  }

  private BooleanExpression searchByEnabledTrue() {
    return board.enable.isTrue();
  }

  @Override
  public Page<ResponseBoardsByCategory> getBoardsByCategory(
      Pageable pageable,
      Long categoryId) {

    List<Board> content = jpaQueryFactory
        .selectFrom(board)
        .where(
            searchByCategory(categoryId),
            searchByEnabledTrue()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(board.createdDt.desc())
        .fetch();

    long totalElement = jpaQueryFactory
        .select(board.boardId.count())
        .from(board)
        .where(
            searchByCategory(categoryId),
            searchByEnabledTrue()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .fetch().get(0);

    return new PageImpl<>(ResponseBoardsByCategory.from(content), pageable, totalElement);
  }
}
