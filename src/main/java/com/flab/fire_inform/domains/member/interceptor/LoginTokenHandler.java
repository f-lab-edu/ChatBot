package com.flab.fire_inform.domains.member.interceptor;

import com.flab.fire_inform.domains.member.dto.LoginRequest;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import com.flab.fire_inform.global.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class LoginTokenHandler implements HandlerInterceptor {

    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN_KEY = "REFRESH_TOKEN_KEY";

    private JwtProvider jwtProvider;
    public LoginTokenHandler(JwtProvider jwtProvider){
        this.jwtProvider = jwtProvider;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String accessToken = request.getHeader(ACCESS_TOKEN);

        if(accessToken != null && jwtProvider.validationToken(accessToken)){

            log.info("[Prehandler] :::::Access_token OK");
            return true;

        }else{

            HttpSession session = request.getSession();
            LoginRequest loginRequest = (LoginRequest)session.getAttribute(request.getHeader("REFRESH_TOKEN_KEY"));

            if (loginRequest == null){
                // 기존에 Objects.requireNonNull(loginRequest) 사용 -- 명확한 에러를 전달할 수 없음.
                throw new CustomException(ErrorCode.REFRESH_TOKEN_NOT_FOUND);
            }

            String refreshToken = loginRequest.getRefreshToken();

            log.info("[Prehandler] :::::::::  RefreshToken is = {}", refreshToken);

            if (refreshToken != null && jwtProvider.validationToken(refreshToken)){
                String newAccessToken = jwtProvider.createAccessToken(loginRequest.getId(),loginRequest.getPassword());
                response.setHeader(ACCESS_TOKEN,newAccessToken);
                log.info("[Prehandler] :::::REFRESH_TOKEN 통과 ---> NewAccessToken ={} ", newAccessToken);
                return true;
            }

            log.info("[Prehandler] :::::Token 유효하지 않음");
            throw new CustomException(ErrorCode.INVALID_TOKEN);

        }
    }
}
