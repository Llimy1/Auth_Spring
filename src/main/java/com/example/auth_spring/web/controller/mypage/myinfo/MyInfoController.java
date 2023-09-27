package com.example.auth_spring.web.controller.mypage.myinfo;

import com.example.auth_spring.service.mypage.myinfo.MyInfoService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.mypage.myinfo.MyInfoResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
@Api(tags = "MyPage APIs")
public class MyInfoController {

    private final MyInfoService myInfoService;

    @ApiOperation(value = "내 정보 조회 API")
    @GetMapping("/user/myInfo")
    public ResponseEntity<ResultDto<MyInfoResponseDto>> myInfoCheck(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken) {
        CommonResponse<Object> commonResponse = myInfoService.myInfoResponse(bearerAccessToken);

        ResultDto<MyInfoResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        result.setData((MyInfoResponseDto) commonResponse.getData());


        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
