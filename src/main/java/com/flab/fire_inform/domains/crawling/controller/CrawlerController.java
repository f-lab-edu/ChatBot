package com.flab.fire_inform.domains.crawling.controller;

import com.flab.fire_inform.domains.news.service.NewsCrawlling;
import com.flab.fire_inform.domains.crawling.util.JobCrawler;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@Slf4j
public class CrawlerController {

    private final List<JobCrawler> jobCrawlers;
    private final NewsCrawlling newsCrawlling;

    public CrawlerController(List<JobCrawler> jobCrawlers, NewsCrawlling newsCrawlling) {
        this.jobCrawlers = jobCrawlers;
        this.newsCrawlling = newsCrawlling;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void crawling() {
        for (JobCrawler jobCrawler : jobCrawlers) {
            jobCrawler.crawling();
        }
    }

    @PostMapping(value={"/api/check/{domain}"})
    public ResponseEntity getNewsList(@PathVariable(required = false) String domain) throws IOException {

        if(domain == null){
            throw new CustomException(ErrorCode.DOMAIN_NOT_FOUND);
        }

        //도메인 등록
        return new ResponseEntity(newsCrawlling.getNewsContents(domain), HttpStatus.OK);
    }
}
