package com.flab.fire_inform.domains.member.controller;


import com.flab.fire_inform.domains.member.dto.JoinRequest;
import com.flab.fire_inform.domains.member.dto.LoginRequest;
import com.flab.fire_inform.domains.member.mapper.MemberMapper;
import com.flab.fire_inform.domains.member.service.MemberService;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import com.flab.fire_inform.global.security.JwtProvider;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
public class MemberController {

    private final MemberService memberService;
    private final JwtProvider jwtProvider;
    private final MemberMapper memberMapper;

    public MemberController(MemberService memberService, JwtProvider jwtProvider, MemberMapper memberMapper){
        this.memberService = memberService;
        this.jwtProvider = jwtProvider;
        this.memberMapper = memberMapper;
    }

    // 로그인
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){

        // 로그인이 안되면 서비스에서 Exeption발생
        memberService.login(loginRequest);

       String token = jwtProvider.createAccessToken(loginRequest.getId(),loginRequest.getPassword());
       response.setHeader("ACCESS_TOKEN",token);

        return new ResponseEntity(HttpStatus.OK);
    }

    //로그아웃
    @PostMapping("/logout")
    public ResponseEntity logout(HttpServletRequest request, HttpServletResponse response){

        Claims claims = jwtProvider.getInformation(request.getHeader("ACCESS_TOKEN"));
        response.setHeader("ACCESS_TOKEN","");

        memberMapper.insertLogoutTime((String)claims.get("id"));

        return new ResponseEntity("로그아웃 되었습니다.",HttpStatus.OK);
    }

    //회원가입
    @PostMapping("/join")
    public ResponseEntity join(@RequestBody JoinRequest joinRequest){

        if(!memberService.idCheck(joinRequest.getId())){
            throw new CustomException(ErrorCode.DUPLICATE_ID);
        }

        memberService.join(joinRequest);

        return new ResponseEntity("성공적으로 가입을 완료했습니다.",HttpStatus.OK);
    }


    /**
     * Google Login Section
     */

    // 구글 로그인
    @PostMapping("/login/google")
    public ResponseEntity googleLogin(){
        return new ResponseEntity(HttpStatus.OK);
    }

    //구글 로그인 리다이렉션
    @PostMapping("/login/google/redirect")
    public ResponseEntity reGoogleLogin(){
        return new ResponseEntity(HttpStatus.OK);
    }

}

