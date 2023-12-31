package com.example.auth_spring.service.all.signup;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.address.AddressRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.auth.signup.BasicSignupRequestDto;
import com.example.auth_spring.web.dto.auth.signup.UserIdResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class BasicSignupService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CommonService commonService;
    private final PasswordEncoder passwordEncoder;


    // 자체 회원 가입
    @Transactional
    public Long basicSignup(BasicSignupRequestDto basicSignupRequestDto) {

        User user = basicSignupRequestDto.toBasicUserEntity();
        user.passwordEncode(passwordEncoder);

        Long userId = userRepository.save(user).getId();

        Address address = basicSignupRequestDto.toAddressEntity(user);
        addressRepository.save(address);

        return userId;
    }

    // API 반환
    @Transactional
    public CommonResponse<Object> signupResponse(BasicSignupRequestDto basicSignupRequestDto) {
        Long userId = basicSignup(basicSignupRequestDto);

        return commonService.successResponse(SuccessCode.BASIC_SIGNUP_SUCCESS.getDescription(), HttpStatus.CREATED, new UserIdResponseDto(userId));
    }
}
