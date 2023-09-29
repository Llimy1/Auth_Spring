package com.example.auth_spring.web.domain.address;

import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<AddressInfoResponseDto> findAllByUserId(Long userId);
}
