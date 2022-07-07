package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class KakaoRecruitCrawlerTest {

    @Autowired
    private RecruitMapper recruitMapper;

    @Test
    @Transactional
    public void crawling() {
        //given

        //when

        //then
    }
}