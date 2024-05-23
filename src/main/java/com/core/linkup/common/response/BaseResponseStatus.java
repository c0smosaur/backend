package com.core.linkup.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    SUCCESS(true, OK.value(),"요청이 성공적으로 처리되었습니다."),
    CREATE_SUCCESS(true, CREATED.value(), "성공적으로 생성되었습니다."),
    DELETE_SUCCESS(true, NO_CONTENT.value(), "성공적으로 삭제되었습니다."),


    // TODO: 개발 진행에 따라 추가될 에정

    EXPIRED_TOKEN(false, UNAUTHORIZED.value(), "만료된 토큰입니다."),
    INVALID_TOKEN(false, UNAUTHORIZED.value(), "잘못된 토큰입니다."),
    TOKEN_NOT_FOUND(false, UNAUTHORIZED.value(), "토큰이 존재하지 않습니다."),


    DUPLICATE_EMAIL(false, BAD_REQUEST.value(), "이미 가입된 이메일입니다."),
    UNVERIFIED_EMAIL(false, BAD_REQUEST.value(), "인증되지 않은 이메일입니다."),
    DUPLICATE_USERNAME(false, BAD_REQUEST.value(), "이미 사용중인 닉네임입니다."),
    INVALID_PASSWORD(false, BAD_REQUEST.value(), "아이디나 비밀번호가 일치하지 않습니다."),
    INVALID_AUTHCODE(false, BAD_REQUEST.value(), "인증에 실패했습니다."),
    REGISTRATION_AUTHCODE_ERROR(false, INTERNAL_SERVER_ERROR.value(), "인증코드 발급에 실패했습니다."),
    EMAIL_ERROR(false, BAD_REQUEST.value(), "메일 발신 과정에서 오류가 발생했습니다."),
    UNREGISTERD_MEMBER(false, NOT_FOUND.value(), "존재하지 않는 사용자입니다."),


    SERVER_ERROR(false, INTERNAL_SERVER_ERROR.value(), "예상하지 못한 서버 에러가 발생했습니다.")
    ;

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;

}
