package com.example.auth_spring.web.domain.cart;

import com.example.auth_spring.web.dto.cart.CartResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {


    @Query("SELECT NEW com.example.auth_spring.web.dto.cart.CartResponseDto(" +
            "p.product.name as productName, " +
            "p.product.price as productPrice," +
            "b.name as brandName) " +
            "FROM Cart c " +
            "JOIN c.user u " +
            "JOIN c.product p " +
            "JOIN p.brand b " +
            "WHERE u.email = :email ")
    Page<CartResponseDto> findCartByUserEmail(@Param("email") String email, Pageable pageable);

    @Query("SELECT c " +
            "FROM Cart c " +
            "JOIN FETCH c.user u " +
            "JOIN FETCH c.product p " +
            "WHERE p.name = ?1 ")
    Optional<Cart> findByProductName(String productName);

}
