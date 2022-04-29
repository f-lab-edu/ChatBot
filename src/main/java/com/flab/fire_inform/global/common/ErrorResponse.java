package com.flab.fire_inform.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ErrorResponse {
    private final HttpStatus status;
    private final String data;
    private final String message;
    private final String errorCode;

    private ErrorResponse(HttpStatus status, String data, String message, String errorCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static ErrorResponse success(ErrorCode errorCode) {
        return new ErrorResponse(errorCode.getStatus(), null, errorCode.getMessage(), errorCode.getErrorCode());
    }
}
