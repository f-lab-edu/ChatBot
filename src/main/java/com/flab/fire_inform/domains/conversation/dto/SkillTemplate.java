package com.flab.fire_inform.domains.conversation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.List;

@ToString
@Getter

public class SkillTemplate<T> {
    private T outputs;

    public SkillTemplate(T t){
        this.outputs = t;
    }
}
