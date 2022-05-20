package com.flab.fire_inform.domains.recruit.controller;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import com.flab.fire_inform.domains.recruit.service.RecruitService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RecruitController {

    private final RecruitService recruitService;

    public RecruitController(RecruitService recruitService) {
        this.recruitService = recruitService;
    }

    @GetMapping("/new-recruits")
    public ResponseEntity<List<Recruit>> getNewRecruitsAfterYesterday() {
        List<Recruit> findRecruits = recruitService.getNewRecruitsAfterYesterday();
        return new ResponseEntity<>(findRecruits, HttpStatus.OK);
    }
}
