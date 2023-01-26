package com.kite.scouter.domain.category.repository;

import com.kite.scouter.domain.category.model.Category;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

  Optional<Category> findByCategoryName(String categoryName);

}
