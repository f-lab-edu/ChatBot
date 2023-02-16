package com.flab.fire_inform.domains.recruit;

public enum CompanyType {
    ALL("all"),
    KAKAO("kakao"),
    NAVER("naver");

    private final String value;

    CompanyType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
