package com.example.auth_spring.service.auth.signup.mail;

import com.example.auth_spring.service.common.CommonService;
import com.example.auth_spring.type.ErrorCode;
import com.example.auth_spring.type.SuccessCode;
import com.example.auth_spring.web.domain.mail.Certification;
import com.example.auth_spring.web.domain.mail.CertificationRepository;
import com.example.auth_spring.web.dto.common.CommonResponse;
import com.example.auth_spring.web.dto.signup.mail.CertificationRequestDto;
import com.example.auth_spring.web.dto.signup.mail.CertificationResponseDto;
import com.example.auth_spring.web.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CertificationService {

    private final CommonService commonService;
    private final CertificationRepository certificationRepository;
    private final JavaMailSender javaMailSender;
    private static final String senderEmail = "alsgur990104@gmail.com";

    // 인증 코드 전송(메일)
    @Transactional
    public String sendMail(CertificationRequestDto certificationRequestDto) {

        String authNum = createCode();

        Optional<Certification> certificationOptional = certificationRepository.findByEmail(certificationRequestDto.getEmail());

        if (certificationOptional.isPresent()) {
            Certification certification = certificationOptional.get();
            certification.codeUpdate(authNum);
        } else {
            certificationRepository.save(Certification.builder()
                            .email(certificationRequestDto.getEmail())
                            .code(authNum)
                            .build());
        }

        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();

            mimeMessage.setRecipients(Message.RecipientType.TO, certificationRequestDto.getEmail()); // 보내는 대상
            mimeMessage.setSubject("이메일 인증을 위한 인증 코드 발송"); // 제목

            String msgg = "";
            msgg += "<div style='margin:100px;'>";
            msgg += "<h1> 인증코드 입니다.</h1>";
            msgg += "<br>";
            msgg += "<p>아래 코드를 회원가입 창으로 돌아가 입력해주세요<p>";
            msgg += "<br>";
            msgg += "<div align='center' style='border:1px solid black; font-family:verdana';>";
            msgg += "<h3 style='color:blue;'>회원가입 인증 코드입니다.</h3>";
            msgg += "<div style='font-size:130%'>";
            msgg += "인증번호 : <strong>";
            msgg += authNum + "</strong><div><br/> "; // 메일에 인증번호 넣기
            msgg += "</div>";

            mimeMessage.setText(msgg, "utf-8", "html"); // 내용, charset 타입, subtype
            mimeMessage.setFrom(new InternetAddress(senderEmail, "회원가입 인증번호")); // 보내는 사람의 이메일 주소, 보내는 사람 이름

            javaMailSender.send(mimeMessage);

            return authNum;
        } catch (MessagingException mie) {
            throw new RuntimeException(ErrorCode.MESSAGING_EXCEPTION.getDescription());
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException(ErrorCode.UNSUPPORTED_ENCODING.getDescription());
        }
    }



    // API 반환 (POST)
    @Transactional
    public CommonResponse<Object> responseSendMail(CertificationRequestDto certificationRequestDto) {
        String code = sendMail(certificationRequestDto);

        return commonService.successResponse(SuccessCode.CERTIFICATION_SEND_SUCCESS.getDescription(), HttpStatus.OK, new CertificationResponseDto(code));
    }

    // 인증 코드가 일치하는지 (true, false) API 반환
    public CommonResponse<Object> responseCertificationCheck(String certificationCode) {
        certificationRepository.findByCode(certificationCode).orElseThrow(() ->
                new NotFoundException(ErrorCode.CERTIFICATION_CODE_INCONSISTENCY));

        return commonService.successResponse(SuccessCode.CERTIFICATION_CHECK.getDescription(), HttpStatus.OK, null);
    }


    // 랜덤 인증 코드 전송
    private String createCode() {
        StringBuilder key = new StringBuilder();
        Random rnd = new Random();

        for (int i = 0; i < 8; i++) { // 인증코드 8자리
            int index = rnd.nextInt(3); // 0~2 까지 랜덤, rnd 값에 따라서 아래 switch 문이 실행됨

            switch (index) {
                case 0:
                    key.append((char) ((int) (rnd.nextInt(26)) + 97));
                    // a~z (ex. 1+97=98 => (char)98 = 'b')
                    break;
                case 1:
                    key.append((char) ((int) (rnd.nextInt(26)) + 65));
                    // A~Z
                    break;
                case 2:
                    key.append((rnd.nextInt(10)));
                    // 0~9
                    break;
            }
        }
        return key.toString();
    }


}
