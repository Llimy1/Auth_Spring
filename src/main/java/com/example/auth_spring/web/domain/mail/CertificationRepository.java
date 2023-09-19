package com.example.auth_spring.web.domain.mail;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CertificationRepository extends JpaRepository<Certification, Long> {
    Optional<Certification> findByEmail(String email);
    Optional<Certification> findByCode(String code);
}
