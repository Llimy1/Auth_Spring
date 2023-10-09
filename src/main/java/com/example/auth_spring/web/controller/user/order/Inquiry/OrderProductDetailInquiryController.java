package com.example.auth_spring.web.controller.user.order.Inquiry;

import com.example.auth_spring.service.user.order.Inquiry.OrderProductDetailInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.order.OrderProductDetailResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "Order APIs")
public class OrderProductDetailInquiryController {

    private final OrderProductDetailInquiryService orderProductDetailInquiryService;

    @ApiOperation(value = "주문 상세 정보 API")
    @GetMapping("/order/{orderName}")
    public ResponseEntity<ResultDto<OrderProductDetailResponseDto>> orderProductDetail(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                                                       @ApiParam(name = "orderName", value = "주문 번호", example = "aB8Xp3Kw") @PathVariable String orderName) {
        CommonResponse<Object> commonResponse = orderProductDetailInquiryService.orderProductDetailResponse(bearerAccessToken, orderName);
        ResultDto<OrderProductDetailResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((OrderProductDetailResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

}
