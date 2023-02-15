package com.flab.fire_inform.domains.recruit;

import com.flab.fire_inform.domains.recruit.entity.Recruit;

import java.util.List;

public interface CompanyCondition {

    Boolean isSatisfiedBy(CompanyType companyType);

    List<Recruit> getRecruitsByCondition();
}
