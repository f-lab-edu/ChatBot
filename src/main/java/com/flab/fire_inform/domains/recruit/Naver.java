package com.flab.fire_inform.domains.recruit;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class Naver implements CompanyCondition {

    private final RecruitMapper recruitMapper;

    public Naver(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    @Override
    public Boolean isSatisfiedBy(CompanyType companyType) {
        return companyType == CompanyType.NAVER;
    }

    @Override
    public List<Recruit> getRecruitsByCondition() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        return recruitMapper.findByCompany(CompanyType.NAVER.getValue()).stream()
            .filter(d -> d.getAddDateTime().isAfter(yesterday))
            .collect(Collectors.toList());
    }
}
