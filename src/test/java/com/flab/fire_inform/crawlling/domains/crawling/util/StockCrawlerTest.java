package com.flab.fire_inform.crawlling.domains.crawling.util;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fire_inform.domains.crawling.dto.StockInformation;
import com.flab.fire_inform.domains.crawling.util.StockCrawler;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
public class StockCrawlerTest {

    @Autowired
    public StockCrawler stockCrawler;

    @Test
    @DisplayName("주식 크롤러 테스트")
    void getStockPrice() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String st = "{\n" +
                "  \"userRequest\": {\n" +
                "    \"timezone\": \"Asia/Seoul\",\n" +
                "    \"utterance\": \"삼성\",\n" +
                "    \"lang\": null,\n" +
                "    \"user\": {\n" +
                "      \"id\": \"972656\",\n" +
                "      \"type\": \"accountId\",\n" +
                "      \"properties\": {}\n" +
                "    }\n" +
                "  }" +
                "}";
        Map<String, Object> prameter = objectMapper.readValue(st, new TypeReference<Map<String, Object>>(){});
        StockInformation stockInformation = stockCrawler.crawling(prameter);
        assertThat("삼성전자").isEqualTo(stockInformation.getName());
    }
}
