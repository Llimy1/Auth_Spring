package com.example.auth_spring.service.all.signup.exist;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.IllegalStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignupExistService {

    private final UserRepository userRepository;
    private final CommonService commonService;

    // 닉네임 중복 확인
    public CommonResponse<Object> nicknameExist(String nickname) {
        userRepository.findByNickname(nickname).ifPresent(a -> {
            throw new IllegalStateException(ErrorCode.NICKNAME_THAT_EXIST);
        });

        return commonService.successResponse(SuccessCode.NICKNAME_USE_EXIST.getDescription(), HttpStatus.OK, null);
    }

    // 이메일 중복 확인
    public CommonResponse<Object> emailExist(String email) {
        userRepository.findByEmail(email).ifPresent(a -> {
            throw new IllegalStateException(ErrorCode.EMAIL_THAT_EXIST);
        });

        return commonService.successResponse(SuccessCode.EMAIL_USE_EXIST.getDescription(), HttpStatus.OK, null);
    }

    // 핸드폰 번호 중복 확인
    public CommonResponse<Object> phoneNumberExist(String phoneNumber) {
        userRepository.findByPhoneNumber(phoneNumber).ifPresent(a -> {
            throw new IllegalStateException(ErrorCode.PHONE_NUMBER_THAT_EXIST);
        });

        return commonService.successResponse(SuccessCode.PHONE_NUMBER_USE_EXIST.getDescription(), HttpStatus.OK, null);
    }

}
