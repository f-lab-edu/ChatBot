package com.flab.fire_inform.domains.recruit;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class Kakao implements CompanyCondition {

    private final RecruitMapper recruitMapper;

    public Kakao(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    @Override
    public Boolean isSatisfiedBy(CompanyType companyType) {
        return companyType == CompanyType.KAKAO;
    }

    @Override
    public List<Recruit> getRecruitsByCondition() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1000);

        List<Recruit> find = recruitMapper.findByCompany(CompanyType.KAKAO.getValue());
        return find.stream()
            .filter(d -> d.getAddDateTime().isAfter(yesterday))
            .collect(Collectors.toList());
    }
}
