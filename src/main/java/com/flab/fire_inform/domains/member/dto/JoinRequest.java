package com.flab.fire_inform.domains.member.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class JoinRequest {

    private String id;
    private String password;
    private String phone_number;

}
