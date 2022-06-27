package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.dto.StockInformation;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
public class StockCrawlerTest {

    @Test
    @DisplayName("주식 크롤러 테스트")
    void getStockPrice() throws IOException {
        StockCrawler stockCrawler = new GoogleStockCrawler();

        String word = "삼성";
        StockInformation test =  stockCrawler.crawling(word);
        Assertions.assertThat(test.getName()).isEqualTo("삼성전자");
    }
}
