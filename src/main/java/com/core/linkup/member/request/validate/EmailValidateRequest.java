package com.core.linkup.member.request.validate;

import jakarta.validation.constraints.Email;

public record EmailValidateRequest(@Email String email) {
}
