package com.flab.fire_inform.domains.recruit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fire_inform.domains.recruit.dto.CommonResponse;
import com.flab.fire_inform.domains.recruit.dto.ListCardResponse;
import com.flab.fire_inform.domains.recruit.dto.SimpleTextResponse;
import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.service.RecruitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class RecruitController {

    private final RecruitService recruitService;
    private final ObjectMapper objectMapper;
    private final Map<String, String> companies = Map.of("kakao", "카카오", "naver", "네이버");

    public RecruitController(RecruitService recruitService, ObjectMapper objectMapper) {
        this.recruitService = recruitService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/api/new-recruits/{company}")
    public CommonResponse getNewRecruitsAfterYesterdayByListCard(@PathVariable(name = "company") String company) {
        List<Recruit> findRecruits;
        if(company.equals("all")) {
            findRecruits = recruitService.getAllRecruitsAfterYesterday();
        } else {
            findRecruits = recruitService.getRecruitsByCompanyAfterYesterday(companies.get(company));
        }

        CommonResponse result;
        if(findRecruits.size() != 0) {
            result = ListCardResponse.of(findRecruits);
        } else {
            result = SimpleTextResponse.empty();
        }

        try {
            String resultJson = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
            log.info(resultJson);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return result;
    }
}
