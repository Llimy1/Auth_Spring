package com.example.auth_spring.web.controller.all.signup.mail;

import com.example.auth_spring.service.all.signup.mail.CertificationService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.dto.auth.signup.mail.CertificationRequestDto;
import com.example.auth_spring.web.dto.auth.signup.mail.CertificationResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/all")
@Api(tags = "Auth APIs")
public class CertificationController {

    private final CertificationService certificationService;

    @ApiOperation(value = "이메일 인증 번호 전송 API")
    @PostMapping("/signup/mail")
    public ResponseEntity<ResultDto<CertificationResponseDto>> mailSend(@RequestBody CertificationRequestDto requestDto) {
        CommonResponse<Object> commonResponse = certificationService.responseSendMail(requestDto);
        ResultDto<CertificationResponseDto> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        result.setData((CertificationResponseDto) commonResponse.getData());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ApiOperation(value = "인증 번호 불러오기 API", notes = "보내진 인증 번호가 일치 하는지 확인 후 반환")
    @GetMapping("/{certificationCode}")
    public ResponseEntity<ResultDto<Void>> certificationCheck(@ApiParam(name = "code", value = "인증 번호") @PathVariable String certificationCode) {
        CommonResponse<Object> commonResponse = certificationService.responseCertificationCheck(certificationCode);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());

        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
