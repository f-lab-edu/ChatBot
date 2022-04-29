package com.flab.fire_inform.domains.crawling.controller;

import com.flab.fire_inform.domains.crawling.entity.Recruit;
import com.flab.fire_inform.domains.crawling.util.JobCrawler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CrawlerController {

    private final JobCrawler jobCrawler;

    public CrawlerController(JobCrawler jobCrawler) {
        this.jobCrawler = jobCrawler;
    }

    @PostMapping("/crawling")
    public String crawling() {
        List<Recruit> recruits = jobCrawler.crawling();

        return "ok";
    }

}
