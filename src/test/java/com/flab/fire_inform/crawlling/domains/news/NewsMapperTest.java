package com.flab.fire_inform.crawlling.domains.news;

import com.flab.fire_inform.domains.news.dto.newsList.ListItem;
import com.flab.fire_inform.domains.crawling.dto.entity.News;
import com.flab.fire_inform.domains.news.mapper.NewsMapper;
import com.flab.fire_inform.domains.news.service.NaverNewsCrawllingImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@WebAppConfiguration
public class NewsMapperTest {

    @Mock
    NewsMapper newsMapperMock;
    @Autowired
    NewsMapper newsMapper;
    @Autowired
    NaverNewsCrawllingImpl naverNewsCrawlling;


    @Test
    @DisplayName("News 엔티티 테스트")
    void makeNewsEntity(){
        News news = News.builder("NAVER","FINANCE","LG전자 주식이 200% 올랐습니다.","http://www.test.com?newsNumber = 0","www.test.com")
                        .build();
        System.out.println("news = " + news);
        News news2 = News.builder("NAVER","FINANCE","LG전자 주식이 200% 올랐습니다.","http://www.test.com?newsNumber = 0","www.test.com")
                .description("디스크립션 테스트")
                .build();
        System.out.println("news2 = " + news2);
    }

    @Test
    @DisplayName("News Mapper 조회 테스트")
    void getNewsListFromDBUnit(){
        List<News> list = new ArrayList<>();
        // 단위 테스트 목적
        News news = News.builder("NAVER","FINANCE","메인 본문","http://www.test.com","https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101")
                .build();
        list.add(news);

        when(newsMapperMock.getNewsList("NAVER","MAIN")).thenReturn(list);

        List<News> news2 = newsMapperMock.getNewsList("NAVER","MAIN");
        for ( News oneNews : news2){
            assertThat(oneNews.getNewsTotalUrl()).isEqualTo("https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101");
        }
    }

    @Test
    @DisplayName("news Mapper 통합테스트")
    void getNewsListFromDBTotal(){
        List<News> news = newsMapper.getNewsList("네이버","MAIN");
        for ( News oneNews : news){
            assertThat(oneNews.getNewsTotalUrl()).isEqualTo("https://news.naver.com/main/main.naver?mode=LSD&mid=shm&sid1=101");
        }

    }

    @Test
    @DisplayName("뉴스 저장 테스트")
    void saveNews(){
        News news2 = News.builder("NAVER","FINANCE","LG전자 주식이 200% 올랐습니다.","http://www.test.com?newsNumber = 0","www.test.com")
                .description("디스크립션 테스트")
                .build();
        int insertResult = newsMapper.insertNewsList(news2);
        int deleteResult =  newsMapper.deleteNewsList("NAVER","FINANCE");
       assertThat(deleteResult + insertResult).isEqualTo(2);
    }

    @Test
    @DisplayName("뉴스 리스트 저장 테스트")
    void saveNewsList(){
        News news2 = News.builder("NAVER","FINANCE","LG전자 주식이 200% 올랐습니다.","http://www.test.com?newsNumber = 0","www.test.com")
                .description("디스크립션 테스트")
                .build();
        int insertResult = newsMapper.insertNewsList(news2);
        int deleteResult =  newsMapper.deleteNewsList("NAVER","FINANCE");
        assertThat(deleteResult).isEqualTo(1);
    }

    @Test
    @DisplayName("뉴스리스트 디비 조회 테스트")
    void getList() {
        List<ListItem> list = naverNewsCrawlling.getNewsList("NAVER", "MAIN")
                                                .stream().limit(5).collect(Collectors.toList());
        assertThat(list.size()).isEqualTo(5);

    }
}
