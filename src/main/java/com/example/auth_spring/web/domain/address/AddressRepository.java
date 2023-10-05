package com.example.auth_spring.web.domain.address;

import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT NEW com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoResponseDto(" +
            "ad.zipCode as zipCode, " +
            "ad.streetAddress as streetAddress, " +
            "ad.detailAddress as detailAddress) " +
            "FROM Address ad " +
            "WHERE ad.user.id = :userId ")
    List<AddressInfoResponseDto> findAddressListByUserId(@Param("userId") Long userId);

}
