package com.example.auth_spring.web.domain.login;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepoistory extends JpaRepository<Login, Long> {
}
