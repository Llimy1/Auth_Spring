package com.example.auth_spring.web.domain.option;

import com.example.auth_spring.web.dto.option.OptionResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("SELECT NEW com.example.auth_spring.web.dto.option.OptionResponseDto(" +
            "o.size as size," +
            "o.color as color) " +
            "FROM Option o " +
            "JOIN o.productOption po " +
            "JOIN po.product p " +
            "WHERE p.name = :productName ")
    List<OptionResponseDto> findOptionListByProductName(@Param("productName") String productName);
}
