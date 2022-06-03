package com.flab.fire_inform.domains.crawling.service;

import com.flab.fire_inform.domains.conversation.dto.OutputContext;

import java.io.IOException;
import java.util.List;

/**
 * 뉴스 크롤링 모델링
 */
public interface NewsCrawlling {

    List<String> getNaverNewsContents(String url) throws IOException;
    List<OutputContext> getNaverNewsEconomyContents(String url) throws IOException;
   // List<String> getNaverNewsMainPointContents(String url) throws IOException;
    int getPgaing(String url) throws IOException;
    String convertURL(String domain);
}
