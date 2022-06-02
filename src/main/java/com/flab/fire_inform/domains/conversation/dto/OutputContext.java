package com.flab.fire_inform.domains.conversation.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@ToString
public class OutputContext {

    private String news;

    private OutputContext(OutputContextBuilder builder){
        this.news = builder.news;
    }

    public static OutputContextBuilder  builder(){
        return new OutputContextBuilder();
    }

    public static class OutputContextBuilder{
        private String news;



        public OutputContextBuilder news(String news){

            this.news = news;

            return this;
        }

        public OutputContext build(){
            return new OutputContext(this);
        }


    }


}
