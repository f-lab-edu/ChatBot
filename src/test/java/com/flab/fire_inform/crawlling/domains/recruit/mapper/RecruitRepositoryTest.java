
package com.flab.fire_inform.crawlling.domains.recruit.mapper;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class RecruitRepositoryTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RecruitMapper recruitMapper;

    @Test
    @Transactional
    public void findAllTest() {
        //given
        Recruit recruit1 = new Recruit.Builder("testTitle1", "testCompany1", "testLink1")
                .address("testAddress1")
                .career("testCareer1")
                .dueDate("testDueDate1")
                .workerType("testWorkerType1")
                .build();
        Recruit recruit2 = new Recruit.Builder("testTitle2", "testCompany2", "testLink2")
                .address("testAddress2")
                .career("testCareer2")
                .dueDate("testDueDate2")
                .workerType("testWorkerType2")
                .build();

        //when
        recruitMapper.save(recruit1);
        recruitMapper.save(recruit2);
        List<Recruit> all = recruitMapper.findAll();
        Recruit findRecruit1 = recruitMapper.findById(recruit1.getId());
        Recruit findRecruit2 = recruitMapper.findById(recruit2.getId());

        //then
        assertThat(all).contains(findRecruit1, findRecruit2);
    }

    @Test
    @Transactional
    public void findByCompanyTest() {
        //given
        Recruit recruit1 = new Recruit.Builder("testTitle1", "testCompany", "testLink1")
                .address("testAddress1")
                .career("testCareer1")
                .dueDate("testDueDate1")
                .workerType("testWorkerType1")
                .build();
        Recruit recruit2 = new Recruit.Builder("testTitle2", "testCompany", "testLink2")
                .address("testAddress2")
                .career("testCareer2")
                .dueDate("testDueDate2")
                .workerType("testWorkerType2")
                .build();
        Recruit recruit3 = new Recruit.Builder("testTitle3", "testCompany", "testLink3")
                .address("testAddress3")
                .career("testCareer3")
                .dueDate("testDueDate3")
                .workerType("testWorkerType3")
                .build();

        //when
        recruitMapper.save(recruit1);
        recruitMapper.save(recruit2);
        recruitMapper.save(recruit3);
        List<Recruit> findCompanies = recruitMapper.findByCompany("testCompany");

        //Then
        assertThat(findCompanies.size()).isEqualTo(3);
    }

    @Test
    @Transactional
    public void findByIdTest() {
        //given
        Recruit recruit = new Recruit.Builder("testTitle", "testCompany", "testLink")
                .address("testAddress")
                .career("testCareer")
                .dueDate("testDueDate")
                .workerType("testWorkerType")
                .build();

        //when
        recruitMapper.save(recruit);
        Recruit findRecruit = recruitMapper.findById(recruit.getId());

        //Then
        assertThat(findRecruit).isEqualTo(recruit);
    }

    @Test
    @Transactional
    public void saveTest() {
        //given
        Recruit recruit = new Recruit.Builder("testTitle", "testCompany", "testLink")
                .address("testAddress")
                .career("testCareer")
                .dueDate("testDueDate")
                .workerType("testWorkerType")
                .build();

        //when
        recruitMapper.save(recruit);
        Recruit findRecruit = recruitMapper.findById(recruit.getId());

        //then
        assertThat(findRecruit.getId()).isEqualTo(recruit.getId());
    }

    @Test
    @Transactional
    public void updateTest() {
        //given
        Recruit recruit = new Recruit.Builder("testTitle", "testCompany", "testLink")
                .address("testAddress")
                .career("testCareer")
                .dueDate("testDueDate")
                .workerType("testWorkerType")
                .build();
        recruitMapper.save(recruit);

        Recruit updateRecruit = new Recruit.Builder("testTitle2", "testCompany2", "testLink2")
                .id(recruit.getId())
                .address("testAddress2")
                .career("testCareer2")
                .dueDate("testDueDate2")
                .workerType("testWorkerType2")
                .build();

        //when
        recruitMapper.update(updateRecruit);

        //then
        Recruit findRecruit = recruitMapper.findById(recruit.getId());
        assertThat(findRecruit).isEqualTo(updateRecruit);
    }

    @Test
    @Transactional
    public void upsertTestWhenFirstRecruit(){
        //given
        Recruit recruit = new Recruit.Builder("testTitle1", "testCompany1", "testLink1")
                .address("testAddress1")
                .career("testCareer1")
                .dueDate("testDueDate1")
                .workerType("testWorkerType1")
                .build();

        // when
        recruitMapper.upsertRecruits(recruit);
        Recruit findRecruit = recruitMapper.findById(recruit.getId());

        // then
        assertThat(findRecruit.getLink()).isEqualTo(recruit.getLink());
    }

    @Test
    @Transactional
    public void upsertTestWhenDuplicateRecruit(){
        //given
        Recruit recruit1 = new Recruit.Builder("testTitle1", "testCompany1", "testLink1")
                .address("testAddress1")
                .career("testCareer1")
                .dueDate("testDueDate1")
                .workerType("testWorkerType1")
                .build();

        Recruit recruit2 = new Recruit.Builder("testTitle2", "testCompany2", "testLink1")
                .address("testAddress2")
                .career("testCareer2")
                .dueDate("testDueDate2")
                .workerType("testWorkerType2")
                .build();

        //when
        recruitMapper.upsertRecruits(recruit1);  // insert
        recruitMapper.upsertRecruits(recruit2);  // update
        Recruit findRecruit = recruitMapper.findById(recruit1.getId());
        Assertions.assertThat(findRecruit.getLink()).isEqualTo(recruit2.getLink());
    }

    @Test
    @Transactional
    public void deleteByIdTest() {
        //given
        Recruit recruit = new Recruit.Builder("testTitle", "testCompany", "testLink")
                .address("testAddress")
                .career("testCareer")
                .dueDate("testDueDate")
                .workerType("testWorkerType")
                .build();
        //when
        recruitMapper.save(recruit);
        recruitMapper.deleteById(recruit.getId());
        Recruit findRecruit = recruitMapper.findById(recruit.getId());

        //then
        assertThat(findRecruit).isNull();
    }
}