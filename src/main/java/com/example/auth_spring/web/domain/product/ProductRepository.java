package com.example.auth_spring.web.domain.product;



import com.example.auth_spring.web.dto.product.ProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByUserId(Long userId, Pageable pageable);


    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(p.name as productName, p.price as productPrice) " +
            "FROM Product p " +
            "JOIN p.user u " +
            "WHERE u.email = :email ")
    Page<ProductResponseDto> findProductByUserEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(p.name as productName, p.price as productPrice) " +
            "FROM Product p ")
    Page<ProductResponseDto> findProductAllList(Pageable pageable);

    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(p.name as productName, p.price as productPrice) " +
            "FROM Product p " +
            "JOIN p.subCategory sc " +
            "JOIN sc.category c " +
            "WHERE c.name = ?1 ")
    Page<ProductResponseDto> findProductListByCategoryName(String categoryName, Pageable pageable);

    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(p.name as productName, p.price as productPrice) " +
            "FROM Product p " +
            "JOIN p.subCategory sc " +
            "WHERE p.name = ?1 ")
    Page<ProductResponseDto> findProductListBySubCategoryName(String subCategoryName, Pageable pageable);

    Page<Product> findAllByNameContaining(String keyword, Pageable pageable);

    Optional<Product> findByName(String productName);

}
