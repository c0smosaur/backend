package com.core.linkup.member.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class LoginRequest extends Loginable {

    private Boolean rememberMe;
}
