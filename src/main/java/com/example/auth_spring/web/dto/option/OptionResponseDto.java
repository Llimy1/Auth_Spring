package com.example.auth_spring.web.dto.option;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionResponseDto {

    private String size;
    private String color;

    @Builder
    public OptionResponseDto(String size, String color) {
        this.size = size;
        this.color = color;
    }
}
