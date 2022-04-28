package com.flab.fire_inform.domains.member.mapper;

import com.flab.fire_inform.domains.member.dto.JoinRequest;
import com.flab.fire_inform.domains.member.dto.LoginRequest;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberMapper {

    boolean login(LoginRequest loginRequest);

    int join(JoinRequest joinRequest);

    boolean checkId(String id);

    int insertLoginTime(String id);

    int insertLogoutTime(String id);
}
