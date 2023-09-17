package com.example.auth_spring.web.controller.auth.signup;

import com.example.auth_spring.service.auth.signup.SignupService;
import com.example.auth_spring.web.dto.SignupRequestDto;
import com.example.auth_spring.web.dto.SignupResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SignupController {

    private final SignupService signupService;

    @PostMapping("/signup")
    public Long signup(@RequestBody SignupRequestDto signupRequestDto) {

        return signupService.signup(signupRequestDto);
    }
}
