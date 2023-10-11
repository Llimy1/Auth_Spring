package com.example.auth_spring.web.domain.image;

import com.example.auth_spring.web.dto.image.ImageResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {

    @Query("SELECT NEW com.example.auth_spring.web.dto.image.ImageResponseDto(" +
            "i.imageSequence as imageSequence," +
            "i.imageUrl as imageUrl)" +
            "FROM Image i " +
            "WHERE i.product.id = :productId ")
    List<ImageResponseDto> findAllImageList(@Param("productId") Long productId);

}
