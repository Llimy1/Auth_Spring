package com.example.auth_spring.web.controller.admin.category.delete;


import com.example.auth_spring.service.admin.category.delete.CategoryDeleteService;
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
@RequestMapping("/api/v1/admin")
@Api(tags = "Admin APIs")
public class CategoryDeleteController {

    private final CategoryDeleteService categoryDeleteService;


    @ApiOperation(value = "카테고리 삭제 API")
    @DeleteMapping("/category/delete/{categoryName}")
    public ResponseEntity<ResultDto<Void>> categoryDelete(@ApiIgnore @RequestHeader("Authorization") String bearerAccessToken,
                                                         @ApiParam(name = "categoryName", value = "카테고리명", example = "의류") @PathVariable String categoryName) {
        CommonResponse<Object> commonResponse = categoryDeleteService.categoryDeleteResponse(bearerAccessToken, categoryName);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
