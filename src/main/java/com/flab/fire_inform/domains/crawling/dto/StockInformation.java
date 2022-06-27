package com.flab.fire_inform.domains.crawling.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StockInformation {
    private final String name;
    private final String price;
    private final String date;
    private final String howDifferenttWithYesterday;

    private final String url;

    private StockInformation(StockBuilder stockBuilder) {
        this.name = stockBuilder.name;
        this.price = stockBuilder.price;
        this.date = stockBuilder.date;
        this.howDifferenttWithYesterday = stockBuilder.howDifferenttWithYesterday;
        this.url = stockBuilder.url;
    }

    public static StockBuilder builder(String name, String price){
        return new StockBuilder(name, price);
    }

    public static class StockBuilder{
        private final String name;
        private final String price;
        private  String date;
        private  String howDifferenttWithYesterday;

        private String url;
        public StockBuilder(String name, String price){
            this.name = name;
            this.price = price;
        }

        public StockBuilder date(String date){
            this.date = date;
            return this;
        }

        public StockBuilder howDifferenttWithYesterday(String howDifferenttWithYesterday){
            this.howDifferenttWithYesterday = howDifferenttWithYesterday;
            return this;
        }
        public StockBuilder url(String url){
            this.url = url;
            return this;
        }


        public StockInformation build(){
            return new StockInformation(this);
        }
    }


}
