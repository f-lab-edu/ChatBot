package com.flab.fire_inform.domains.conversation.controller;

import com.flab.fire_inform.domains.conversation.dto.SkillResponse;
import com.flab.fire_inform.domains.conversation.dto.SkillTemplate;
import com.flab.fire_inform.domains.conversation.dto.newsList.*;
import com.flab.fire_inform.domains.crawling.dto.StockInformation;
import com.flab.fire_inform.domains.crawling.service.NewsCrawlling;
import com.flab.fire_inform.domains.crawling.util.StockCrawler;
import lombok.extern.slf4j.Slf4j;
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
    public SkillResponse newsListAPI(@PathVariable(required = false) String domain
                                        ,@RequestBody(required = false) Map<String, Object> params) throws IOException {

        // header setting
        String url = newsCrawlling.convertURL(domain);
        String date = DateTimeFormatter.ofPattern("MM월 dd일(E)").format(LocalDateTime.now()) + " 경제 뉴스입니다.";
        ListItem header = ListItem.builder(date).build();

        // 본문 아이템 리스트
        List<ListItem> items = newsCrawlling.getNewsListForKaKao(url).subList(0,4);

        // 버튼 리스트 생성
        List<Button> buttons = Button.list(url,"더 보기");

        // 여기는 listCard 생성
        ListCard listCard = ListCard.builder(header, items).buttons(buttons).build();
        HashMap<String,ListCard> listcardHashMap = new HashMap<>();
        listcardHashMap.put("listCard",listCard);

        List<HashMap<String,ListCard>> listCards = new ArrayList();
        listCards.add(listcardHashMap);


        SkillTemplate skillTemplate = new SkillTemplate(listCards);
        SkillResponse skillResponse = new SkillResponse("2.0", skillTemplate);

        return skillResponse;
    }

    @RequestMapping(value = "/api/stock", method= {RequestMethod.POST , RequestMethod.GET },headers = {"Accept=application/json"})
    public SkillResponse getStockInfo(@RequestBody(required = false) Map<String, Object> params) throws IOException {

        log.info("params={}",params);

        StockInformation stockInformation = stockCrawler.crawling(params);

        // header setting
        String date = DateTimeFormatter.ofPattern("MM월 dd일(E) ").format(LocalDateTime.now()) + stockInformation.getName() +" 주식";
        ListItem header = ListItem.builder(date).build();

        List<ListItem> items = new ArrayList<>();
        items.add(ListItem.builder(stockInformation.getPrice()).description(stockInformation.getHowDifferenttWithYesterday()).build());

        List<Button> buttons = Button.list(stockInformation.getUrl(),"차트 보러가기");

        ListCard listCard = ListCard.builder(header, items).buttons(buttons).build();
        HashMap<String,ListCard> listcardHashMap = new HashMap<>();
        listcardHashMap.put("listCard",listCard);

        List<HashMap<String,ListCard>> listCards = new ArrayList();
        listCards.add(listcardHashMap);


        SkillTemplate skillTemplate = new SkillTemplate(listCards);
        SkillResponse skillResponse = new SkillResponse("2.0", skillTemplate);


        return skillResponse;
    }
}
