package com.example.auth_spring.web.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor(staticName = "in")
public class ResultDto<Data> {

    private final String status;
    private final String message;
    private Data data;


}
