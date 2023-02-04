package com.kite.scouter.domain.reply.repository;

import static com.kite.scouter.domain.reply.model.QReply.reply;

import com.kite.scouter.domain.reply.dto.ReplyDto.ResponseReplysByBoardId;
import com.kite.scouter.domain.reply.model.Reply;
import com.kite.scouter.global.utils.ObjectUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

@Repository
public class ReplyCustomRepoImpl extends QuerydslRepositorySupport implements ReplyCustomRepo {

  private final JPAQueryFactory jpaQueryFactory;

  public ReplyCustomRepoImpl(JPAQueryFactory jpaQueryFactory) {
    super(Reply.class);
    this.jpaQueryFactory = jpaQueryFactory;
  }
  private BooleanExpression searchByBoard(final Long boardId) {
    if(!ObjectUtil.isEmpty(boardId)){
      return reply.board.boardId.eq(boardId);
    }
    return null;
  }

  private BooleanExpression searchByEnabledTrue() {
    return reply.enable.isTrue();
  }

  @Override
  public Page<ResponseReplysByBoardId> getReplysByBoardId(
      Pageable pageable,
      Long boardId) {

    List<Reply> content = jpaQueryFactory
        .selectFrom(reply)
        .where(
            searchByBoard(boardId),
            searchByEnabledTrue()
        )
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize())
        .orderBy(reply.createdDt.desc())
        .fetch();

    long totalElement = jpaQueryFactory
        .select(reply.replyId.count())
        .from(reply)
        .where(
            searchByBoard(boardId),
            searchByEnabledTrue()
        )
        .fetch().get(0);

    return new PageImpl<>(ResponseReplysByBoardId.from(content), pageable, totalElement);
  }
}
