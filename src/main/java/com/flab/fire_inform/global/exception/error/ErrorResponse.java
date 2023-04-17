package com.flab.fire_inform.global.exception.error;


import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.ResponseEntity;

/**
 * bulider 만들어서 리팩토링하기
 */
@Getter
@Builder
public class ErrorResponse {

    private final LocalDateTime timestamp = LocalDateTime.now();
    private final int status;
    private final String error;
    private final String code;
    private final String message;

    public static ResponseEntity<ErrorResponse> toResponseEntity(ErrorCode errorCode) {
        return ResponseEntity
            .status(errorCode.getHttpStatus())
            .body(ErrorResponse.builder()
                .status(errorCode.getHttpStatus().value())
                .error(errorCode.getHttpStatus().name())
                .code(errorCode.name())
                .message(errorCode.getDetail())
                .build()
            );
    }
}
