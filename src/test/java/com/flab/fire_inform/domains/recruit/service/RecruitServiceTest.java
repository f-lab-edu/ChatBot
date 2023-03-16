package com.flab.fire_inform.domains.recruit.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.flab.fire_inform.domains.recruit.CompanyCondition;
import com.flab.fire_inform.domains.recruit.CompanyType;
import com.flab.fire_inform.domains.recruit.EntireCompany;
import com.flab.fire_inform.domains.recruit.Kakao;
import com.flab.fire_inform.domains.recruit.Naver;
import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import com.flab.fire_inform.domains.recruit.service.RecruitService;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class RecruitServiceTest {

    @Spy
    @InjectMocks
    RecruitService recruitService;

    @Spy
    List<CompanyCondition> companyConditions;

    @Mock
    Kakao kakao;

    @Mock
    Naver naver;

    @Mock
    EntireCompany entireCompany;

    @Mock
    RecruitMapper recruitMapper;

    @BeforeEach
    public void setUp() {
//        MockitoAnnotations.openMocks(this);
        when(entireCompany.isSatisfiedBy(any(CompanyType.class))).thenReturn(false);
        companyConditions.add(entireCompany);
        when(kakao.isSatisfiedBy(any(CompanyType.class))).thenReturn(true);
        companyConditions.add(kakao);
        when(naver.isSatisfiedBy(any(CompanyType.class))).thenReturn(false);
        companyConditions.add(naver);
    }

    @Test
    public void getRecruitsByCompanySuccessTest() {
        //given
        Recruit recruit1 = new Recruit.Builder("testTitle1", "testCompany1", "testLink1")
            .addDateTime(LocalDateTime.now().minusHours(1))
            .build();
        Recruit recruit2 = new Recruit.Builder("testTitle2", "testCompany2", "testLink2")
            .addDateTime(LocalDateTime.now().minusHours(1))
            .build();
        Recruit recruit3 = new Recruit.Builder("testTitle3", "testCompany3", "testLink3")
            .addDateTime(LocalDateTime.now().minusDays(7))
            .build();
        when(recruitMapper.findByCompany(any(String.class))).thenReturn(
            Arrays.asList(recruit1, recruit2, recruit3));
//        when(entireCompany.isSatisfiedBy(any(CompanyType.class))).thenReturn(false);
//        when(kakao.isSatisfiedBy(any(CompanyType.class))).thenReturn(true);
//        when(naver.isSatisfiedBy(any(CompanyType.class))).thenReturn(false);
        when(kakao.getRecruitsByCondition()).thenReturn(
            Arrays.asList(recruit1, recruit2, recruit3));

        //when
        CompanyType testCompanyType = CompanyType.KAKAO;
        List<Recruit> find = recruitService.getRecruitsByCompany(testCompanyType);

        //then
        verify(entireCompany.isSatisfiedBy(testCompanyType), times(1));
        verify(naver.isSatisfiedBy(testCompanyType), times(1));
        verify(kakao.isSatisfiedBy(testCompanyType), times(1));

        verify(entireCompany.getRecruitsByCondition(), times(0));
        verify(naver.getRecruitsByCondition(), times(0));
        verify(kakao.getRecruitsByCondition(), times(1));

        assertThat(find.size()).isEqualTo(2);
        assertThat(find).contains(recruit1, recruit2);
    }
}