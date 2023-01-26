package com.kite.scouter.global.listener;

import com.kite.scouter.domain.category.model.Category;
import com.kite.scouter.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.wildfly.common.annotation.NotNull;

@Component
@RequiredArgsConstructor
public class CategoryLoaderListener implements ApplicationListener<ApplicationStartedEvent>, Ordered {


  private final CategoryRepository categoryRepository;

  @Override
  public int getOrder() {
    return 0;
  }

  @Override
  public void onApplicationEvent(@NotNull final ApplicationStartedEvent event) {

    Category category1 = Category.builder()
        .categoryName("자유게시판")
        .build();

    Category category2 = Category.builder()
        .categoryName("공지사항")
        .build();

    createUserAssertNull(category1);
    createUserAssertNull(category2);
  }

  private void createUserAssertNull(final Category category) {
    if (categoryRepository.findByCategoryName(category.getCategoryName()).isEmpty()) {
      categoryRepository.save(category);
    }
  }

}
