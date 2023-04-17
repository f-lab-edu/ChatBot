package com.flab.fire_inform.domains.recruit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.flab.fire_inform.domains.recruit.CompanyCondition;
import com.flab.fire_inform.domains.recruit.CompanyType;
import com.flab.fire_inform.domains.recruit.EntireCompany;
import com.flab.fire_inform.domains.recruit.Kakao;
import com.flab.fire_inform.domains.recruit.Naver;
import com.flab.fire_inform.domains.recruit.entity.Recruit;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class RecruitServiceTest {

    @Autowired
    RecruitService recruitService;

    @Autowired
    List<CompanyCondition> companyConditions;

    @MockBean
    Kakao kakao;

    @MockBean
    Naver naver;

    @MockBean
    EntireCompany entireCompany;

    @Test
    public void returnRecruitsSearchByKAKAO() {
        //given
        Recruit recruit1 = new Recruit.Builder("testTitle1", "testCompany1", "testLink1")
            .addDateTime(LocalDateTime.now().minusHours(1))
            .build();
        Recruit recruit2 = new Recruit.Builder("testTitle2", "testCompany2", "testLink2")
            .addDateTime(LocalDateTime.now().minusHours(1))
            .build();
        when(entireCompany.isSatisfiedBy(any(CompanyType.class))).thenReturn(false);
        when(kakao.isSatisfiedBy(any(CompanyType.class))).thenReturn(true);
        when(naver.isSatisfiedBy(any(CompanyType.class))).thenReturn(false);
        when(kakao.getRecruitsByCondition()).thenReturn(
            Arrays.asList(recruit1, recruit2));

        //when
        CompanyType testCompanyType = CompanyType.KAKAO;
        List<Recruit> find = recruitService.getRecruitsByCompany(testCompanyType);

        //then
        assertThat(find.size()).isEqualTo(2);
        assertThat(find).contains(recruit1, recruit2);
    }

    @Test
    public void containsAllWhenIterate() {
        //given

        //when

        //then
        assertThat(companyConditions.size()).isEqualTo(3);
    }
}