package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.dto.StockInformation;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
@Slf4j
public class GoogleStockCrawler implements StockCrawler {
    private final String stockSearchUrl = "https://www.google.com/search?q=";

    // 크롤링하는 책임만 가지고 있다.
    public StockInformation crawling(Map<String,Object> params) throws IOException {
        Document doc;
        String seachUrlWithKeyword = gettingLinkFromKeyword(params);

        try{
        doc = Jsoup.connect(seachUrlWithKeyword).get();


        // HTML 구문 및 이름
        Element html = Objects.requireNonNull(doc.getElementsByClass("PZPZlf").get(4));
        String name = doc.getElementsByClass("PZPZlf").get(1).text();

        // 가격.
        String price = html.getElementsByClass("IsqQVc NprOob wT3VGc").first().text()
                        + " " + html.getElementsByClass("knFDje").first().text();
        // 날짜.
        String date = html.select("span.TgMHGc > span").get(1).text();
        String diffrentWithYesterdatDate = html.select("span.WlRRw.IsqQVc > span").get(0).text()
                                        +" "+ html.select("span.WlRRw.IsqQVc > span").get(1).text();

        if (diffrentWithYesterdatDate.charAt(0) == 43){

            diffrentWithYesterdatDate += " 상승";

        }else {

            diffrentWithYesterdatDate += " 하락";

        }


        return StockInformation.builder(name,price)
                                .date(date)
                                .howDifferenttWithYesterday(diffrentWithYesterdatDate)
                                .url(seachUrlWithKeyword)
                                .build();
        }catch (Exception e){
            throw new CustomException(ErrorCode.COMPANY_NOT_FOUND);
        }
    }

    // 받은 json 형식에서 사용자 발화 추출 후 검색 링크 반환
    public String gettingLinkFromKeyword(Map<String,Object> params){
        log.info("int gettingkeyword params={}", params);

        Map<String,Object> mapper = (HashMap<String,Object>)params.get("userRequest");
        String word = (String)mapper.get("utterance");


        return stockSearchUrl + word + "주식";
    }


}
