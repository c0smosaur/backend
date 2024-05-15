package com.core.linkup.member.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class RegistrationRequest extends Loginable {

    @NotBlank
    private final String name;
    @NotBlank
    private final String username;
    private final String phoneNumber;
    private final String gender;
    private final LocalDate birthday;
    private final String companyName;

}
