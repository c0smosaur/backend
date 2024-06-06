package com.core.linkup.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.*;

@Getter
@AllArgsConstructor
public enum BaseResponseStatus {

    SUCCESS(true, OK.value(), "요청이 성공적으로 처리되었습니다."),
    CREATE_SUCCESS(true, CREATED.value(), "성공적으로 생성되었습니다."),
    DELETE_SUCCESS(true, NO_CONTENT.value(), "성공적으로 삭제되었습니다."),

    EMAIL_ERROR(false, OK.value(), "메일이 도착하지 않았다면 다시 시도해주세요."),


    // TODO: 개발 진행에 따라 추가될 에정

    EXPIRED_TOKEN(false, UNAUTHORIZED.value(), "만료된 토큰입니다."),
    INVALID_TOKEN(false, UNAUTHORIZED.value(), "잘못된 토큰입니다."),
    TOKEN_NOT_FOUND(false, UNAUTHORIZED.value(), "토큰이 존재하지 않습니다."),

    REQUIRES_CONSENT(false, BAD_REQUEST.value(), "개인정보 수집에 동의해주세요."),
    DUPLICATE_EMAIL(false, BAD_REQUEST.value(), "이미 가입된 이메일입니다."),
    UNVERIFIED_EMAIL(false, BAD_REQUEST.value(), "인증되지 않은 이메일입니다."),
    DUPLICATE_USERNAME(false, BAD_REQUEST.value(), "이미 사용중인 닉네임입니다."),
    INVALID_PASSWORD(false, BAD_REQUEST.value(), "아이디나 비밀번호가 일치하지 않습니다."),
    INVALID_AUTHCODE(false, BAD_REQUEST.value(), "인증에 실패했습니다."),
    AUTHCODE_ISSUE_ERROR(false, INTERNAL_SERVER_ERROR.value(), "인증코드 발급에 실패했습니다."),

    UNREGISTERD_MEMBER(false, NOT_FOUND.value(), "존재하지 않는 사용자입니다."),
    DOES_NOT_EXIST(false, NOT_FOUND.value(), "요청한 데이터가 존재하지 않습니다."),

    INVALID_MEMBERSHIP_ID(false, NOT_FOUND.value(), "등록된 멤버십이 없습니다."),
    INVALID_OFFICEBUILDING_ID(false, NOT_FOUND.value(), "존재하지 않는 공유오피스 입니다."),
    INVALID_OFFICE_LOCATION(false, NOT_FOUND.value(), "지점 정보가 옳지 않습니다."),
    INVALID_CLUB_ID(false, NOT_FOUND.value(), "존재하지 않는 소모임입니다."),
    INVALID_CLUB_OWNER(false, NOT_FOUND.value(), "소모임 소유자가 아닙니다."),
    INVALID_NOTICE(false, NOT_FOUND.value(), "공지사항이 없습니다."),
    INVALID_CLUB_NOTICE(false, NOT_FOUND.value(), "소모임에 등록된 공지가 맞는지 확인하세요."),
    INVALID_NOTICE_ID(false, NOT_FOUND.value(), "게시판이 존재하지 않습니다."),
    INVALID_COMMENT_ID(false, NOT_FOUND.value(), "댓글이 존재하는지 확인해주세요."),
    INVALID_CLUB_MEMBER(false, NOT_FOUND.value(), "소모임에 등록된 회원이 아닙니다."),
    INVALID_CLUB_MEETING(false, NOT_FOUND.value(), "소모임에 등록된 정기모임이 아닙니다."),

    INVALID_REQUEST(false, INTERNAL_SERVER_ERROR.value(), "잘못된 요청입니다."),
    INVALID_MEMBER(false, INTERNAL_SERVER_ERROR.value(), "로그인된 사용자와 일치하지 않습니다."),

    SERVER_ERROR(false, INTERNAL_SERVER_ERROR.value(), "예상하지 못한 서버 에러가 발생했습니다.");

    private final boolean isSuccess;
    private final int statusCode;
    private final String message;

}
