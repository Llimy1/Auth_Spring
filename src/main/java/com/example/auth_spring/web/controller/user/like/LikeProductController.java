package com.example.auth_spring.web.controller.user.like;

import com.example.auth_spring.service.user.like.LikeProductService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
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
@Api(tags = "Like APIs")
public class LikeProductController {

    private final LikeProductService likeProductService;

    @ApiOperation(value = "좋아요 API")
    @PostMapping("/like/{productName}")
    public ResponseEntity<ResultDto<Void>> likeProduct(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                       @ApiParam(name = "productName", value = "상품 이름", example = "나이키 맨투") @PathVariable("productName") String productName) {

        CommonResponse<Object> commonResponse = likeProductService.likeProductResponse(bearerAccessToken, productName);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }


}
