package com.kite.scouter.domain.category.repository;

import com.kite.scouter.domain.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
