package com.flab.fire_inform.global.response;

import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
public class Response<T> {

    private final Timestamp localDateTime;
    private final int code;
    private final String message;
    private final T data;
    public static class Builder<T>{
        // 필수 요소에는 final 적용
        private final Timestamp localDateTime = new Timestamp(System.currentTimeMillis());
        private final int code;
        private final String message;

        //선택요소에는 final 미적용
        private T data = null;

        //필수요소는 생성자로 받는다.
        public Builder(int code, String message){
            this.message = message;
            this.code = code;
        }

        public Builder data(T data){
            this.data = data;
            return this;
        }

        public Response build(){
            return new Response(this);
        }
    }

    private Response(Builder<T> builder){
        this.code = builder.code;
        this.message = builder.message;
        this.data = builder.data;
        this.localDateTime = builder.localDateTime;
    }

    public String toString() {
        return "Response{" +
                "localDateTime=" + localDateTime +
                ", code=" + code +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    /*
    RespnoseEntity도 반환 가능한 객체
    SuccessCode 생성도 필요할까?
     */
    public static <T> ResponseEntity<Response> toResponseEntitySuccess(int code, String message, T data){
        return ResponseEntity.status(HttpStatus.OK)
                .body( new Response.Builder<T>(code, message).data(data).build()
                );
    }

    public static ResponseEntity<Response> toResponseEntityError(ErrorCode errorCode){
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(new Response.Builder(errorCode.getHttpStatus().value(),errorCode.getDetail()).build());
    }
}
