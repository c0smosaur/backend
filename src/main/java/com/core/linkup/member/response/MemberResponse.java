package com.core.linkup.member.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class MemberResponse {

    private Long id;
    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String gender;
    private String industry;
    private String occupation;
    private LocalDate birthday;
    private String introduction;
    private String profileImage;

    private Long companyMembershipId;

}
