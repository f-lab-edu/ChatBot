package com.flab.fire_inform.domains.news.service;

import com.flab.fire_inform.domains.news.dto.newsList.ListItem;

import java.io.IOException;
import java.util.List;

/**
 * 뉴스 크롤링 모델링
 */
public interface NewsCrawlling {
    List<String> getNewsContents(String domain) throws IOException;
   // int getPgaing(String url) throws IOException;
    String convertURL(String domain);
    List<ListItem> getNewsListForKaKao(String url, String domain)throws IOException;
}
