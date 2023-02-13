package com.flab.fire_inform.domains.recruit;

import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;

import java.security.InvalidParameterException;

public class CompanyFactory {
    public static Company getCompany(String company) {
        if (company.equals(CompanyType.ALL.getValue())) {
            return new EntireCompany(CompanyType.ALL);
        } else if (company.equals(CompanyType.KAKAO.getValue())) {
            return new Kakao(CompanyType.KAKAO);
        } else if (company.equals(CompanyType.NAVER.getValue())) {
            return new Naver(CompanyType.NAVER);
        } else {
            throw new InvalidParameterException("Invalid Company Type");
        }
    }
}
