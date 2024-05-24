package com.core.linkup.member.controller;

import com.core.linkup.common.response.BaseResponse;
import com.core.linkup.member.request.LoginRequest;
import com.core.linkup.member.request.RegistrationRequest;
import com.core.linkup.member.request.validate.EmailValidationRequest;
import com.core.linkup.member.request.validate.EmailVerificationRequest;
import com.core.linkup.member.request.validate.PasswordValidationRequest;
import com.core.linkup.member.request.validate.UsernameValidationRequest;
import com.core.linkup.member.response.MemberResponse;
import com.core.linkup.member.service.MemberService;
import com.core.linkup.member.service.ValidationService;
import com.core.linkup.security.MemberDetails;
import com.core.linkup.security.Tokens;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.core.linkup.common.utils.CookieUtils.*;
import static com.core.linkup.security.jwt.JwtProperties.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final ValidationService validationService;

    // unique email validation
    @PostMapping("/validate/email")
    public BaseResponse<String> validateEmail(@RequestBody EmailValidationRequest request) {
        validationService.sendEmailAuthCodeByEmail(request);
        return BaseResponse.response("OK");
    }

    @PostMapping("/verify/email")
    public BaseResponse<String> verifyEmail(@RequestBody EmailVerificationRequest request) {
        if (validationService.verifyCode(request.email(), request.authCode())){
            return BaseResponse.response("OK");
        } else {
            return BaseResponse.response("Email verification failed");
        }
    }

    // unique username validation
    @PostMapping("/validate/username")
    public BaseResponse<String> validateUsername(@RequestBody UsernameValidationRequest request) {
        validationService.validateUsername(request);
        return BaseResponse.response("OK");
    }

    @PostMapping("/validate/password")
    public BaseResponse<String> validatePassword(@RequestBody PasswordValidationRequest request,
                                                   @AuthenticationPrincipal MemberDetails memberDetails) {
        validationService.validatePassword(request, memberDetails);
        return BaseResponse.response("OK");
    }

    // TODO: 기업인증
//    @PostMapping("/verify/company")
//    public BaseResponse<String> verifyCompany(@RequestBody )

    @PostMapping("/register")
    public BaseResponse<MemberResponse> registerMember( @RequestBody RegistrationRequest request){
        MemberResponse memberResponse = memberService.registerMember(request);
        return BaseResponse.response(memberResponse);
    }

    // TODO: 기업회원가입
//    @PostMapping("/company/register")
//    public BaseResponse<MemberResponse> registerCompanyMember(@Valid @RequestBody RegistrationRequest request){
//
//    }

    @PostMapping("/login")
    public BaseResponse<MemberResponse> login(@Valid @RequestBody LoginRequest request,
                                                HttpServletResponse response) {
        MemberResponse memberResponse = memberService.login(request);

        if (request.getRememberMe()){
            Tokens tokens = memberService.issueTokens(memberResponse.getId(), ACCESS_TOKEN, REMEMBER_REFRESH_TOKEN);
            addCookie(response, "access-token", tokens.accessToken(), REMEMBER_COOKIE_EXPIRATION_SECONDS);
            addCookie(response, "refresh-token", tokens.refreshToken(), REMEMBER_COOKIE_EXPIRATION_SECONDS);
        } else {
            Tokens tokens = memberService.issueTokens(memberResponse.getId(), ACCESS_TOKEN, REFRESH_TOKEN);
            addCookie(response, "access-token", tokens.accessToken(), COOKIE_EXPIRATION_SECONDS);
            addCookie(response, "refresh-token", tokens.refreshToken(), COOKIE_EXPIRATION_SECONDS);
        }
        return BaseResponse.response(memberResponse);
    }

    @GetMapping("/my-page")
    public BaseResponse<MemberResponse> myPage(@AuthenticationPrincipal MemberDetails memberDetails) {
        MemberResponse memberResponse = memberService.getMemberInfo(memberDetails);
        return BaseResponse.response(memberResponse);
    }

    @GetMapping("/token")
    public BaseResponse<String> renewToken(@AuthenticationPrincipal MemberDetails memberDetails) {
        String newAccessToken = memberService.issueAccessToken(memberDetails.getId());
        return BaseResponse.response(newAccessToken);
    }
}
