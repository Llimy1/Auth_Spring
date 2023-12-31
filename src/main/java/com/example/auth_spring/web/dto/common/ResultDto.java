package com.example.auth_spring.web.dto.common;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "in")
public class ResultDto<Data> {


    @ApiModelProperty(example = "응답 성공 여부")
    private final String status;

    @ApiModelProperty(example = "응답 메세지")
    private final String message;

    @ApiModelProperty(example = "응답 데이터")
    @Setter
    private Data data;


}
