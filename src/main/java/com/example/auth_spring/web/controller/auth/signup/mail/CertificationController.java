package com.example.auth_spring.web.controller.auth.signup.mail;

import com.example.auth_spring.service.auth.signup.mail.CertificationService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.auth.signup.mail.CertificationRequestDto;
import com.example.auth_spring.web.dto.auth.signup.mail.CertificationResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/signup")
public class CertificationController {

    private final CertificationService certificationService;

    @PostMapping("/mail")
    public ResponseEntity<ResultDto<CertificationResponseDto>> mailSend(@RequestBody CertificationRequestDto requestDto) {
        CommonResponse<Object> commonResponse = certificationService.responseSendMail(requestDto);
        ResultDto<CertificationResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        result.setData((CertificationResponseDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @GetMapping("/{certificationCode}")
    public ResponseEntity<ResultDto<Void>> certificationCheck(@PathVariable String certificationCode) {
        CommonResponse<Object> commonResponse = certificationService.responseCertificationCheck(certificationCode);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
