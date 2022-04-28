package com.flab.fire_inform.domains.member.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class JoinRequest {

    private String id;
    private String password;
    private String phoneNumber;


    public JoinRequest() {
    }

    public JoinRequest(String id, String password, String phoneNumber) {
        this.id = id;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

}
