package com.flab.fire_inform.domains.recruit.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flab.fire_inform.domains.recruit.dto.CommonResponse;
import com.flab.fire_inform.domains.recruit.dto.ListCardResponse;
import com.flab.fire_inform.domains.recruit.dto.SimpleTextResponse;
import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.service.RecruitService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class RecruitController {

    private final RecruitService recruitService;
    private final ObjectMapper objectMapper;

    public RecruitController(RecruitService recruitService, ObjectMapper objectMapper) {
        this.recruitService = recruitService;
        this.objectMapper = objectMapper;
    }

    @PostMapping("/new-recruits")
    public CommonResponse getNewRecruitsAfterYesterdayByListCard() {
        List<Recruit> findRecruits = recruitService.getNewRecruitsAfterYesterday();
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
