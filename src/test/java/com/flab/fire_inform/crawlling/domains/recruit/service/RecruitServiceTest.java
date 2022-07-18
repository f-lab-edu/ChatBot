package com.flab.fire_inform.crawlling.domains.recruit.service;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import com.flab.fire_inform.domains.recruit.service.RecruitService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecruitServiceTest {

    @Mock
    RecruitMapper recruitMapper;

    @InjectMocks
    RecruitService recruitService;

    @Test
    public void getNewRecruitsAfterYesterdayTest() {
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
        when(recruitService.getAllRecruitsAfterYesterday()).thenReturn(Arrays.asList(recruit1, recruit2));

        //when
        List<Recruit> newRecruitsAfterYesterday = recruitService.getAllRecruitsAfterYesterday();

        //then
        assertThat(newRecruitsAfterYesterday.size()).isEqualTo(2);
        assertThat(newRecruitsAfterYesterday).contains(recruit1, recruit2);
    }
}