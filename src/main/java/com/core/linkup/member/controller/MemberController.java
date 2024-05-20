package com.core.linkup.member.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.member.request.LoginRequest;
import com.core.linkup.member.request.RegistrationRequest;
import com.core.linkup.member.request.validate.EmailValidateRequest;
import com.core.linkup.member.request.validate.EmailVerificationRequest;
import com.core.linkup.member.request.validate.PasswordValidateRequest;
import com.core.linkup.member.request.validate.UsernameValidateRequest;
import com.core.linkup.member.response.MemberResponse;
import com.core.linkup.member.service.MemberService;
import com.core.linkup.security.MemberDetails;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.core.linkup.common.utils.CookieUtils.addCookie;
import static com.core.linkup.security.jwt.JwtProperties.COOKIE_EXPIRATION_SECONDS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/member")
public class MemberController {

    private final MemberService memberService;

    // unique email validation
    @PostMapping("/validate/email")
    public BaseResponse<String> validateEmail(@RequestBody EmailValidateRequest request) {
        memberService.sendCodeByEmail(request);
        return BaseResponse.response("OK");
    }

    @PostMapping("/verify/email")
    public BaseResponse<String> verifyEmail(@RequestBody EmailVerificationRequest request) {
        if (memberService.verifyCode(request.email(), request.authCode())){
            return BaseResponse.response("OK");
        } else {
            return BaseResponse.response("Email verification failed");
        }
    }

    // unique username validation
    @PostMapping("/validate/username")
    public BaseResponse<String> validateUsername(@RequestBody UsernameValidateRequest request) {
        memberService.validateUsername(request);
        return BaseResponse.response("OK");
    }

    @PostMapping("/validate/password")
    public BaseResponse<String> validatePassword(@RequestBody PasswordValidateRequest request,
                                                   @AuthenticationPrincipal MemberDetails memberDetails) {
        memberService.validatePassword(request, memberDetails);
        return BaseResponse.response("OK");
    }

    @PostMapping("/register/user")
    public BaseResponse<MemberResponse> registerUser( @RequestBody RegistrationRequest request){
        MemberResponse memberResponse = memberService.registerUser(request);
        return BaseResponse.response(memberResponse);
    }

    @PostMapping("/login")
    public BaseResponse<MemberResponse> login(@Valid @RequestBody LoginRequest request,
                                                HttpServletResponse response) {
        MemberResponse memberResponse = memberService.login(request);

        addCookie(response, "access-token", memberResponse.getTokens().accessToken(),
                COOKIE_EXPIRATION_SECONDS);
        addCookie(response, "refresh-token", memberResponse.getTokens().refreshToken(),
                COOKIE_EXPIRATION_SECONDS);
        memberResponse.setTokens(null);
        return BaseResponse.response(memberResponse);
    }

    @GetMapping("/my-page")
    public BaseResponse<MemberResponse> myPage(@AuthenticationPrincipal MemberDetails memberDetails) {
        MemberResponse memberResponse = memberService.getMemberInfo(memberDetails);
        return BaseResponse.response(memberResponse);
    }

}
