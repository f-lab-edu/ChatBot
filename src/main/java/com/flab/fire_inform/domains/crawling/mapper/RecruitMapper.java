package com.flab.fire_inform.domains.crawling.mapper;

import com.flab.fire_inform.domains.crawling.entity.Recruit;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecruitMapper {

    List<Recruit> findByCompany(String company);

    void save(Recruit recruit);
}
