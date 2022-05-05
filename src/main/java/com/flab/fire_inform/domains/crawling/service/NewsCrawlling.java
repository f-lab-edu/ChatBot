package com.flab.fire_inform.domains.crawling.service;

import java.io.IOException;
import java.util.List;

/**
 * 뉴스 크롤링 모델링
 */
public interface NewsCrawlling {

    List<String> getNaverNewscontents() throws IOException;
    int getPgaing() throws IOException;
    void setUrl(String domain);
}
