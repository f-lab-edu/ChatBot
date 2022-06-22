package com.flab.fire_inform.domains.conversation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class SkillResponse {
    private String version;
    private SkillTemplate template;
}
