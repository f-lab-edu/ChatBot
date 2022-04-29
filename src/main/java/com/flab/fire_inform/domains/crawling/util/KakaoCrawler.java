package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.entity.Recruit;
import com.flab.fire_inform.domains.crawling.mapper.RecruitMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class KakaoCrawler implements JobCrawler {

    private final RecruitMapper recruitDao;

    public KakaoCrawler(RecruitMapper recruitDao) {
        this.recruitDao = recruitDao;
    }

    @Override
    public List<Recruit> crawling() {
        try {
            log.info("================== Crawling Start ===================");
            // 1. 전체 페이지수 구하기
            Document document = Jsoup.connect("https://careers.kakao.com/jobs?page=1").get();
            String lastPageUrl = Objects.requireNonNull(document.getElementsByClass("change_page btn_lst").first()).attr("href");
            int indexOfTotalPage = lastPageUrl.indexOf("page=") + 5;
            int totalPage = Integer.parseInt(lastPageUrl.substring(indexOfTotalPage, indexOfTotalPage + 1));
            log.info("totalPage : {}", totalPage);

            // 2. 크롤링 데이터 담을 List 선언
            int currentPage = 1;
            Document doc = null;
            String url = "https://careers.kakao.com/jobs?page=";
            List<Recruit> recruits = new ArrayList<>();
            while (currentPage > totalPage) {
                String fixedUrl = url + currentPage++;
                doc = Jsoup.connect(fixedUrl).get();

                // 3. 채용 공고 정보 크롤링

                // 4. 채용 공고 상세 화면 url로 이동후 데이터 크롤링

                // 5. list.add(recruit)

                break;
            }
            log.info(String.valueOf(doc));
            Elements elements = doc.select("h4[class=tit_jobs]");
            log.info(String.valueOf(elements));

            // 6. DB와 크롤링 데이터 비교후 중복 제거

            // 7. 저장
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.info("================== Crawling End ===================");
        }

        return null;
    }
}
