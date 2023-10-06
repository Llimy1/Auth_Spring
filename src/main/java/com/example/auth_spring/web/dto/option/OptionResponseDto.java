package com.example.auth_spring.web.dto.option;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionResponseDto {

    private String optionName;

    @Builder
    public OptionResponseDto(String optionName) {
        this.optionName = optionName;
    }
}
