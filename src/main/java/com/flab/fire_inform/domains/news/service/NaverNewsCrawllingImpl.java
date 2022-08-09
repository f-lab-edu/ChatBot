package com.flab.fire_inform.domains.news.service;
import com.flab.fire_inform.domains.news.dto.newsList.Link;
import com.flab.fire_inform.domains.news.dto.newsList.ListItem;
import com.flab.fire_inform.domains.crawling.dto.EconomyNewsUrl;
import com.flab.fire_inform.domains.crawling.dto.entity.News;
import com.flab.fire_inform.domains.news.mapper.NewsMapper;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class NaverNewsCrawllingImpl implements NewsCrawlling {

    private NewsMapper newsMapper;
    public NaverNewsCrawllingImpl(NewsMapper newsMapper){
        this.newsMapper = newsMapper;
    }
    /**
     * @return newsList
     * @throws IOException
     */
    @Override
    public List<String> getNewsContents(String url) throws IOException {
        List<String> contentsList = new ArrayList<>();
        int size = getPgaing(url);

        for(int i =1 ; i<= size; i++) {
            String pageUrl = url + "&page=" + i;
            Document doc = Jsoup.connect(pageUrl).get();
            Elements contents;
            //헤드라인과 url 가져오기

            /*
            main과 domain 별로 분리
             */
            if (url.equals(EconomyNewsUrl.MAIN.getUrl())){
                contents = doc.select("tr > td.content > div.content > div.list_body > div._persist " +
                        "> div.cluster > div.cluster_group > div.cluster_head > div.cluster_head_inner > div.cluster_head_topic_wrap > h2.cluster_head_topic > a");
            }else{
                contents = doc.select("tr > td.content > div.content > div.list_body > ul > li > dl > dt:not(.photo) > a");
            }

            for (Element content : contents) {
                contentsList.add(content.toString());
            }
        }
        log.info("[NaverNesCrawllingImpl] :::::: contentList ={}", contentsList.toString());
        return contentsList;
    }



    /**
    템플릿에 담아서 반환해주는 뉴스 목록 for Kakao
     - Url에 따라 페이지 수 체크
     - 모든 컨텐츠를 다 가져온다.\
     - 필요한 헤드와 짧은 내용, Url을 가져온다.
     - News객체를 만들어서 db에 저장
     - content가 제대로 조회되어 오면 바로 저장하고 반환
     -
     **/
    public List<ListItem> getNewsListForKaKao(String url, String domain) throws IOException {
        // try- catch는 이전에 저장된 데이터를 반환해주기 위해서
        try{
        List<ListItem> itemList = new ArrayList<>();
        int size = getPgaing(url);

        for(int i =1 ; i<= size; i++) {
            String pageUrl = url + "&page=" + i;
            Document doc = Jsoup.connect(pageUrl).get();
            Elements contents;
            //헤드라인과 url 가져오기

            /*
            ListItem을 만들고 카드에 헤더랑 링크 담고 보내기
             */
            if (url.equals(EconomyNewsUrl.MAIN.getUrl())){

                contents = doc.select("tr > td.content > div.content > div.list_body > div._persist " +
                        "> div.cluster > div.cluster_group > div.cluster_head > div.cluster_head_inner > div.cluster_head_topic_wrap > h2.cluster_head_topic > a");

            }else{
                contents = doc.select("tr > td.content > div.content > div.list_body > ul > li > dl > dt:not(.photo) > a");
            }

            for (Element content : contents) {

                Link link = new Link(content.absUrl("href"));
                String contentss = content.text();

                itemList.add(ListItem.builder(contentss).link(link).build());
            }

            // 혹시 넘어올 경우 대비
            if(itemList.isEmpty()){
                itemList = getNewsList("NAVER",domain);
            }

            if(!itemList.isEmpty() && !getNewsList("NAVER",domain).isEmpty()) {
                delete("NAVER",domain);
            }

            saveNewsList(itemList,url,domain);
        }

        log.info("[NaverNesCrawllingImpl] :::::: textList ={}", itemList.toString());
        return itemList;

        }catch (IOException e){
            // 크롤링에서 문제가 생기면 디비에서 가장 최근 데이터 조회
            return getNewsList("NAVER",domain);
        }
    }

    @Transactional
    public boolean saveNewsList( List<ListItem> itemList , String url, String domain) {
        for ( ListItem item : itemList) {
            News news = News.builder("NAVER", domain, item.getTitle(), item.getLink().getWeb(),url).build();
            newsMapper.insertNewsList(news);
        }
        return true;
    }
    @Transactional
    public boolean delete(String site, String domain){

        newsMapper.deleteNewsList(site,domain);

        return true;
    }

    public List<ListItem> getNewsList(String site,String domain){

        List<ListItem> returnList = new ArrayList<>();
        List<News> list = newsMapper.getNewsList(site,domain);

        for ( News news : list){
            ListItem listItem = ListItem.builder(news.getTitle()).link(new Link(news.getLink())).imageUrl(news.getNewsTotalUrl()).build();
            returnList.add(listItem);
        }

        return returnList;
    }



    /**
     * 해당 url 페이지 체크
     * @return paging
     * @throws IOException
     */
    private int getPgaing(String url) throws IOException{

        Document doc = Jsoup.connect(url).get();
        Elements contents = doc.select("tr > td.content > div.content > div.paging > a");
        // 자신의 페이지는 count 안된다.
        int paging = contents.size() + 1;

        log.info("[NaverNesCrawllingImpl] :::::: paging ={}", paging);

        return paging;
    }

    /*
     * url 분기
     */
    @Override
    public String convertURL(String domain){
        // optional 연습겸 사용
        try{
            Optional<String> optionalMain = Optional.of(domain);
            return EconomyNewsUrl.valueOf(optionalMain.orElseThrow(()
                                         -> new CustomException(ErrorCode.DOMAIN_NOT_FOUND))).getUrl();
        }catch (IllegalArgumentException e){
            throw new CustomException(ErrorCode.DOMAIN_NOT_FOUND);
        }

        /* url로 domain 추출
        List<EconomyNewsUrl> collect = Arrays.stream(EconomyNewsUrl.values()).filter(economyNewsUrl -> url.equals(economyNewsUrl.getUrl())).collect(Collectors.toList());
        EconomyNewsUrl economyNewsUrl = collect.get(0); */
    }
}
