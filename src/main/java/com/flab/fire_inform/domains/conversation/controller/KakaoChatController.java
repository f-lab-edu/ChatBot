package com.flab.fire_inform.domains.conversation.controller;

import com.flab.fire_inform.domains.conversation.dto.newsList.*;
import com.flab.fire_inform.domains.crawling.service.NewsCrawlling;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class KakaoChatController {
    private final NewsCrawlling newsCrawlling;

    public KakaoChatController(NewsCrawlling newsCrawlling){
        this.newsCrawlling = newsCrawlling;
    }

    @RequestMapping(value = "/api/check/{domain}" , method= {RequestMethod.POST , RequestMethod.GET },headers = {"Accept=application/json"})
    public SkillResponse newsListAPI(@PathVariable(required = false) String domain,
                                     @RequestBody(required = false) Map<String, Object> params) throws IOException {

        String url = newsCrawlling.convertURL(domain);
        // 본문 아이템 리스트
        String date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH").format(LocalDateTime.now()) + "시 날짜 경제 뉴스입니다.";
        ListItem header = ListItem.builder(date).build();
        List<ListItem> items = newsCrawlling.getNaverNewsListEconomyContents(url).subList(0,4);

        // 버튼 생성
        List<Button> buttons = new ArrayList<Button>();
        buttons.add(new Button(url));

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
}
