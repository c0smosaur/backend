package com.core.linkup.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class MemberModificationRequest {

    private final String username;
    private final String phoneNumber;
    private final String gender;
    private final String companyName;

}
