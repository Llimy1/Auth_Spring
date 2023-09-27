package com.example.auth_spring.service.auth.signup;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.address.Address;
import com.example.auth_spring.web.domain.address.AddressRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.auth.signup.OAuth2SignupRequestDto;
import com.example.auth_spring.web.dto.common.UserIdResponseDto;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OAuth2SignupService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final CommonService commonService;

    @Transactional
    public Long oAuth2Signup(String email, OAuth2SignupRequestDto oAuth2SignupRequestDto) {

        String nickname = oAuth2SignupRequestDto.getNickname();
        String phoneNumber = oAuth2SignupRequestDto.getPhoneNumber();
        String gender= oAuth2SignupRequestDto.getGender();
        String introduce = oAuth2SignupRequestDto.getIntroduce();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        user.oauth2UserUpdate(nickname, phoneNumber, gender, introduce);

        Address address = oAuth2SignupRequestDto.toOAuth2AddressEntity(user);
        addressRepository.save(address);


        return user.getId();
    }

    @Transactional
    public CommonResponse<Object> oauth2SignupResponse(String email, OAuth2SignupRequestDto oAuth2SignupRequestDto) {
        Long userId = oAuth2Signup(email, oAuth2SignupRequestDto);

        return commonService.successResponse(SuccessCode.OAUTH2_SIGNUP_SUCCESS.getDescription(), HttpStatus.CREATED, new UserIdResponseDto(userId));
    }


}
