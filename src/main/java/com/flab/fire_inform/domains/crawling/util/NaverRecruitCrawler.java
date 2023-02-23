package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.exception.ElementParseException;
import com.flab.fire_inform.domains.crawling.exception.InvalidUrlException;
import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class NaverRecruitCrawler implements JobCrawler {

    private final RecruitMapper recruitMapper;

    public NaverRecruitCrawler(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    @Override
    public void crawling() {
        log.info("================== Naver Recruits Crawling Start ===================");
        String listUrl = "https://recruit.navercorp.com/rcrt/list.do?annoId=&sw=&subJobCdArr=1010001%2C1010004%2C1060001&sysCompanyCdArr=&empTypeCdArr=&entTypeCdArr=&workAreaCdArr=";

        List<String> recruitDetailLinks = getRecruitDetailLinks(listUrl);

        List<Recruit> crawledRecruits = mapCrawlingDataToObject(recruitDetailLinks);

        for (Recruit crawledRecruit : crawledRecruits) {
            recruitMapper.upsertRecruits(crawledRecruit);
        }

        log.info("================== Naver Recruits Crawling End =====================");
    }

    private List<String> getRecruitDetailLinks(String listUrl) {
        List<String> urls = new ArrayList<>();
        String detailUrl = "https://recruit.navercorp.com/rcrt/view.do?annoId=";
        Document document;

        try {
            document = Jsoup.connect(listUrl).get();
            Elements elements = document.select("a[class=card_link]");

            for (Element element : elements) {
                String annoId = element.attr("onclick");
                annoId = annoId.replace("show('", "");
                annoId = annoId.replace("')", "");
                log.info("annoId: {}", annoId);
                urls.add(detailUrl + annoId);
            }
        } catch (IOException e) {
            throw new InvalidUrlException(ErrorCode.INVALID_URL);
        } catch (Exception e) {
            throw new ElementParseException(ErrorCode.PARSE_INVALID_ELEMENT);
        }

        return urls;
    }

    private List<Recruit> mapCrawlingDataToObject(List<String> recruitDetailLinks) {
        List<Recruit> result = new ArrayList<>();
        for (String recruitDetailLink : recruitDetailLinks) {
            try {
                Document document = Jsoup.connect(recruitDetailLink).get();
                String title = document.select("h4[class=card_title]").get(0).text();
                String career = document.select(
                    "dt:contains(모집 경력)[class=blind] + dd[class=info_text]").get(0).text();
                String duringDate = document.select(
                    "dt:contains(모집 기간)[class=blind] + dd[class=info_text]").get(0).text();
                String dueDate = duringDate.split(" ")[2];
                String company = "네이버";
                String address = null;
                String workerType = document.select(
                    "dt:contains(모집 경력)[class=blind] + dd[class=info_text]").get(0).text();

                log.info("title: {}", title);
                log.info("career: {}", career);
                log.info("dueDate: {}", dueDate);
                log.info("company: {}", company);
                log.info("workType: {}", workerType);
                log.info("link: {}", recruitDetailLink);
                log.info("");

                result.add(new Recruit.Builder(title, company, recruitDetailLink)
                    .career(career)
                    .dueDate(dueDate)
                    .workerType(workerType)
                    .build());
            } catch (IOException e) {
                throw new InvalidUrlException(ErrorCode.INVALID_URL);
            } catch (Exception e) {
                throw new ElementParseException(ErrorCode.PARSE_INVALID_ELEMENT);
            }
        }

        return result;
    }
}
