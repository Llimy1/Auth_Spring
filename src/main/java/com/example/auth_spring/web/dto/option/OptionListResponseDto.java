package com.example.auth_spring.web.dto.option;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class OptionListResponseDto {

    List<OptionResponseDto> optionList;

    @Builder
    public OptionListResponseDto(List<OptionResponseDto> optionList) {
        this.optionList = optionList;
    }
}
