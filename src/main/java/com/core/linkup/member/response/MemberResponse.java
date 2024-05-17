package com.core.linkup.member.response;

import com.core.linkup.security.Tokens;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Builder
@Getter
public class MemberResponse {

    private String name;
    private String username;
    private String email;
    private String phoneNumber;
    private String gender;
    private String industry;
    private String occupation;
    private String address;
    private LocalDate birthday;
    private String introduction;
    private Long companyId;
    private String profileImage;

    @Setter
    private Tokens tokens;

}
