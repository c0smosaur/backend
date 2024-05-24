package com.core.linkup.member.converter;

import com.core.linkup.common.annotation.Converter;
import com.core.linkup.member.entity.Member;
import com.core.linkup.member.response.MemberResponse;

@Converter
public class MemberConverter {

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
                .build();
    }
}
