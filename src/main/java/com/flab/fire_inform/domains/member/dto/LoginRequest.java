package com.flab.fire_inform.domains.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@ToString
public class LoginRequest {

    private String id;
    private String password;


    public LoginRequest() {
    }

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

}
