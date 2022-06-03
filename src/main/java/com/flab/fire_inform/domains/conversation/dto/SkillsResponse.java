package com.flab.fire_inform.domains.conversation.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class SkillsResponse {

    private Answer answer;
    private List<OutputContext> outputContext;

    private SkillsResponse(Builder builder){
        this.answer = builder.answer;
        this.outputContext = builder.outputContext;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static class Builder{
        private Answer answer;
        private List<OutputContext> outputContext;

        private Builder(){}

        public Builder answer(Answer answer){
            this.answer = answer;
            return this;
        }

        public Builder outputContext(List<OutputContext> outputContext){
            this.outputContext = outputContext;
            return this;
        }

        public SkillsResponse build(){
            return new SkillsResponse(this);
        }

    }

}
