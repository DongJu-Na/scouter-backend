package com.kite.scouter.domain.category.controlle;

import static org.springframework.http.HttpStatus.OK;

import com.kite.scouter.domain.category.model.Category;
import com.kite.scouter.domain.category.repository.CategoryRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categorys")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryRepository categoryRepository;

  @GetMapping
  @ResponseStatus(OK)
  public ResponseEntity<List<Category>> seyHello() {
    return ResponseEntity.ok(categoryRepository.findAll());
  }

}
