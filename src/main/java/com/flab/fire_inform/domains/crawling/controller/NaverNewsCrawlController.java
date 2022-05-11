package com.flab.fire_inform.domains.crawling.controller;

import com.flab.fire_inform.domains.crawling.service.NewsCrawlling;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
public class NaverNewsCrawlController {

    private final NewsCrawlling newsCrawlling;

    public NaverNewsCrawlController(NewsCrawlling newsCrawlling){
        this.newsCrawlling = newsCrawlling;
    }

    /**
     * {host}:port/newsList/{DOMAIN}
     * @param domain
     * @return
     * @throws IOException
     */
    @GetMapping(value={"/newsList/{domain}","/newsList"})
    public ResponseEntity getNewsList(@PathVariable(required = false) String domain) throws IOException {
        if(domain == null){
            throw new CustomException(ErrorCode.DOMAIN_NOT_FOUND);
        }
        newsCrawlling.setUrl(domain);
        return new ResponseEntity(newsCrawlling.getNaverNewscontents(),HttpStatus.OK);
    }
}
