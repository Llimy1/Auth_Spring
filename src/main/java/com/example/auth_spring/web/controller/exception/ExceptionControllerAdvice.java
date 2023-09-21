package com.example.auth_spring.web.controller.exception;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.common.ResultDto;
import com.example.auth_spring.web.exception.*;
import com.example.auth_spring.web.exception.IllegalStateException;
import io.jsonwebtoken.IncorrectClaimException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(NotFoundException nfe) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(nfe.getMessage(), HttpStatus.NOT_FOUND, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ExceptionHandler(NotFoundProviderException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(NotFoundProviderException nfp) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(nfp.getMessage(), HttpStatus.NOT_FOUND, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ExceptionHandler(JwtException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(JwtException jwe) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(jwe.getMessage(), HttpStatus.NOT_FOUND, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ExceptionHandler(IncorrectClaimException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(IncorrectClaimException icc) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(icc.getMessage(), HttpStatus.NOT_FOUND, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(UsernameNotFoundException unf) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(unf.getMessage(), HttpStatus.NOT_FOUND, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ResultDto<Void>> handleIllegalStateException(LoginException lie) {
        CommonResponse<Object> commonResponse = commonService.errorResponse(lie.getMessage(), HttpStatus.BAD_REQUEST, null);
        ResultDto<Void> result = ResultDto.in(commonResponse.getStatus(), commonResponse.getMessage());
        return ResponseEntity.status(commonResponse.getHttpStatus()).body(result);
    }
}
