package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.entity.Recruit;
import com.flab.fire_inform.domains.crawling.mapper.RecruitMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class KakaoCrawler implements JobCrawler {

    private final RecruitMapper recruitMapper;

    public KakaoCrawler(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    @Transactional
    @Override
    public List<Recruit> crawling() {
        try {
            log.info("================== Kakao Crawling Start ===================");
            String kakaoUrl = "https://careers.kakao.com";

            // 1. 전체 페이지수 구하기
            Document document = Jsoup.connect(kakaoUrl + "/jobs?page=1").get();
            String lastPageUrl = Objects.requireNonNull(document.getElementsByClass("change_page btn_lst").first()).attr("href");
            int indexOfTotalPage = lastPageUrl.indexOf("page=") + 5;
            int totalPage = Integer.parseInt(lastPageUrl.substring(indexOfTotalPage, indexOfTotalPage + 1));
            log.info("totalPage : {}", totalPage);

            // 2. 크롤링 데이터 담을 List 선언
            int currentPage = 1;
            Document doc;
            String url = kakaoUrl + "/jobs?page=";
            List<Recruit> recruits = new ArrayList<>();

            // 3. 채용 공고 Page url을 수집할 list 선언
            List<String> recruitLinks = new ArrayList<>();
            while (currentPage <= totalPage) {
                String fixedUrl = url + currentPage++;
                doc = Jsoup.connect(fixedUrl).get();
                Elements linkTags = doc.getElementsByClass("link_jobs");
                for (Element linkTag : linkTags) {
                    String link = linkTag.attr("href");
                    //log.info("link : {}", link);
                    recruitLinks.add(link);
                }
            }
            log.info("totalCountOfRecruits : {}", recruitLinks.size());

            // 4. 채용 공고 정보 크롤링
            for (String recruitLink : recruitLinks) {
                log.info("-------- Recruit --------");
                String link = kakaoUrl + recruitLink;

                doc = Jsoup.connect(link).get();
                String title = Objects.requireNonNull(doc.getElementsByClass("tit_jobs").first()).text();

                String company = null;
                String workerType = null;
                String dueDate = null;
                String address = null;
                String career = null;

                if (title.contains("신입") && title.contains("경력")) {
                    career = "경력무관";
                } else if (title.contains("경력")) {
                    career = "경력";
                } else if (title.contains("신입")) {
                    career = "신입";
                }

                Elements headerKeys = doc.select(".list_info dt");
                Elements headerValues = doc.select(".list_info dd");
                for (int i = 0; i < headerKeys.size(); i++) {
                    if (headerKeys.get(i).text().equals("회사정보")) {
                        company = headerValues.get(i).text();
                    } else if (headerKeys.get(i).text().equals("직원유형")) {
                        workerType = headerValues.get(i).text();
                    } else if (headerKeys.get(i).text().equals("영입마감일")) {
                        dueDate = headerValues.get(i).text();
                    } else if (headerKeys.get(i).text().equals("근무지 정보")) {
                        address = headerValues.get(i).text();
                    }
                }
                log.info("title : {}", title);
                log.info("company : {}", company);
                log.info("career : {}", career);
                log.info("dueDate : {}", dueDate);
                log.info("address : {}", address);
                log.info("workerType : {}", workerType);
                log.info("link : {}", link);

                recruits.add(new Recruit.Builder(title, company, link)
                        .career(career)
                        .dueDate(dueDate)
                        .address(address)
                        .workerType(workerType)
                        .Build());
            }

            // 5. DB 데이터 가져오기
            List<Recruit> findRecruits = recruitMapper.findByCompany("카카오");

            // 6. DB와 크롤링 데이터 비교후 중복 제거
            List<Recruit> deduplicateRecruits = DeduplicationUtils.deduplicate(recruits, findRecruits);

            // 7. 저장
            for (Recruit recruit : deduplicateRecruits) {
                recruitMapper.save(recruit);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.info("================== Kakao Crawling End =====================");
        }

        return null;
    }
}
