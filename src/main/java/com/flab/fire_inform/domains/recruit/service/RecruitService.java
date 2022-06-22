package com.flab.fire_inform.domains.recruit.service;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.mapper.RecruitMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecruitService {

    private final RecruitMapper recruitMapper;

    public RecruitService(RecruitMapper recruitMapper) {
        this.recruitMapper = recruitMapper;
    }

    public List<Recruit> getAllRecruitsAfterYesterday() {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        return recruitMapper.findAll().stream()
                .filter(d -> d.getAddDateTime().isAfter(yesterday))
                .collect(Collectors.toList());
    }

    public List<Recruit> getRecruitsByCompanyAfterYesterday(String company) {
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);

        return recruitMapper.findByCompany(company).stream()
                .filter(d -> d.getAddDateTime().isAfter(yesterday))
                .collect(Collectors.toList());

    }
}
