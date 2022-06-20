package com.flab.fire_inform.domains.conversation.dto.newsList;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@ToString
public class Button implements Serializable {

    private final String label = "더 보기";
    private final String action = "webLink";
    private final String webLinkUrl;

    private Button(String webLinkUrl){
        this.webLinkUrl = webLinkUrl;
    }

    public static List<Button> list(String url){
        List<Button> list = new ArrayList<>();
        list.add(new Button(url));
        return list;
    }
}
