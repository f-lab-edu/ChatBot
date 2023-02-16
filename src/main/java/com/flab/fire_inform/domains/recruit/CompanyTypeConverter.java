package com.flab.fire_inform.domains.recruit;

import java.security.InvalidParameterException;

public class CompanyTypeConverter {
    public static CompanyType from(String company) {
        switch (company) {
            case "all":
                return CompanyType.ALL;
            case "kakao":
                return CompanyType.KAKAO;
            case "naver":
                return CompanyType.NAVER;
            default:
                throw new InvalidParameterException("Invalid Company Type");
        }
    }
}
