package com.flab.fire_inform.domains.crawling.controller;

import com.flab.fire_inform.domains.crawling.util.JobCrawler;
import com.flab.fire_inform.global.common.SuccessResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CrawlerController {

    private final JobCrawler jobCrawler;

    public CrawlerController(JobCrawler jobCrawler) {
        this.jobCrawler = jobCrawler;
    }

    @PostMapping("/crawling")
    public SuccessResponse<String> crawling() {
        jobCrawler.crawling();

        return SuccessResponse.success("Crawling Done");
    }

}
