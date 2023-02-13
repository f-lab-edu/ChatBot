package com.flab.fire_inform.domains.recruit;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

public class Kakao implements Company {

    private final CompanyType companyType;

    public Kakao(CompanyType companyType) {
        this.companyType = companyType;
    }

    @Override
    public CompanyType getCompanyType() {
        return companyType;
    }
}
