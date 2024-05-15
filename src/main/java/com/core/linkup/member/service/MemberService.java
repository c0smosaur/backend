package com.core.linkup.member.service;

import com.core.linkup.common.service.S3Service;
import com.core.linkup.member.converter.MemberConverter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.entity.enums.GenderType;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.core.linkup.security.jwt.JwtProperties.ACCESS_TOKEN;
import static com.core.linkup.security.jwt.JwtProperties.REFRESH_TOKEN;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;
    private final S3Service s3Service;

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
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .gender(GenderType.valueOf(request.getGender()))
                .birthday(request.getBirthday())
                .role(RoleType.ROLE_USER)
                .build();

        Member savedMember = memberRepository.save(member);

        return memberConverter.toMemberResponse(member);
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

        refreshTokenService.saveRefreshToken(String.valueOf(member.getUuid()), refreshToken);

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
