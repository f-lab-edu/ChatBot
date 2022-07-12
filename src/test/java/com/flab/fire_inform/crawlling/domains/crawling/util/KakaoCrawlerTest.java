package com.flab.fire_inform.crawlling.domains.crawling.util;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@Transactional
class KakaoCrawlerTest {

    @Autowired
    private RecruitMapper recruitMapper;

    @Test
    @Transactional
    public void saveTest() {
        //given
        Recruit recruit = new Recruit.Builder("testTitle", "testCompany", "testLink")
                .address("testAddress")
                .career("testCareer")
                .dueDate("testDueDate")
                .workerType("testWorkerType")
                .Build();

        //when
        recruitMapper.save(recruit);

        //then
    }

    @Test
    public void findByCompanyTest() {
        //given
        Recruit recruit1 = new Recruit.Builder("testTitle1", "testCompany", "testLink1")
                .address("testAddress1")
                .career("testCareer1")
                .dueDate("testDueDate1")
                .workerType("testWorkerType1")
                .Build();
        Recruit recruit2 = new Recruit.Builder("testTitle2", "testCompany", "testLink2")
                .address("testAddress2")
                .career("testCareer2")
                .dueDate("testDueDate2")
                .workerType("testWorkerType2")
                .Build();
        Recruit recruit3 = new Recruit.Builder("testTitle3", "testCompany", "testLink3")
                .address("testAddress3")
                .career("testCareer3")
                .dueDate("testDueDate3")
                .workerType("testWorkerType3")
                .Build();
        recruitMapper.save(recruit1);
        recruitMapper.save(recruit2);
        recruitMapper.save(recruit3);

        //when
        List<Recruit> findCompanies = recruitMapper.findByCompany("testCompany");

        //Then
        assertThat(findCompanies.size()).isEqualTo(3);

    }

    @Test
    public void findByIdTest() {
        //given
        Recruit recruit = new Recruit.Builder("testTitle", "testCompany", "testLink")
                .address("testAddress")
                .career("testCareer")
                .dueDate("testDueDate")
                .workerType("testWorkerType")
                .Build();

        //when
        recruitMapper.save(recruit);
        Recruit findRecruit = recruitMapper.findById(recruit.getId());

        //Then
        assertThat(findRecruit.getId()).isEqualTo(recruit.getId());
    }

    @Test
    public void deleteByIdTest() {
        //given
        Recruit recruit = new Recruit.Builder("testTitle", "testCompany", "testLink")
                .address("testAddress")
                .career("testCareer")
                .dueDate("testDueDate")
                .workerType("testWorkerType")
                .Build();
        //when
        recruitMapper.save(recruit);
        recruitMapper.deleteById(recruit.getId());
        Recruit findRecruit = recruitMapper.findById(recruit.getId());

        //then
        assertThat(findRecruit).isNull();
    }

    @Test
    public void updateTest() {
        //given
        Recruit recruit = new Recruit.Builder("testTitle", "testCompany", "testLink")
                .address("testAddress")
                .career("testCareer")
                .dueDate("testDueDate")
                .workerType("testWorkerType")
                .Build();

        recruitMapper.save(recruit);

        Recruit updateRecruit = new Recruit.Builder("testTitle2", "testCompany2", "testLink2")
                .id(recruit.getId())
                .address("testAddress2")
                .career("testCareer2")
                .dueDate("testDueDate2")
                .workerType("testWorkerType2")
                .Build();
        updateRecruit.setUpdateDateTimeToNow();
        LocalDateTime updateDateTime = updateRecruit.getUpdateDateTime();

        //when
        recruitMapper.update(updateRecruit);

        //then
        Recruit findRecruit = recruitMapper.findById(recruit.getId());

        assertThat(findRecruit.getTitle()).isEqualTo(updateRecruit.getTitle());
        assertThat(findRecruit.getCompany()).isEqualTo(updateRecruit.getCompany());
        assertThat(findRecruit.getLink()).isEqualTo(updateRecruit.getLink());
        assertThat(findRecruit.getAddress()).isEqualTo(updateRecruit.getAddress());
        assertThat(findRecruit.getCareer()).isEqualTo(updateRecruit.getCareer());
        assertThat(findRecruit.getDueDate()).isEqualTo(updateRecruit.getDueDate());
        assertThat(findRecruit.getWorkerType()).isEqualTo(updateRecruit.getWorkerType());
        assertThat(updateDateTime.toString()).isEqualTo(findRecruit.getUpdateDateTime().toString());
    }
}