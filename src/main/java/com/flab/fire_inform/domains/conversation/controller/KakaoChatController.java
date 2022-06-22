package com.flab.fire_inform.domains.conversation.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fire_inform.domains.conversation.dto.SkillResponse;
import com.flab.fire_inform.domains.conversation.dto.SkillTemplate;
import com.flab.fire_inform.domains.conversation.dto.newsList.*;
import com.flab.fire_inform.domains.crawling.dto.StockInformation;
import com.flab.fire_inform.domains.crawling.service.NewsCrawlling;
import com.flab.fire_inform.domains.crawling.util.GoogleStockCrawler;
import com.flab.fire_inform.domains.crawling.util.StockCrawler;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@Slf4j
public class KakaoChatController {
    private final NewsCrawlling newsCrawlling;
    private final StockCrawler stockCrawler;

    public KakaoChatController(NewsCrawlling newsCrawlling, StockCrawler stockCrawler){
        this.newsCrawlling = newsCrawlling;
        this.stockCrawler = stockCrawler;
    }

    @RequestMapping(value = "/api/news/{domain}" , method= {RequestMethod.POST , RequestMethod.GET },headers = {"Accept=application/json"})
    public SkillResponse newsListAPI(@PathVariable(required = false) String domain,
                                     @RequestBody(required = false) Map<String, Object> params) throws IOException {

        // header setting
        String url = newsCrawlling.convertURL(domain);
        String date = DateTimeFormatter.ofPattern("MM월 dd일(E)").format(LocalDateTime.now()) + " 경제 뉴스입니다.";
        ListItem header = ListItem.builder(date).build();

        // 본문 아이템 리스트
        List<ListItem> items = newsCrawlling.getNewsListForKaKao(url).subList(0,4);

        // 버튼 리스트 생성
        List<Button> buttons = Button.list(url);

        // 여기는 listCard 생성
        ListCard listCard = new ListCard(header, items, buttons);
        HashMap<String,ListCard> listcardHashMap = new HashMap<>();
        listcardHashMap.put("listCard",listCard);

        List<HashMap<String,ListCard>> listCards = new ArrayList();
        listCards.add(listcardHashMap);


        SkillTemplate skillTemplate = new SkillTemplate(listCards);
        SkillResponse skillResponse = new SkillResponse("2.0", skillTemplate);

        return skillResponse;
    }

    @RequestMapping(value = "/api/stock", method= {RequestMethod.POST , RequestMethod.GET },headers = {"Accept=application/json"})
    public StockInformation getStockInfo(@RequestBody(required = false) Map<String, Object> params) {
        StockInformation stockInformation;

        log.info("params={}",params);
        try{
             stockInformation = stockCrawler.crawling(params);

        }catch (Exception e){

            throw new CustomException(ErrorCode.COMPANY_NOT_FOUND);

        }
        return stockInformation;
    }
}
