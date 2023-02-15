package com.flab.fire_inform.domains.recruit.controller;

import com.flab.fire_inform.domains.recruit.CompanyType;
import com.flab.fire_inform.domains.recruit.CompanyTypeConverter;
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

@RestController
@Slf4j
public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    @PostMapping("/api/new-recruits/{company}")
    public CommonResponse getNewRecruitsAfterYesterdayByListCard(@PathVariable(name = "company") String company) {
        CompanyType companyType = CompanyTypeConverter.from(company);
        List<Recruit> findRecruits = recruitService.getRecruitsByCompany(companyType);
        return parseToChatBotTemplate(findRecruits);
    }

    private CommonResponse parseToChatBotTemplate(List<Recruit> findRecruits) {
        if(findRecruits.size() != 0) {
            return ListCardResponse.of(findRecruits);
        } else {
            return SimpleTextResponse.empty();
        }
    }
}
