package com.example.auth_spring.web.domain.cart;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    Page<Cart> findAllByUserId(Long userId, Pageable pageable);

    Optional<Cart> findByProductName(String productName);

}
