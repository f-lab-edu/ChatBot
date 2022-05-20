package com.flab.fire_inform.domains.crawling.controller;

import com.flab.fire_inform.domains.crawling.util.JobCrawler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class CrawlerController {

    private final JobCrawler jobCrawler;

    public CrawlerController(JobCrawler jobCrawler) {
        this.jobCrawler = jobCrawler;
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void crawling() {
        jobCrawler.crawling();
    }
}
