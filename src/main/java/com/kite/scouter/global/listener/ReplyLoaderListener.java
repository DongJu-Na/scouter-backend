package com.kite.scouter.global.listener;

import com.kite.scouter.domain.board.model.Board;
import com.kite.scouter.domain.board.repository.BoardRepository;
import com.kite.scouter.domain.board.service.BoardService;
import com.kite.scouter.domain.category.model.Category;
import com.kite.scouter.domain.category.service.CategoryService;
import com.kite.scouter.domain.reply.model.Reply;
import com.kite.scouter.domain.reply.repository.ReplyRepository;
import com.kite.scouter.domain.user.model.User;
import com.kite.scouter.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.wildfly.common.annotation.NotNull;

@Component
@RequiredArgsConstructor
public class ReplyLoaderListener implements ApplicationListener<ApplicationStartedEvent>, Ordered {

  private final BoardService boardService;

  private final UserService userService;

  private final ReplyRepository replyRepository;

  @Override
  public int getOrder() {
    return 2;
  }

  @Override
  public void onApplicationEvent(@NotNull final ApplicationStartedEvent event) {

    Board board = boardService.getBoardById(1L);

    User user = userService.getUserById(1L);

    Reply reply1 = Reply.builder()
        .replyContent("댓글 테스트1")
        .user(user)
        .board(board)
        .build();

    Reply reply2 = Reply.builder()
        .replyContent("댓글 테스트2")
        .user(user)
        .board(board)
        .build();



    createReplyAssertNull(reply1);
    createReplyAssertNull(reply2);
  }

  private void createReplyAssertNull(final Reply reply) {
    if (replyRepository.findByReplyContent(reply.getReplyContent()).isEmpty()) {
      replyRepository.save(reply);
    }
  }

}
