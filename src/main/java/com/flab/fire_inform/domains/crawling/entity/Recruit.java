package com.flab.fire_inform.domains.crawling.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Recruit {
    private Long id;
    private String title;
    private String company;
    private String career;
    private String dueDate;
    private String address;
    private String workerType;
    private String link;
    private LocalDateTime addDateTime;
    private LocalDateTime updateDateTime;
}
