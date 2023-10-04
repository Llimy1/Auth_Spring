package com.example.auth_spring.web.domain.product;



import com.example.auth_spring.web.dto.product.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByUserId(Long userId, Pageable pageable);

    Page<Product> findAll(Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "JOIN p.subCategory sc " +
            "JOIN sc.category c " +
            "WHERE c.name = ?1 ")
    Page<Product> findProductListByCategoryName(String categoryName, Pageable pageable);

    @Query("SELECT p FROM Product p " +
            "JOIN p.subCategory sc " +
            "WHERE p.name = ?1 ")
    Page<Product> findProductListBySubCategoryName(String subCategoryName, Pageable pageable);

    Page<Product> findAllByNameContaining(String keyword, Pageable pageable);

    Optional<Product> findByName(String productName);

}
