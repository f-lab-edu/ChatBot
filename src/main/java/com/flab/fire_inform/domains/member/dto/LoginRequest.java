package com.flab.fire_inform.domains.member.dto;

import java.io.Serializable;
import lombok.Getter;
import lombok.ToString;


@Getter
@ToString
public class LoginRequest implements Serializable {

    private String id;
    private String password;

    private String refreshToken;

    public LoginRequest() {
    }

    public LoginRequest(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
