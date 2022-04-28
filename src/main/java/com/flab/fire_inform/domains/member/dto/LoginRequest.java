package com.flab.fire_inform.domains.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class LoginRequest {

    private String id;
    private String password;
}
