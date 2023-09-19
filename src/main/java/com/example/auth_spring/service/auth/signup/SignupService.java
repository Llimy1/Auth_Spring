package com.example.auth_spring.service.auth.signup;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.address.AddressRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.signup.SignupRequestDto;
import com.example.auth_spring.web.dto.signup.SignupResponseDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import com.example.auth_spring.type.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignupService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CommonService commonService;

    protected Long signup(SignupRequestDto signupRequestDto) {

        userRepository.findByNickname(signupRequestDto.getNickname()).ifPresent(a -> {
            throw new IllegalStateException(ErrorCode.NICKNAME_THAT_EXIST);
        });

        userRepository.findByEmail(signupRequestDto.getEmail()).ifPresent(a -> {
            throw new IllegalStateException(ErrorCode.EMAIL_THAT_EXIST);
        });

        userRepository.findByPhoneNumber(signupRequestDto.getPhoneNumber()).ifPresent(a -> {
            throw new IllegalStateException(ErrorCode.PHONE_NUMBER_THAT_EXIST);
        });


        User user = signupRequestDto.toUserEntity();
        Long userId = userRepository.save(user).getId();

        Address address = signupRequestDto.toAddressEntity(user);
        addressRepository.save(address);

        return userId;
    }

    public CommonResponse<Object> signupResponse(SignupRequestDto signupRequestDto) {
        Long userId = signup(signupRequestDto);

        return commonService.successResponse(SuccessCode.SIGNUP_SUCCESS.getDescription(), HttpStatus.CREATED, new SignupResponseDto(userId));
    }
}
