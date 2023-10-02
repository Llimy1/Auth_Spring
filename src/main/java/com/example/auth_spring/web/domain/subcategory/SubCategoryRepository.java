package com.example.auth_spring.web.domain.subcategory;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    List<SubCategory> findAllByCategoryId(Long categoryId);

    Optional<SubCategory> findByName(String subCategoryName);
}
