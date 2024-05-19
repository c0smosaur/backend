package com.core.linkup.member.request.validate;

import jakarta.validation.constraints.NotBlank;

public record UsernameValidateRequest(@NotBlank String username) {}
