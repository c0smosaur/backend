package com.core.linkup.member.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.common.utils.EmailUtils;
import com.core.linkup.common.utils.RedisUtils;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.member.request.validate.EmailValidationRequest;
import com.core.linkup.member.request.validate.PasswordValidationRequest;
import com.core.linkup.member.request.validate.UsernameValidationRequest;
import com.core.linkup.security.MemberDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerificationService {

    private final EmailUtils emailUtils;
    private final RedisUtils redisUtils;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void sendEmailAuthCodeByEmail(EmailValidationRequest request) {
        String subject = "LinkUp 이메일 인증번호";
        String authCode = createAuthCode();
        try{
            validateEmail(request);
            emailUtils.sendEmail(request.email(), subject, authCode);
            redisUtils.saveAuthCode(request.email(), authCode);
        } catch (Exception e) {
            log.error("messaging error");
            throw new BaseException(BaseResponseStatus.EMAIL_ERROR);
        }
    }

    private String createAuthCode() {
        int len = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder authCode = new StringBuilder();
            for (int i = 0; i < len; i++) {
                authCode.append(random.nextInt(10));
            }
            return authCode.toString();
        } catch (NoSuchAlgorithmException e) {
            log.debug("MemberService.createAuthCode() met an exception");
            throw new BaseException(BaseResponseStatus.REGISTRATION_AUTHCODE_ERROR);
        }
    }

    public Boolean verifyCode(String email, String authCode){
        return redisUtils.findAuthCode(email).equals(authCode);
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
