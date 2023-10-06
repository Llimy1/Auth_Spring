package com.example.auth_spring.web.dto.option;

import com.example.auth_spring.web.domain.option.Option;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionRequestDto {

    private String optionName;

    @Builder
    public OptionRequestDto(String optionName) {
        this.optionName = optionName;
    }

    public Option toOptionEntity(User user) {
        return Option.builder()
                .user(user)
                .name(optionName)
                .build();
    }
}
