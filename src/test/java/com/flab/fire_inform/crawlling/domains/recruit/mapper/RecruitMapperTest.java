package com.flab.fire_inform.crawlling.domains.recruit.mapper;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RecruitMapperTest {

    @Mock
    RecruitMapper recruitMapper;

    @Test
    public void findAllTest() {
        //given
        Recruit recruit1 = new Recruit.Builder("testTitle1", "testCompany1", "testLink1")
                .address("testAddress1")
                .career("testCareer1")
                .dueDate("testDueDate1")
                .workerType("testWorkerType1")
                .Build();
        Recruit recruit2 = new Recruit.Builder("testTitle2", "testCompany2", "testLink2")
                .address("testAddress2")
                .career("testCareer2")
                .dueDate("testDueDate2")
                .workerType("testWorkerType2")
                .Build();
        List<Recruit> recruits = Arrays.asList(recruit1, recruit2);
        when(recruitMapper.findAll()).thenReturn(recruits);

        //when
        List<Recruit> all = recruitMapper.findAll();

        //then
        Assertions.assertThat(all.size()).isEqualTo(2);
        assertThat(all).contains(recruit1, recruit2);
    }
}