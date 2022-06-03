package com.flab.fire_inform.domains.conversation.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class Answer {

    private String status;
    private String sentence;
    private String dialog;

    private Answer(AnswerBuilder builder){
        this.status = builder.status;
        this.sentence = builder.sentence;
        this.dialog = builder.dialog;
    }

    public static AnswerBuilder builder(){
        return new AnswerBuilder();
    }

    public static class AnswerBuilder{
        private String status;
        private String sentence;
        private String dialog;

        private AnswerBuilder(){};

        public AnswerBuilder status(String status){
            this.status = status;
            return this;
        }
        public AnswerBuilder sentence(String sentence){
            this.sentence = sentence;
            return this;
        }
        public AnswerBuilder dialog(String dialog){
            this.dialog = dialog;
            return this;
        }

        public Answer build(){
            return new Answer(this);
        }

    }


}
