package com.example.auth_spring.web.domain.productoption;



import com.example.auth_spring.web.dto.option.OptionResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("SELECT po " +
            "FROM ProductOption po " +
            "JOIN FETCH po.option o " +
            "JOIN FETCH po.product p " +
            "WHERE p.name = :productName ")
    Optional<ProductOption> findProductOptionByProductName(@Param("productName") String productName);
}
