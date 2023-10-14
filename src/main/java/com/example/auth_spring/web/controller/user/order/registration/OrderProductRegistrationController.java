package com.example.auth_spring.web.controller.user.order.registration;

import com.example.auth_spring.service.user.order.registration.OrderProductRegistrationService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.order.OrderProductRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "Order APIs")
public class OrderProductRegistrationController {

    private final OrderProductRegistrationService orderProductRegistrationService;

    @ApiOperation(value = "주문 정보 저장 API")
    @PostMapping("/order")
    public ResponseEntity<ResultDto<Void>> orderProductRegistration(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                                    @RequestBody OrderProductRequestDto orderProductRequestDto) {
        CommonResponse<Object> commonResponse = orderProductRegistrationService.orderProductRegistrationResponse(bearerAccessToken, orderProductRequestDto);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
