package com.core.linkup.member.service;

import com.core.linkup.common.service.RedisService;
import com.core.linkup.member.converter.MemberConverter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.entity.enums.GenderType;
import com.core.linkup.member.entity.enums.IndustryType;
import com.core.linkup.member.entity.enums.OccupationType;
import com.core.linkup.member.entity.enums.RoleType;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.member.request.LoginRequest;
import com.core.linkup.member.request.RegistrationRequest;
import com.core.linkup.member.request.validate.EmailValidateRequest;
import com.core.linkup.member.request.validate.PasswordValidateRequest;
import com.core.linkup.member.request.validate.UsernameValidateRequest;
import com.core.linkup.member.response.MemberResponse;
import com.core.linkup.security.MemberDetails;
import com.core.linkup.security.Tokens;
import com.core.linkup.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import static com.core.linkup.security.jwt.JwtProperties.ACCESS_TOKEN;
import static com.core.linkup.security.jwt.JwtProperties.REFRESH_TOKEN;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisService redisService;
    private final MailService mailService;

    public void sendCodeByEmail(EmailValidateRequest request) {
        String subject = "LinkUp 이메일 인증번호";
        String authCode = createAuthCode();
        try{
            validateEmail(request);
            mailService.sendEmail(request.email(), subject, authCode);
            redisService.saveAuthCode(request.email(), authCode);
        } catch (Exception e) {
            // TODO: 예외처리
            log.error("messaging error");
            e.printStackTrace();
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
            throw new RuntimeException(e);
        }
    }

    public Boolean verifyCode(String email, String authCode){
        return redisService.findAuthCode(email).equals(authCode);
    }

    public void validateEmail(EmailValidateRequest request){
        if (memberRepository.existsByEmail(request.email())){
            throw new RuntimeException("Email already in use");
        }
    }

    public void validateUsername(UsernameValidateRequest request){
        if (memberRepository.existsByUsername(request.username())){
            throw new RuntimeException("Username already in use");
        }
    }

    public void validatePassword(PasswordValidateRequest request,
                                 MemberDetails memberDetails){
        if (!passwordEncoder.matches(request.password(), memberDetails.getPassword())){
            throw new RuntimeException("Invalid password");
        }
    }

    public MemberResponse registerUser(RegistrationRequest request){

        if (request.getEmailVerified()){
            Member member = Member.builder()
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .name(request.getName())
                    .birthday(request.getBirthday())
                    .gender(GenderType.fromKor(request.getGender()))
                    .phoneNumber(request.getPhoneNumber())
                    .username(request.getUsername())
                    .industry(IndustryType.fromKor(request.getIndustry()))
                    .occupation(OccupationType.fromKor(request.getOccupation()))
                    .role(RoleType.ROLE_USER)
                    .build();

            Member savedMember = memberRepository.save(member);

            return memberConverter.toMemberResponse(savedMember);
        } else {
            throw new RuntimeException("Email not verified");
        }
    }

    public MemberResponse login(LoginRequest request){
        Member member = memberRepository.findByUserEmail(request.getEmail(), "Check ID and password");

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new RuntimeException("Check ID and password");
        }
        return getMemberResponseAndTokens(member);
    }

    public MemberResponse getMemberResponseAndTokens(Member member){
        String accessToken = issueJwt(member, ACCESS_TOKEN);
        String refreshToken = issueJwt(member, REFRESH_TOKEN);

        redisService.saveRefreshToken(String.valueOf(member.getUuid()), refreshToken);

        return memberConverter.toMemberResponse(member, new Tokens(accessToken, refreshToken));
    }

    public String issueJwt(Member member, String tokenType){
        return jwtProvider.createToken(member.getUuid(), tokenType);
    }

    public MemberResponse getMemberInfo(MemberDetails memberDetails){
        Member member = memberRepository.findByUuid(memberDetails.getUuid());
        return memberConverter.toMemberResponse(member);
    }

    // TODO: 개인정보 수정 기능 -> 비밀번호 재입력 후 개인정보 수정
//    public MemberResponse modifyMemberInfo(MemberModificationRequest request,
//                                           MemberDetails memberDetails){
//        Member member = memberRepository.findByUuid(memberDetails.getUuid());
//
//
//    }


}
