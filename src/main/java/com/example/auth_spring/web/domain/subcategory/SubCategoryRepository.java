package com.example.auth_spring.web.domain.subcategory;

import com.example.auth_spring.web.dto.subcategory.SubCategoryResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Long> {

    @Query("SELECT NEW com.example.auth_spring.web.dto.subcategory.SubCategoryResponseDto(" +
            "s.name as subCategoryName) " +
            "FROM SubCategory s " +
            "JOIN s.category c " +
            "WHERE c.name = ?1 ")
    List<SubCategoryResponseDto> findSubCategoryListByCategoryName(String categoryName);

    Optional<SubCategory> findByName(String subCategoryName);
}
