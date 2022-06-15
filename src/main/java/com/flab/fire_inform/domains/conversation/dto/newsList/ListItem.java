package com.flab.fire_inform.domains.conversation.dto.newsList;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class ListItem {
    private String title;
    private String description;
    private Link link;

    private ListItem(ListItemBuilder builder) {
        this.title = builder.title;
        this.description = builder.description;
        this.link = builder.link;
    }
    public static ListItemBuilder builder(String title){
        return new ListItemBuilder(title);
    }

    public static class ListItemBuilder{
        private String title;
        private String description;
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

        public ListItem build(){
            return new ListItem(this);
        }
    }

}
