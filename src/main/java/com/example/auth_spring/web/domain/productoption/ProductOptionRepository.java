package com.example.auth_spring.web.domain.productoption;



import com.example.auth_spring.web.dto.option.OptionResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

    @Query("SELECT NEW com.example.auth_spring.web.dto.option.OptionResponseDto(" +
            "o.name as optionName)" +
            "FROM ProductOption po " +
            "JOIN po.option o " +
            "JOIN o.user u " +
            "WHERE u.email = :email ")
    List<OptionResponseDto> findOptionListByUserEmail(@Param("email") String email);

    @Query("SELECT po " +
            "FROM ProductOption po " +
            "JOIN FETCH po.option o " +
            "JOIN FETCH po.product p " +
            "WHERE p.name = ?1 ")
    Optional<ProductOption> findProductOptionByProductName(String productName);
}
