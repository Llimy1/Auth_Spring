package com.example.auth_spring.web.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query("SELECT l " +
            "FROM Like l " +
            "JOIN l.user u " +
            "JOIN l.product p " +
            "WHERE u.id = :userId AND p.name = :productName ")
    Optional<Like> findLikeByProductNameAndUserId(@Param("userId") Long userId, @Param("productName") String productName);

}
