package com.core.linkup.member.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.common.utils.RedisUtils;
import com.core.linkup.member.converter.MemberConverter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.common.entity.enums.GenderType;
import com.core.linkup.common.entity.enums.IndustryType;
import com.core.linkup.common.entity.enums.OccupationType;
import com.core.linkup.common.entity.enums.RoleType;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.member.request.CompanyMemberRegistrationRequest;
import com.core.linkup.member.request.LoginRequest;
import com.core.linkup.member.request.RegistrationRequest;
import com.core.linkup.member.response.MemberResponse;
import com.core.linkup.reservation.membership.company.repository.CompanyMembershipRepository;
import com.core.linkup.security.MemberDetails;
import com.core.linkup.security.Tokens;
import com.core.linkup.security.jwt.JwtProvider;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.core.linkup.security.jwt.JwtProperties.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RedisUtils redisUtils;
    private final CompanyMembershipRepository companyMembershipRepository;

    @Transactional
    public MemberResponse registerMember(RegistrationRequest request){

        if (request.isEmailVerified()){
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
            throw new BaseException(BaseResponseStatus.UNVERIFIED_EMAIL);
        }
    }

    @Transactional
    public MemberResponse login(LoginRequest request){
        Member member = memberRepository.findByUserEmail(request.getEmail());

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())){
            throw new BaseException(BaseResponseStatus.INVALID_PASSWORD);
        }

        return memberConverter.toMemberResponse(member);
    }

    public String issueAccessToken(Long id){
        return jwtProvider.createToken(id, ACCESS_TOKEN);
    }

    public Tokens issueTokens(Long id, String tokenType1, String tokenType2){
        String accessToken = jwtProvider.createToken(id, tokenType1);
        String refreshToken = jwtProvider.createToken(id, tokenType2);

        if (tokenType2.equals(REFRESH_TOKEN)){
            redisUtils.saveRefreshToken(id, refreshToken);
        } else {
            redisUtils.saveRememberRefreshToken(id, refreshToken);
        }
            return new Tokens(accessToken, refreshToken);
    }

    public MemberResponse getMemberInfo(MemberDetails memberDetails){
        Member member = memberDetails.getMember();
        return memberConverter.toMemberResponse(member);
    }

    @Transactional
    public MemberResponse registerCompanyMember(
            CompanyMemberRegistrationRequest request) {

        Long companyId = request.getCompanyId();

        if (request.isEmailVerified() &&
                request.isCompanyVerified() &&
                companyMembershipRepository.existsByCompanyId(companyId)) {

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
                    .companyMembershipId(companyMembershipRepository.findFirstByCompanyId(companyId).getId())
                    .role(RoleType.ROLE_USER)
                    .build();

            Member savedMember = memberRepository.save(member);

            return memberConverter.toMemberResponse(savedMember);
        } else {
            throw new BaseException(BaseResponseStatus.UNVERIFIED_EMAIL);
        }

        // TODO: 개인정보 수정 기능 -> 비밀번호 재입력 후 개인정보 수정(예정) -> 내 정보 화면설계 나오면 추가
        //
//    public MemberResponse modifyMemberInfo(MemberModificationRequest request,
//                                           MemberDetails memberDetails){
//        Member member = memberRepository.findByUuid(memberDetails.getUuid());
//
//
//    }
    }
}
