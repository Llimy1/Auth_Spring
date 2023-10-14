package com.example.auth_spring.web.dto.option;

import com.example.auth_spring.web.domain.option.Option;
import com.example.auth_spring.web.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OptionRequestDto {

    private String size;
    private String color;

    @Builder
    public OptionRequestDto(String size, String color) {
        this.size = size;
        this.color = color;
    }


    public Option toOptionEntity(User user) {
        return Option.builder()
                .user(user)
                .size(size)
                .color(color)
                .build();
    }
}
