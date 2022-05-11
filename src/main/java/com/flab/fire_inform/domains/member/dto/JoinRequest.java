package com.flab.fire_inform.domains.member.dto;

import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class JoinRequest implements Serializable {
    private String id;
    private String password;
    private String phoneNumber;
    //항상 기본 생성자 사용
    public JoinRequest() {
    }
    public JoinRequest(String id, String password, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }
}
