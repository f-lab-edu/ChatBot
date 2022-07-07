package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Component
public class KakaoRecruitCrawler implements JobCrawler {

    private final RecruitMapper recruitMapper;

    public KakaoRecruitCrawler(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    @Override
    public void crawling() {
        try {
            log.info("================== Kakao Recruits Crawling Start ===================");
            String kakaoUrl = "https://careers.kakao.com";

            int totalPage = getTotalPage(kakaoUrl);

            int currentPage = 1;
            String url = kakaoUrl + "/jobs?page=";
            List<Recruit> recruits = new ArrayList<>();

            List<String> recruitLinks = getRecruitLinks(totalPage, currentPage, url);

            crawlRecruitDetail(kakaoUrl, recruits, recruitLinks);

            List<Recruit> findRecruits = recruitMapper.findByCompany("카카오");

            List<Recruit> recruitsToAdd = new ArrayList<>();
            List<Recruit> recruitsToUpdate = new ArrayList<>();
            deduplicate(recruits, findRecruits, recruitsToAdd, recruitsToUpdate);

            log.info("recruitsToAdd.size = {}", recruitsToAdd.size());
            log.info("recruitsToUpdate.size = {}", recruitsToUpdate.size());

            for (Recruit recruit : recruitsToAdd) {
                recruitMapper.save(recruit);
            }

            for (Recruit recruit : recruitsToUpdate) {
                recruitMapper.update(recruit);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            log.info("================== Kakao Recruits Crawling End =====================");
        }
    }

    private void deduplicate(List<Recruit> recruits, List<Recruit> findRecruits, List<Recruit> recruitsToAdd, List<Recruit> recruitsToUpdate) {
        for (Recruit recruit : recruits) {
            boolean isAlreadyIn = false;
            String title = recruit.getTitle();
            for (Recruit findRecruit : findRecruits) {
                if(title.equals(findRecruit.getTitle())) {
                    recruit.setId(findRecruit.getId());
                    isAlreadyIn = true;
                    break;
                }
            }

            if(!isAlreadyIn) {
                recruitsToAdd.add(recruit);
            } else {
                recruitsToUpdate.add(recruit);
            }
        }
    }


    private void crawlRecruitDetail(String kakaoUrl, List<Recruit> recruits, List<String> recruitLinks) throws IOException {
        Document doc;
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
                    .build());
        }
    }

    private List<String> getRecruitLinks(int totalPage, int currentPage, String url) throws IOException {
        Document doc;
        List<String> recruitLinks = new ArrayList<>();
        while (currentPage <= totalPage) {
            String fixedUrl = url + currentPage++;
            doc = Jsoup.connect(fixedUrl).get();
            Elements linkTags = doc.getElementsByClass("link_jobs");
            for (Element linkTag : linkTags) {
                String link = linkTag.attr("href");
                recruitLinks.add(link);
            }
        }
        log.info("totalCountOfRecruits : {}", recruitLinks.size());
        return recruitLinks;
    }

    private int getTotalPage(String kakaoUrl) throws IOException {
        Document document = Jsoup.connect(kakaoUrl + "/jobs?page=1").get();
        String lastPageUrl = Objects.requireNonNull(document.getElementsByClass("change_page btn_lst").first()).attr("href");
        int indexOfTotalPage = lastPageUrl.indexOf("page=") + 5;
        int totalPage = Integer.parseInt(lastPageUrl.substring(indexOfTotalPage, indexOfTotalPage + 1));
        log.info("totalPage : {}", totalPage);
        return totalPage;
    }
}
