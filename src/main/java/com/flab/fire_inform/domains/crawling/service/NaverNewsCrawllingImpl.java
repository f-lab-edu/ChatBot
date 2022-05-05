package com.flab.fire_inform.domains.crawling.service;
import com.flab.fire_inform.domains.crawling.dto.EconomyNewsUrl;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class NaverNewsCrawllingImpl implements NewsCrawlling {

    private String URL ;
    /**
     * @return newsList
     * @throws IOException
     */
    @Override
    public List<String> getNaverNewscontents() throws IOException {
        List<String> contentsList = new ArrayList<>();
        int size = getPgaing();

        for(int i =1 ; i<= size; i++) {
            String pageUrl = URL + "&page=" + i;
            Document doc = Jsoup.connect(pageUrl).get();

            //헤드라인과 url 가져오기
            Elements contents = doc.select("tr > td.content > div.content > div.list_body > ul > li > dl > dt:not(.photo) > a");
            for (Element content : contents) {
                contentsList.add(content.toString());
            }
        }
        log.info("[NaverNesCrawllingImpl] :::::: contentList ={}" + contentsList.toString());
        return contentsList;
    }


    /**
     * 해당 url 페이지 체크
     * @return paging
     * @throws IOException
     */
    @Override
    public int getPgaing() throws IOException{
        Document doc = Jsoup.connect(URL).get();
        Elements contents = doc.select("tr > td.content > div.content > div.paging > a");

        int paging = contents.size() + 1; // 자신의 페이지는 제공 안함

        log.info("[NaverNesCrawllingImpl] :::::: paging ={}" + paging);
        return paging;
    }

    /**
     * url 분기
     * @param domain
     */
    @Override
    public void setUrl(String domain){
        if(domain.equals("FINANCE")){
            URL = EconomyNewsUrl.FINANCE.getUrl();
        }
        if(domain.equals("STOCK")){
            URL = EconomyNewsUrl.STOCK.getUrl();
        }
        if(domain.equals("INDUSTRY")){
            URL = EconomyNewsUrl.INDUSTRY.getUrl();
        }
        if(domain.equals("VENTURE")){
            URL = EconomyNewsUrl.VENTURE.getUrl();
        }
        if(domain.equals("ESTATE")){
            URL = EconomyNewsUrl.ESTATE.getUrl();
        }
        if(domain.equals("GLOBAL")){
            URL = EconomyNewsUrl.GLOBAL.getUrl();
        }
        if(domain.equals("LIFE")){
            URL = EconomyNewsUrl.LIFE.getUrl();
        }
        if(domain.equals("ORDINARY")){
            URL = EconomyNewsUrl.ORDINARY.getUrl();
        }

    }


}
