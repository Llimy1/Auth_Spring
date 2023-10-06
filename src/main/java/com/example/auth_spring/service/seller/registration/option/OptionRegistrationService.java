package com.example.auth_spring.service.seller.registration.option;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.service.user.token.TokenService;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.option.Option;
import com.example.auth_spring.web.domain.option.OptionRepository;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.option.OptionRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OptionRegistrationService {

    private final TokenService tokenService;
    private final CommonService commonService;
    private final OptionRepository optionRepository;

    // 상품 옵션 저장
    public void optionRegistration(String bearerAccessToken, OptionRequestDto optionRequestDto) {

        tokenService.accessTokenExpiration(bearerAccessToken);

        User user = tokenService.findUser(bearerAccessToken);

        Option option = optionRequestDto.toOptionEntity(user);

        optionRepository.save(option);
    }

    // API 반환
    public CommonResponse<Object> optionRegistrationResponse(String bearerAccessToken, OptionRequestDto optionRequestDto) {

        optionRegistration(bearerAccessToken, optionRequestDto);

        return commonService.successResponse(SuccessCode.OPTION_REGISTRATION_SUCCESS.getDescription(), HttpStatus.CREATED, null);
    }
}
