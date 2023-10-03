package com.example.auth_spring.web.domain.product;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByUserId(Long userId, Pageable pageable);
    Page<Product> findAll(Pageable pageable);

    Optional<Product> findByName(String productName);

}
