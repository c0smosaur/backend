package com.core.linkup.member.request.validate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record EmailVerificationRequest(@Email String email, @NotBlank String authCode) {
}
