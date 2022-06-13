package com.flab.fire_inform.domains.conversation.dto.newsList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class NewsResponse {
    private String version;
    private SkillTemplate template;
}
