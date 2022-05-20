package com.flab.fire_inform.global.exception;


import com.flab.fire_inform.global.exception.error.ErrorCode;

// 이렇게 RuntimeException을 상속받음으로 Runtime에 발생하는 예외에 더해 개발자가 예상한 예외를 만들어 다른 개발자들도 사용할 수 있게 된다.

public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;
    public CustomException(ErrorCode errorCode){
        this.errorCode = errorCode;
    }
    public ErrorCode getErrorCode() {
        return errorCode;
    }
}
