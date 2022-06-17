package com.flab.fire_inform.domains.conversation.dto.newsList;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@ToString
public class Button implements Serializable {

    private final String label = "더 보기";
    private final String action = "webLink";
    private final String webLinkUrl;

    public Button(String webLinkUrl){
        this.webLinkUrl = webLinkUrl;
    }
}
