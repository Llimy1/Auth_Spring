package com.example.auth_spring.service.auth.withdrawal;

import com.example.auth_spring.security.jwt.service.TokenService;
import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.user.User;
import com.example.auth_spring.web.domain.user.UserRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class WithdrawalService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CommonService commonService;

    @Transactional
    public void withdrawal(String accessToken) {

        Long userId = tokenService.accessTokenUserId(accessToken);

        User user = userRepository.findById(userId)
                        .orElseThrow(() -> new NotFoundException(ErrorCode.USER_NOT_FOUND));

        userRepository.delete(user);
    }

    @Transactional
    public CommonResponse<Object> withdrawalResponse(String accessToken) {
        withdrawal(accessToken);

        return commonService.successResponse(SuccessCode.WITHDRAWAL_SUCCESS.getDescription(), HttpStatus.OK, null);
    }

}
