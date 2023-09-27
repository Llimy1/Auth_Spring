package com.example.auth_spring.web.domain.address;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    Optional<Address> findByUserId(Long userId);
}
