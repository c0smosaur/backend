package com.core.linkup.member.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public abstract class Loginable {

    @Email
    @NotBlank
    protected String email;

    @NotBlank
    protected String password;

}
