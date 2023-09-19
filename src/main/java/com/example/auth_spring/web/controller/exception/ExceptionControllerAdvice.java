package com.example.auth_spring.web.controller.exception;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.exception.IllegalStateException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

@RestControllerAdvice
@RequiredArgsConstructor
public class ExceptionControllerAdvice {

    private final CommonService commonService;


    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(IllegalStateException ise) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(ise.getMessage(), HttpStatus.BAD_REQUEST, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ExceptionHandler(javax.mail.MessagingException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(MessagingException mie) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(mie.getMessage(), HttpStatus.BAD_REQUEST, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ExceptionHandler(UnsupportedEncodingException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(UnsupportedEncodingException uee) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(uee.getMessage(), HttpStatus.BAD_REQUEST, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
