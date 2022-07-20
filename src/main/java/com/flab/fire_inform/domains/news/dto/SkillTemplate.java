package com.flab.fire_inform.domains.news.dto;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@ToString
@Getter

public class SkillTemplate<T extends Component> {
    private List<T> outputs;

    public SkillTemplate(List<T> t){
        this.outputs = t;
    }
}
