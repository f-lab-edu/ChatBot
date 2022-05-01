package com.flab.fire_inform.domains.member.service;


import com.flab.fire_inform.domains.member.dto.JoinRequest;
import com.flab.fire_inform.domains.member.dto.LoginRequest;
import com.flab.fire_inform.domains.member.mapper.MemberMapper;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class MemberService {

    private final MemberMapper memberMapper;
    public MemberService(MemberMapper memberMapper){
        this.memberMapper = memberMapper;
    }

    public void login(LoginRequest loginRequest){

        log.info("[MemberMapper] :::: ========= loginRequest  = " + loginRequest);

        if(!memberMapper.login(loginRequest)){
            throw new CustomException(ErrorCode.MEMBER_NOT_FOUND);
        }
        memberMapper.insertLoginTime(loginRequest.getId());
    }

    public void join(JoinRequest joinRequest){

        try{
            memberMapper.join(joinRequest);
        }catch (Exception e){
            throw new CustomException(ErrorCode.JOIN_FAIL);
        }

    }

    public boolean idCheck(String id){
        return memberMapper.checkId(id);
    }
}
