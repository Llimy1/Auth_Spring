package com.example.auth_spring.web.domain.brand;

import com.example.auth_spring.web.dto.brand.BrandResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    Optional<Brand> findByName(String brandName);

    @Query("SELECT NEW com.example.auth_spring.web.dto.brand.BrandResponseDto(b.name as brandName) " +
            "FROM Brand b " +
            "JOIN b.user u " +
            "WHERE u.email = :email ")
    List<BrandResponseDto> findBrandListByUserEmail(@Param("email") String email);

}
