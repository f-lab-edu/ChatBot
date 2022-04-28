package com.flab.fire_inform.global.config;


import com.flab.fire_inform.domains.member.interceptor.LoginTokenHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Slf4j
@Configuration
public class LoginInterceptorConfiguration extends WebMvcConfigurationSupport {

    private final LoginTokenHandler loginTokenHandler;

    public LoginInterceptorConfiguration(LoginTokenHandler loginTokenHandler){
        this.loginTokenHandler = loginTokenHandler;
    }

    // 로그인과 회원가입으 제외하고 모든 요청에서 토큰 체크할 수 있도록
    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginTokenHandler)
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/join");
    }
}
