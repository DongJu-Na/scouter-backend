package com.kite.scouter.global.listener;

import com.kite.scouter.domain.board.model.Board;
import com.kite.scouter.domain.board.repository.BoardRepository;
import com.kite.scouter.domain.category.model.Category;
import com.kite.scouter.domain.category.service.CategoryService;
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
public class BoardLoaderListener implements ApplicationListener<ApplicationStartedEvent>, Ordered {

  private final CategoryService categoryService;

  private final UserService userService;

  private final BoardRepository boardRepository;

  @Override
  public int getOrder() {
    return 1;
  }

  @Override
  public void onApplicationEvent(@NotNull final ApplicationStartedEvent event) {

    Category category = categoryService.getCategoryById(1L);

    User user = userService.getUserById(1L);

    Board board = Board.builder()
      .boardTitle("게시판테스트")
      .boardContent("게시판내용")
      .category(category)
      .user(user)
      .build();

    Board board2 = Board.builder()
        .boardTitle("게시판테스트2")
        .boardContent("게시판내용2")
        .category(category)
        .user(user)
        .build();

    createBoardAssertNull(board);
    createBoardAssertNull(board2);
  }

  private void createBoardAssertNull(final Board board) {
    if (boardRepository.findByBoardTitle(board.getBoardTitle()).isEmpty()) {
      boardRepository.save(board);
    }
  }

}
