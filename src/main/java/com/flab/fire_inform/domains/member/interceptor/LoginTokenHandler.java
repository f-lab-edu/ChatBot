package com.flab.fire_inform.domains.member.interceptor;

import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import com.flab.fire_inform.global.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Component
public class LoginTokenHandler implements HandlerInterceptor {
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";

    private JwtProvider jwtProvider;

    public LoginTokenHandler(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader(ACCESS_TOKEN);

        // Refresh Token 없이 Access Token만
        if(accessToken != null && jwtProvider.validationToken(accessToken)){
            log.info("[Prehandler] :::::Access_token 통과");
            return true;
        }else{
            log.info("[Prehandler] :::::Access_token 유효하지 않음");
            throw new CustomException(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }
}
