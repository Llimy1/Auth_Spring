package com.example.auth_spring.web.controller.user.mypage.addressinfo;

import com.example.auth_spring.service.user.mypage.addressinfo.AddressInfoService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.mypage.addressInfo.AddressInfoListResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "MyPage APIs")
public class AddressInfoController {

    private final AddressInfoService addressInfoService;

    @ApiOperation(value = "내 주소 정보 조회 API")
    @GetMapping("/addressInfo")
    public ResponseEntity<ResultDto<AddressInfoListResponseDto>> addressInfoCheck(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken) {
        CommonResponse<Object> commonResponse = addressInfoService.addressInfoResponse(bearerAccessToken);
        ResultDto<AddressInfoListResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((AddressInfoListResponseDto) commonResponse.getData());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
