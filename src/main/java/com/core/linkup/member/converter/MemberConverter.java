package com.core.linkup.member.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.response.MemberResponse;
import com.core.linkup.reservation.membership.individual.converter.IndividualMembershipConverter;
import lombok.RequiredArgsConstructor;

@Converter
@RequiredArgsConstructor
public class MemberConverter {

    private final IndividualMembershipConverter individualMembershipConverter;

    public MemberResponse toMemberResponse(Member member) {
        return MemberResponse.builder()
                .id(member.getId())
                .name(member.getName())
                .username(member.getUsername())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .gender(member.getGender().getGenderInKor())
                .industry(member.getIndustry().getIndustryName())
                .occupation(member.getOccupation().getOccupationName())
                .birthday(member.getBirthday())
                .introduction(member.getIntroduction())
                .profileImage(member.getProfileImage())
                .companyMembershipId(member.getCompanyMembershipId())
                .build();
    }
}
