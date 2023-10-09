package com.example.auth_spring.web.domain.product;



import com.example.auth_spring.web.dto.product.ProductResponseDto;
import com.example.auth_spring.web.dto.seller.SellerProductResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT NEW com.example.auth_spring.web.dto.seller.SellerProductResponseDto(" +
            "p.name as productName," +
            "p.price as productPrice," +
            "b.name as brandName," +
            "p.isDiscount as isDiscount," +
            "p.discountRate as discountRate," +
            "v.count as viewCount," +
            "p.likeCount as likeCount) " +
            "FROM Product p " +
            "JOIN p.user u " +
            "JOIN p.brand b " +
            "JOIN p.viewList v " +
            "ON p.id = v.product.id " +
            "WHERE u.email = :email ")
    Page<SellerProductResponseDto> findProductByUserEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(" +
            "p.name as productName, " +
            "p.price as productPrice," +
            "b.name as brandName," +
            "p.isDiscount as isDiscount," +
            "p.discountRate as discountRate) " +
            "FROM Product p " +
            "JOIN p.brand b ")
    Page<ProductResponseDto> findProductAllList(Pageable pageable);

    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(" +
            "p.name as productName, " +
            "p.price as productPrice, " +
            "b.name as brandName," +
            "p.isDiscount as isDiscount," +
            "p.discountRate as discountRate) " +
            "FROM Product p " +
            "JOIN p.subCategory sc " +
            "JOIN sc.category c " +
            "JOIN p.brand b " +
            "WHERE c.name = ?1 ")
    Page<ProductResponseDto> findProductListByCategoryName(String categoryName, Pageable pageable);

    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(" +
            "p.name as productName, " +
            "p.price as productPrice, " +
            "b.name as brandName," +
            "p.isDiscount as isDiscount," +
            "p.discountRate as discountRate) " +
            "FROM Product p " +
            "JOIN p.subCategory sc " +
            "JOIN p.brand b " +
            "WHERE p.name = ?1 ")
    Page<ProductResponseDto> findProductListBySubCategoryName(String subCategoryName, Pageable pageable);


    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(" +
            "p.name as productName, " +
            "p.price as productPrice, " +
            "b.name as brandName," +
            "p.isDiscount as isDiscount," +
            "p.discountRate as discountRate) " +
            "FROM Product p " +
            "JOIN p.brand b " +
            "WHERE p.name LIKE %:keyword% ")
    Page<ProductResponseDto> findProductListBySearchName(@Param("keyword")String keyword, Pageable pageable);

    Optional<Product> findByName(String productName);

}
