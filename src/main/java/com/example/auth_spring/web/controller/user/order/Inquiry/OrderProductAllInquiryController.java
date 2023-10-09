package com.example.auth_spring.web.controller.user.order.Inquiry;

import com.example.auth_spring.service.user.order.Inquiry.OrderProductAllInquiryService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.order.OrderProductAllListResponseDto;
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
public class OrderProductAllInquiryController {

    private final OrderProductAllInquiryService orderProductAllInquiryService;

    @ApiOperation(value = "주문 정보 리스트 API")
    @GetMapping("/order")
    public ResponseEntity<ResultDto<OrderProductAllListResponseDto>> orderProductAllList(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                                                         @RequestParam(value = "page", defaultValue = "1", required = false) int page,
                                                                                         @RequestParam(value = "size", defaultValue = "10", required = false) int size,
                                                                                         @RequestParam(value = "sortBy", defaultValue = "modifiedAt", required = false) String sortBy) {
        CommonResponse<Object> commonResponse = orderProductAllInquiryService.orderProductAllListResponse(bearerAccessToken, page, size, sortBy);
        ResultDto<OrderProductAllListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((OrderProductAllListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
