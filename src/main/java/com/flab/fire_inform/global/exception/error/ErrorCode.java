package com.flab.fire_inform.global.exception.error;

import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;
import static org.springframework.http.HttpStatus.CONFLICT;


public enum ErrorCode {
    //서버에서 발생할 수 있는 모든 예외처리
    INVALID_ACCESS_TOKEN(BAD_REQUEST,"토큰이 유효하지 않습니다."),
    MEMBER_NOT_FOUND(NOT_FOUND,"아이디와 비밀번호를 확인하세요."),
    DUPLICATE_ID(CONFLICT,"아이디가 이미 존재합니다."),
    SERVER_ERROR(BAD_REQUEST,"데이터가 이미 존재합니다."),
    JOIN_FAIL(CONFLICT,"회원가입에 실패했습니다. 가입정보를 다시 확인해주세요.")
    ;

    private final HttpStatus httpStatus;
    private final String detail;

    ErrorCode(HttpStatus httpStatus, String detail) {
        this.httpStatus = httpStatus;
        this.detail = detail;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getDetail() {
        return detail;
    }
}