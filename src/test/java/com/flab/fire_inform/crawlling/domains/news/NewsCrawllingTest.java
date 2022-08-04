package com.flab.fire_inform.crawlling.domains.news;

import com.flab.fire_inform.domains.crawling.dto.EconomyNewsUrl;
import com.flab.fire_inform.domains.news.service.NewsCrawlling;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import com.flab.fire_inform.global.response.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@WebAppConfiguration
public class NewsCrawllingTest {

    static String url = "https://news.naver.com/main/list.naver?mode=LS2D&mid=shm&sid1=101&sid2=259";
    @Autowired
    NewsCrawlling newsCrawlling;

   /* public NewsCrawllingTest(NewsCrawlling newsCrawlling){
        this.newsCrawlling = newsCrawlling;
    }*/

    @Test
    @DisplayName("크롤링 테스트")
    void crawllingTest() throws IOException {

        List<String> contentsList = new ArrayList<>();

        Document doc = Jsoup.connect(url).get();
        Elements contents = doc.select("tr > td.content > div.content > div.paging > a");

        int size = contents.size() + 1; // 자신의 페이지는 제공 안함

        for (int i = 1; i <= size; i++) {
            String pageUrl = url + "&page=" + i;
            doc = Jsoup.connect(pageUrl).get();
            //div.cluster_body
            contents = doc.select("tr > td.content > div.content > div.list_body > ul > li > dl > dt:not(.photo) > a");
            for (Element content : contents) {
                contentsList.add(content.toString());
            }
        }
        System.out.println("size = " + contentsList.size());
        for (String arr : contentsList) {
            System.out.println("arr = " + arr);
        }
    }
    @Test
    @DisplayName("페이지 개수 제공")
    void getPage() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Elements contents = doc.select("tr > td.content > div.content > div.paging > a");

        int paging = contents.size() + 1; // 자신의 페이지는 제공 안함

        System.out.println("paging = " + paging);
    }

    @ParameterizedTest(name = "{index}, {displayName}, message={0}")
    @DisplayName("url enum convert 테스트 ")
    @ValueSource(strings = {"MAIN","FINANCE","STOCK"})
    void testEnumEconomy(String domain) {
       String url = EconomyNewsUrl.valueOf(domain).getUrl();
       assertThat(EconomyNewsUrl.valueOf(domain).getUrl()).isEqualTo(url);
    }

    @Test
    @DisplayName("Response 객체 빌더 테스트")
    void testBuilder() {
        Testt smalltest = new Testt(1, 2);
        Response<Testt> test = new Response.Builder<Testt>(200, "this is message").data(smalltest).build();
        Response<Testt> test2 = new Response.Builder<Testt>(500, "Server Error").data(smalltest).build();
        ResponseEntity test3 = Response.toResponseEntitySuccess(200, "this is message", smalltest);
        ResponseEntity test4 = Response.toResponseEntityError(ErrorCode.DOMAIN_NOT_FOUND);

        System.out.println(test.toString());
        System.out.println(test2.toString());
        System.out.println(test3.toString());
        System.out.println(test4.toString());
    }

    static class Testt {
        //빌더의 객체 등록 여부 체크를 위한 내부 클래스 생성
        int b;
        int a;

        public Testt(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }



}
