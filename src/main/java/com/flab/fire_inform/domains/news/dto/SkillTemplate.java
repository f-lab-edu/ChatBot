package com.flab.fire_inform.domains.news.dto;

import java.util.List;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter

public class SkillTemplate<T extends Component> {

    private List<T> outputs;

    public SkillTemplate(List<T> t) {
        this.outputs = t;
    }
}
