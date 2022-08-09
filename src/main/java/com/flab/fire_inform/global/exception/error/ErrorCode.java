package com.flab.fire_inform.global.exception.error;

import org.springframework.http.HttpStatus;
import static org.springframework.http.HttpStatus.*;

public enum ErrorCode {
    //고객 관련 에러
    INVALID_TOKEN(BAD_REQUEST,"토큰이 유효하지 않습니다."),
    REFRESH_TOKEN_NOT_FOUND(BAD_REQUEST,"토큰이 유효하지 않습니다."),
    MEMBER_NOT_FOUND(NOT_FOUND,"아이디와 비밀번호를 확인하세요."),
    DUPLICATE_ID(CONFLICT,"아이디가 이미 존재합니다."),
    SERVER_ERROR(BAD_REQUEST,"데이터가 이미 존재합니다."),
    JOIN_FAIL(CONFLICT,"회원가입에 실패했습니다. 가입정보를 다시 확인해주세요."),

    // 뉴스 헤드라인 조회 관련 에러
    DOMAIN_NOT_FOUND(BAD_REQUEST,"도메인을 선택해주셔야합니다."),
    COMPANY_NOT_FOUND(BAD_REQUEST,"정확한 회사명을 입력해주세요."),
    WRONG_DATASOURCE(BAD_REQUEST,"잘못된 데이터 소스입니다.")
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
