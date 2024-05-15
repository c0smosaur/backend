package com.core.linkup.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginRequest extends Loginable {
    public LoginRequest(String email, String password) {
        super(email, password);
    }
}
