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
            "p.likeCount as likeCount," +
            "i.imageUrl as mainProductImageUrl) " +
            "FROM Product p " +
            "JOIN p.user u " +
            "JOIN p.brand b " +
            "JOIN p.viewList v " +
            "JOIN p.imageList i " +
            "WHERE u.email = :email " +
            "AND i.imageSequence = 1 ")
    Page<SellerProductResponseDto> findProductByUserEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(" +
            "p.id as productId," +
            "p.name as productName, " +
            "p.price as productPrice," +
            "b.name as brandName," +
            "p.isDiscount as isDiscount," +
            "p.discountRate as discountRate) " +
            "FROM Product p " +
            "JOIN p.brand b ")
    Page<ProductResponseDto> findProductAllList(Pageable pageable);

    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(" +
            "p.id as productId," +
            "p.name as productName, " +
            "p.price as productPrice, " +
            "b.name as brandName," +
            "p.isDiscount as isDiscount," +
            "p.discountRate as discountRate) " +
            "FROM Product p " +
            "LEFT JOIN p.subCategory sc " +
            "LEFT JOIN sc.category c " +
            "JOIN p.brand b " +
            "WHERE c.name = :categoryName " )
    Page<ProductResponseDto> findProductListByCategoryName(@Param("categoryName") String categoryName, Pageable pageable);


    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(" +
            "p.id as productId, " +
            "p.name as productName, " +
            "p.price as productPrice, " +
            "b.name as brandName, " +
            "p.isDiscount as isDiscount, " +
            "p.discountRate as discountRate) " +
            "FROM Product p " +
            "JOIN p.subCategory sc " +
            "JOIN p.brand b " +
            "WHERE sc.name = :subCategoryName")
    Page<ProductResponseDto> findProductListBySubCategoryName(@Param("subCategoryName") String subCategoryName, Pageable pageable);


    @Query("SELECT NEW com.example.auth_spring.web.dto.product.ProductResponseDto(" +
            "p.id as productId," +
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
