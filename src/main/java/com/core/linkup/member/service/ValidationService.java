package com.core.linkup.member.service;

import com.core.linkup.common.exception.BaseException;
import com.core.linkup.common.response.BaseResponseStatus;
import com.core.linkup.common.utils.AuthCodeUtils;
import com.core.linkup.common.utils.EmailUtils;
import com.core.linkup.common.utils.RedisUtils;
import com.core.linkup.member.repository.MemberRepository;
import com.core.linkup.member.request.validate.EmailValidationRequest;
import com.core.linkup.member.request.validate.PasswordValidationRequest;
import com.core.linkup.member.request.validate.UsernameValidationRequest;
import com.core.linkup.security.MemberDetails;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ValidationService {

    private final EmailUtils emailUtils;
    private final RedisUtils redisUtils;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthCodeUtils authCodeUtils;

    public void sendEmailAuthCodeByEmail(EmailValidationRequest request) {
        String subject = "LinkUp 이메일 인증번호";
        String authCode = authCodeUtils.createEmailAuthCode();

        String htmlTemplate = "<div\n" +
                "  style=\"\n" +
                "    margin: 20px auto;\n" +
                "    background: #bfd4ff;\n" +
                "    width: 100%;\n" +
                "    max-width: 40rem;\n" +
                "    height: 30rem;\n" +
                "    text-align: center;\n" +
                "    padding: 20px;\n" +
                "    box-sizing: border-box;\n" +
                "  \"\n" +
                ">\n" +
                "  <svg\n" +
                "    xmlns=\"http://www.w3.org/2000/svg\"\n" +
                "    width=\"223\"\n" +
                "    height=\"41\"\n" +
                "    viewBox=\"0 0 223 41\"\n" +
                "    fill=\"none\"\n" +
                "    style=\"max-width: 60vw; margin-bottom: 2rem; width: 12rem\"\n" +
                "  >\n" +
                "    <path\n" +
                "      d=\"M56 1H43V40H56V1Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M72 1H59V40H72V1Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M98 14H85V40H98V14Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M85 1C92.1825 1 98 6.8175 98 14H72C72 6.8175 77.8175 1 85 1Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M167 40H180V1H167V40Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M141 27H154V1H141V27Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M154 40C146.817 40 141 34.1825 141 27L167 27C167 34.1825 161.182 40 154 40Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M40 27H14V40H40V27Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M1 27C1 19.8175 6.8175 14 14 14L14 40C6.8175 40 1 34.1825 1 27Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M11 1L1 11V14H14V1H11Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M196 1H183V40H196V1Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "    <path\n" +
                "      fill-rule=\"evenodd\"\n" +
                "      clip-rule=\"evenodd\"\n" +
                "      d=\"M196 1H209C216.182 1 222 6.8175 222 14C222 21.1825 216.182 27 209 27V14H196V1Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M196 1V0.25H195.25V1H196ZM209 27H208.25V27.75H209V27ZM209 14H209.75V13.25H209V14ZM196 14H195.25V14.75H196V14ZM209 0.25H196V1.75H209V0.25ZM209 1.75C215.768 1.75 221.25 7.23171 221.25 14H222.75C222.75 6.40329 216.597 0.25 209 0.25V1.75ZM221.25 14C221.25 20.7683 215.768 26.25 209 26.25V27.75C216.597 27.75 222.75 21.5967 222.75 14H221.25ZM209.75 27V14H208.25V27H209.75ZM196 14.75H209V13.25H196V14.75ZM195.25 1V14H196.75V1H195.25Z\"\n" +
                "      fill=\"#171717\"\n" +
                "    />\n" +
                "    <path\n" +
                "      fill-rule=\"evenodd\"\n" +
                "      clip-rule=\"evenodd\"\n" +
                "      d=\"M108 14L121 1H134V4L117.5 20.5L134 37V40H121L108 27V14Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M121 1V0.25H120.69L120.47 0.469662L121 1ZM108 14L107.47 13.4697L107.25 13.6893V14H108ZM134 1H134.75V0.25H134V1ZM134 4L134.531 4.53033L134.75 4.31066V4H134ZM117.5 20.5L116.97 19.9697L116.439 20.5L116.97 21.0303L117.5 20.5ZM134 37H134.75V36.6893L134.531 36.4697L134 37ZM134 40V40.75H134.75V40H134ZM121 40L120.47 40.5303L120.69 40.75H121V40ZM108 27H107.25V27.3107L107.47 27.5303L108 27ZM120.47 0.469662L107.47 13.4697L108.53 14.5303L121.531 1.53034L120.47 0.469662ZM134 0.25H121V1.75H134V0.25ZM134.75 4V1H133.25V4H134.75ZM118.03 21.0303L134.531 4.53033L133.47 3.46967L116.97 19.9697L118.03 21.0303ZM116.97 21.0303L133.47 37.5303L134.531 36.4697L118.03 19.9697L116.97 21.0303ZM133.25 37V40H134.75V37H133.25ZM134 39.25H121V40.75H134V39.25ZM121.531 39.4697L108.53 26.4697L107.47 27.5303L120.47 40.5303L121.531 39.4697ZM108.75 27V14H107.25V27H108.75Z\"\n" +
                "      fill=\"#171717\"\n" +
                "    />\n" +
                "    <path\n" +
                "      d=\"M114 1H101V40H114V1Z\"\n" +
                "      fill=\"#FAEC23\"\n" +
                "      stroke=\"#171717\"\n" +
                "      stroke-width=\"1.5\"\n" +
                "    />\n" +
                "  </svg>\n" +
                "  <div\n" +
                "    style=\"\n" +
                "      border-radius: 1rem;\n" +
                "      background-color: #e4eeff;\n" +
                "      text-align: center;\n" +
                "      padding: 1rem;\n" +
                "      box-sizing: border-box;\n" +
                "      width: 90%;\n" +
                "      height: 70%;\n" +
                "      margin: 0 auto;\n" +
                "    \"\n" +
                "  >\n" +
                "    <h1\n" +
                "      style=\"\n" +
                "        font-size: 1.125rem;\n" +
                "        margin-bottom: 0.5rem;\n" +
                "        word-break: keep-all;\n" +
                "      \"\n" +
                "    >\n" +
                "      회원가입을 위한 인증번호입니다.\n" +
                "    </h1>\n" +
                "    <p style=\"font-size: 0.875rem; word-break: keep-all\">\n" +
                "      아래 인증번호를 확인하여 회원가입을 진행해 주세요.\n" +
                "    </p>\n" +
                "    <div style=\"margin-top: 4rem\">\n" +
                "      <b style=\"font-size: 1.6rem\">{{authCode}}</b>\n" +
                "    </div>\n" +
                "  </div>\n" +
                "</div>\n";

        String htmlContent = htmlTemplate.replace("{{authCode}}", authCode);
        validateEmail(request);
        try {
            emailUtils.sendEmail(request.email(), subject, htmlContent);
            redisUtils.saveEmailAuthCode(request.email(), authCode);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new BaseException(BaseResponseStatus.EMAIL_ERROR);
        }
    }

    public Boolean verifyEmailAuthCode(String email, String authCode){
        return redisUtils.findEmailAuthCode(email).equals(authCode);
    }

    public long findCompanyIdByAuthCode(String authCode){
        return Integer.parseInt(redisUtils.findCompanyAuthCode(authCode));
    }

    public void validateEmail(EmailValidationRequest request){
        if (memberRepository.existsByEmail(request.email())){
            throw new BaseException(BaseResponseStatus.DUPLICATE_EMAIL);
        }
    }

    public void validateUsername(UsernameValidationRequest request){
        if (memberRepository.existsByUsername(request.username())){
            throw new BaseException(BaseResponseStatus.DUPLICATE_USERNAME);
        }
    }

    public void validatePassword(PasswordValidationRequest request,
                                 MemberDetails memberDetails){
        if (!passwordEncoder.matches(request.password(), memberDetails.getPassword())){
            throw new BaseException(BaseResponseStatus.INVALID_PASSWORD);
        }
    }
}
