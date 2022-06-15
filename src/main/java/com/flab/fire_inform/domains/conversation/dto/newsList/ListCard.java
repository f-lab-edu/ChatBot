package com.flab.fire_inform.domains.conversation.dto.newsList;

import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.Array;
import java.util.List;

@ToString
@Getter
public class ListCard {

    private final ListItem header;
    private final List items;

    private final List<Button> buttons;


    public ListCard(ListItem header, List items, List button) {
        this.header = header;
        this.items = items;
        this.buttons = button;
    }
}
