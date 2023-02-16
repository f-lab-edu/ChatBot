package com.flab.fire_inform.domains.recruit;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntireCompany implements CompanyCondition {

    private final RecruitMapper recruitMapper;

    public EntireCompany(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    @Override
    public Boolean isSatisfiedBy(CompanyType companyType) {
        return companyType == CompanyType.ALL;
    }

    @Override
    public List<Recruit> getRecruitsByCondition() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        return recruitMapper.findAll().stream()
                .filter(d -> d.getAddDateTime().isAfter(yesterday))
                .collect(Collectors.toList());
    }
}
