package com.core.linkup.member.controller;

import com.core.linkup.member.request.LoginRequest;
import com.core.linkup.member.request.RegistrationRequest;
import com.core.linkup.member.request.validate.EmailValidateRequest;
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

    @PostMapping("/validate/email")
    public ResponseEntity<String> validateEmail(@RequestBody EmailValidateRequest request) {
        memberService.validateEmail(request);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/validate/username")
    public ResponseEntity<String> validateUsername(@RequestBody UsernameValidateRequest request) {
        memberService.validateUsername(request);
        return ResponseEntity.ok("OK");
    }

    @PostMapping("/validate/password")
    public ResponseEntity<String> validatePassword(@RequestBody PasswordValidateRequest request,
                                                   @AuthenticationPrincipal MemberDetails memberDetails) {
        try {
            memberService.validatePassword(request, memberDetails);
            return ResponseEntity.ok("OK");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body("wrong password");
        }
    }

    @PostMapping("/register/user")
    public ResponseEntity<MemberResponse> registerUser(@Valid @RequestBody RegistrationRequest request){
        MemberResponse memberResponse = memberService.registerUser(request);

        return ResponseEntity.ok(memberResponse);
    }

    @PostMapping("/login")
    public ResponseEntity<MemberResponse> login(@Valid @RequestBody LoginRequest request,
                                                HttpServletResponse response) {
        MemberResponse memberResponse = memberService.login(request);

        addCookie(response, "access-token", memberResponse.getTokens().accessToken(),
                COOKIE_EXPIRATION_SECONDS);
        addCookie(response, "refresh-token", memberResponse.getTokens().refreshToken(),
                COOKIE_EXPIRATION_SECONDS);
        memberResponse.setTokens(null);

        return ResponseEntity.ok(memberResponse);
    }

    @GetMapping("/my-page")
    public ResponseEntity<MemberResponse> myPage(@AuthenticationPrincipal MemberDetails memberDetails) {
        MemberResponse memberResponse = memberService.getMemberInfo(memberDetails);
        return ResponseEntity.ok(memberResponse);
    }

}
