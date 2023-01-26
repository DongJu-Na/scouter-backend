package com.kite.scouter.domain.category.service.impl;

import com.kite.scouter.domain.category.model.Category;
import com.kite.scouter.domain.category.repository.CategoryRepository;
import com.kite.scouter.domain.category.service.CategoryService;
import com.kite.scouter.global.enums.ResponseCode;
import com.kite.scouter.global.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepo;

  @Override
  public Category getCategoryById(Long categoryId) {
    return categoryRepo.findById(categoryId)
        .orElseThrow(() -> BadRequestException.of(ResponseCode.CY0001,"category.id.not.find"));
  }

}
