package com.flab.fire_inform.domains.crawling.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fire_inform.domains.crawling.service.NewsCrawlling;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
public class NaverNewsCrawlController {

    private final NewsCrawlling newsCrawlling;

    public NaverNewsCrawlController(NewsCrawlling newsCrawlling){
        this.newsCrawlling = newsCrawlling;
    }

    // {host}:port/newsList/{DOMAIN}
    @PostMapping(value={"/api/news/{domain}"})
    public ResponseEntity getNewsList(@PathVariable(required = false) String domain) throws IOException {

        if(domain == null){
            throw new CustomException(ErrorCode.DOMAIN_NOT_FOUND);
        }
        //도메인 등록
        return new ResponseEntity(newsCrawlling.getNaverNewsContents(newsCrawlling.convertURL(domain)),HttpStatus.OK);
    }


}
