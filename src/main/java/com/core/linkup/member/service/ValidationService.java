package com.core.linkup.member.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.common.utils.AuthCodeUtils;
import com.core.linkup.common.utils.EmailUtils;
import com.core.linkup.common.utils.RedisUtils;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.member.request.validate.EmailValidationRequest;
import com.core.linkup.member.request.validate.PasswordValidationRequest;
import com.core.linkup.member.request.validate.UsernameValidationRequest;
import com.core.linkup.security.MemberDetails;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationService {

    private final EmailUtils emailUtils;
    private final RedisUtils redisUtils;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthCodeUtils authCodeUtils;

    public void sendEmailAuthCodeByEmail(EmailValidationRequest request) {
        String subject = "LinkUp 이메일 인증번호";
        String authCode = authCodeUtils.createEmailAuthCode();

        validateEmail(request);
        try {
            emailUtils.sendEmail(request.email(), subject, authCode);
            redisUtils.saveEmailAuthCode(request.email(), authCode);
        } catch (MessagingException e) {
            log.error(e.getMessage());
        }
    }

    public Boolean verifyEmailAuthCode(String email, String authCode){
        return redisUtils.findEmailAuthCode(email).equals(authCode);
    }

    public long findCompanyIdByAuthCode(String authCode){
        return Integer.parseInt(redisUtils.findCompanyAuthCode(authCode));
    }

    public void validateEmail(EmailValidationRequest request){
        if (memberRepository.existsByEmail(request.email())){
            throw new BaseException(BaseResponseStatus.DUPLICATE_EMAIL);
        }
    }

    public void validateUsername(UsernameValidationRequest request){
        if (memberRepository.existsByUsername(request.username())){
            throw new BaseException(BaseResponseStatus.DUPLICATE_USERNAME);
        }
    }

    public void validatePassword(PasswordValidationRequest request,
                                 MemberDetails memberDetails){
        if (!passwordEncoder.matches(request.password(), memberDetails.getPassword())){
            throw new BaseException(BaseResponseStatus.INVALID_PASSWORD);
        }
    }
}
