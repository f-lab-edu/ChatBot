package com.flab.fire_inform.domains.news.dto.newsList;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
@AllArgsConstructor
public class Link {
    private final String web;

    public Link toLink(String link){
        return new Link(link);
    }
}
