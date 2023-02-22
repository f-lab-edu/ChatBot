package com.flab.fire_inform.domains.recruit.mapper;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RecruitMapper {

    Recruit findById(Long id);

    List<Recruit> findByCompany(String company);

    List<Recruit> findAll();

    void save(Recruit recruit);

    void deleteById(Long id);

    void update(Recruit recruit);

    void upsertRecruits(Recruit crawledRecruits);
}
