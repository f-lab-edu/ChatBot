package com.flab.fire_inform.domains.conversation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.List;

@ToString
@Getter

public class SkillTemplate<T extends Component> {
    private List<T> outputs;

    public SkillTemplate(List<T> t){
        this.outputs = t;
    }
}
