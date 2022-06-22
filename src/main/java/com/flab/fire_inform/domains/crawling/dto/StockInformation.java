package com.flab.fire_inform.domains.crawling.dto;

import com.flab.fire_inform.domains.conversation.dto.newsList.ListItem;
import com.flab.fire_inform.domains.crawling.util.StockCrawler;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class StockInformation {
    private final String name;
    private final String price;
    private final String date;
    private final String howDifferenttWithYesterday;

    private StockInformation(StockBuilder stockBuilder) {
        this.name = stockBuilder.name;
        this.price = stockBuilder.price;
        this.date = stockBuilder.date;
        this.howDifferenttWithYesterday = stockBuilder.howDifferenttWithYesterday;
    }

    public static StockBuilder builder(String name, String price){
        return new StockBuilder(name, price);
    }

    public static class StockBuilder{
        private final String name;
        private final String price;
        private  String date;
        private  String howDifferenttWithYesterday;
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

        public StockInformation build(){
            return new StockInformation(this);
        }
    }


}
