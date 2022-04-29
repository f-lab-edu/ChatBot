package com.flab.fire_inform.global.common;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class SuccessResponse<T> {
    private final HttpStatus status;
    private final T data;
    private final String message;
    private final String errorCode;

    private SuccessResponse(HttpStatus status, T data, String message, String errorCode) {
        this.status = status;
        this.data = data;
        this.message = message;
        this.errorCode = errorCode;
    }

    public static <T> SuccessResponse<T> success(T data) {
        return new SuccessResponse<>(HttpStatus.OK, data, "success", null);
    }
}
