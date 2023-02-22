package com.flab.fire_inform.domains.news.dto.newsList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Button implements Serializable {

    private final String label;
    private final String action = "webLink";
    private final String webLinkUrl;

    private Button(String webLinkUrl, String label) {
        this.webLinkUrl = webLinkUrl;
        this.label = label;
    }

    public static List<Button> list(String url, String label) {
        List<Button> list = new ArrayList<>();
        list.add(new Button(url, label));
        return list;
    }
}
