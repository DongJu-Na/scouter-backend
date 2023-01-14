package com.kite.scouter.global.core;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
public class PageWrapper<T> {

  private List<T> content;
  private int page;
  private int size;
  private int totalPages;
  private long totalElements;
  private boolean first;
  private boolean last;

  private PageWrapper(final Page<T> page) {
    this.content = page.getContent();
    this.page = page.getNumber();
    this.size = page.getSize();
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
    this.first = page.isFirst();
    this.last = page.isLast();
  }

  public static <T> PageWrapper of(final Page<T> page) {
    return new PageWrapper<>(page);
  }

}
