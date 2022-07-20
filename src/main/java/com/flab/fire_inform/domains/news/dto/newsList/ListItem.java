package com.flab.fire_inform.domains.news.dto.newsList;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ListItem {
    private final String title;
    private final String description;
    private final String imageUrl;
    private final Link link;

    private ListItem(ListItemBuilder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.imageUrl = builder.imageUrl;
        this.link = builder.link;
    }
    public static ListItemBuilder builder(String title){
        return new ListItemBuilder(title);
    }

    public static class ListItemBuilder{
        private String title;
        private String description;
        private String imageUrl;
        private Link link;

        private ListItemBuilder(String title){
            this.title = title;
        }


        public ListItemBuilder link(Link link){
            this.link = link;
            return this;
        }
        public ListItemBuilder description(String description){
            this.description = description;
            return this;
        }

        public ListItemBuilder imageUrl(String imageUrl){
            this.imageUrl = imageUrl;
            return this;
        }

        public ListItem build(){
            return new ListItem(this);
        }
    }

}
