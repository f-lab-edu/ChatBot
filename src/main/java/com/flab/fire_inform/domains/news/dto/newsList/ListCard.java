package com.flab.fire_inform.domains.news.dto.newsList;

import com.flab.fire_inform.domains.news.dto.Component;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@ToString
@Getter
public class ListCard implements Serializable, Component {

    private final ListItem header;
    private final List<ListItem> items;
    private  List<Button> buttons;

    public ListCard(ListCardBuilder listCardBuilder) {
        this.header = listCardBuilder.header;
        this.items = listCardBuilder.items;
        this.buttons = listCardBuilder.buttons;
    }

    public static ListCardBuilder builder(ListItem header, List items){
        return new ListCardBuilder(header, items);
    }
    public static class ListCardBuilder{

        private final ListItem header;
        private final List items;

        private  List<Button> buttons;

        private ListCardBuilder(ListItem header, List items){
            this.header = header;
            this.items = items;
        }

        public ListCardBuilder buttons(List<Button> buttons){
            this.buttons = buttons;
            return this;
        }

        public ListCard build(){
            return new ListCard(this);
        }

    }

}
