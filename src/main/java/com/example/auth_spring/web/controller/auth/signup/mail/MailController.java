package com.example.auth_spring.web.controller.auth.signup.mail;

import com.example.auth_spring.service.auth.signup.mail.MailService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.signup.mail.MailRequestDto;
import com.example.auth_spring.web.dto.signup.mail.MailResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/signup")
public class MailController {

    private final MailService mailService;

    @PostMapping("/mail")
    public ResponseEntity<ResultDto<MailResponseDto>> mailSend(@RequestBody MailRequestDto requestDto) {
        CommonResponse<Object> commonResponse = mailService.responseSendMail(requestDto);
        ResultDto<MailResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        result.setData((MailResponseDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
