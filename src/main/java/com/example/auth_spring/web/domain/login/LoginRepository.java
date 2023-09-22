package com.example.auth_spring.web.domain.login;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRepository extends JpaRepository<Login, Long> {
    Optional<Login> findByRefreshToken(String refreshToken);
    Optional<Login> findByUserId(Long userId);
}
