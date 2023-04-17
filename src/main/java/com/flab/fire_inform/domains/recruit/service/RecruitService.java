package com.flab.fire_inform.domains.recruit.service;

import com.flab.fire_inform.domains.recruit.CompanyCondition;
import com.flab.fire_inform.domains.recruit.CompanyType;
import com.flab.fire_inform.domains.recruit.entity.Recruit;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RecruitService {

    private final List<CompanyCondition> companyConditions;

    public RecruitService(List<CompanyCondition> companyConditions) {
        this.companyConditions = companyConditions;
    }

    public List<Recruit> getRecruitsByCompany(CompanyType companyType) {
        return companyConditions.stream()
            .filter(companyCondition -> companyCondition.isSatisfiedBy(companyType))
            .findFirst().get().getRecruitsByCondition();
    }
}
