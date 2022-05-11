package com.flab.fire_inform.crawlling;

import com.flab.fire_inform.domains.crawling.dto.EconomyNewsUrl;
import com.flab.fire_inform.domains.crawling.service.NaverNewsCrawllingImpl;
import com.flab.fire_inform.domains.crawling.service.NewsCrawlling;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewsCrawllingTest {

    static String url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=259";

    @Test
    @DisplayName("크롤링 테스트")
    void crawllingTest() throws IOException {

        List<String> contentsList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        Elements contents = doc.select("tr > td.content > div.content > div.paging > a");

        int size = contents.size() + 1; // 자신의 페이지는 제공 안함

        for(int i =1 ; i<=size; i++) {
            String pageUrl = url + "&page=" + i;
            doc = Jsoup.connect(pageUrl).get();
            //div.cluster_body
            contents = doc.select("tr > td.content > div.content > div.list_body > ul > li > dl > dt:not(.photo) > a");
            for (Element content : contents) {
                contentsList.add(content.toString());
            }
        }
        System.out.println("size = " + contentsList.size());
        for(String arr : contentsList){
            System.out.println("arr = " + arr);
        }
    }

    @Test
    @DisplayName("페이지 개수 제공")
    void getPage() throws  IOException{
        Document doc = Jsoup.connect(url).get();
        Elements contents = doc.select("tr > td.content > div.content > div.paging > a");

        int paging = contents.size() + 1; // 자신의 페이지는 제공 안함

        System.out.println("paging = " + paging);
    }

    @Test
    @DisplayName("서비스 작동 여부 체크")
    void servicetest() throws IOException{
        NewsCrawlling nnc = new NaverNewsCrawllingImpl();
        List<String> contentList = nnc.getNaverNewscontents();
        for(String arr : contentList){
            System.out.println("arr = " + arr);
        }
    }

    @Test
    @DisplayName("url enum 테스트 ")
    void testEnumEconomy(){
        Assertions.assertThat(EconomyNewsUrl.FINANCE.getUrl()).isEqualTo("https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=259");
    }
}