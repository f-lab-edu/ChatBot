package com.flab.fire_inform.domains.crawling.service;

import com.flab.fire_inform.domains.conversation.dto.newsList.ListItem;

import java.io.IOException;
import java.util.List;

/**
 * 뉴스 크롤링 모델링
 */
public interface NewsCrawlling {

    List<String> getNaverNewsContents(String domain) throws IOException;
   // int getPgaing(String url) throws IOException;
    String convertURL(String domain);
    List<ListItem> getNaverNewsListEconomyContents(String domain)throws IOException;
}
