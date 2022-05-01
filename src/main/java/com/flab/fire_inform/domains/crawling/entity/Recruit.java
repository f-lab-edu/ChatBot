package com.flab.fire_inform.domains.crawling.entity;

import lombok.Getter;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;

@Getter
@Alias("recruit")
public class Recruit {
    private final Long id;
    private final String title;
    private final String career;
    private final String dueDate;
    private final String company;
    private final String address;
    private final String workerType;
    private final String link;
    private final LocalDateTime addDateTime;
    private final LocalDateTime updateDateTime;

    private Recruit(Builder builder) {
        id = builder.id;
        title = builder.title;
        career = builder.career;
        dueDate = builder.dueDate;
        company = builder.company;
        address = builder.address;
        workerType = builder.workerType;
        link = builder.link;
        addDateTime = builder.addDateTime;
        updateDateTime = builder.updateDateTime;
    }

    public static class Builder {
        // 필수 매개변수
        private final String title;
        private final String company;
        private final String link;

        // 선택 매개변수
        private Long id = 0L;
        private String career = null;
        private String dueDate = null;
        private String address = null;
        private String workerType = null;
        private LocalDateTime addDateTime = null;
        private LocalDateTime updateDateTime = null;

        public Builder(String title, String company, String link) {
            this.title = title;
            this.company = company;
            this.link = link;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder career(String career) {
            this.career = career;
            return this;
        }

        public Builder dueDate(String dueDate) {
            this.dueDate = dueDate;
            return this;
        }

        public Builder address(String address) {
            this.address = address;
            return this;
        }

        public Builder workerType(String workerType) {
            this.workerType = workerType;
            return this;
        }

        public Builder addDateTime(LocalDateTime addDateTime) {
            this.addDateTime = addDateTime;
            return this;
        }

        public Builder updateDateTime(LocalDateTime updateDateTime) {
            this.updateDateTime = updateDateTime;
            return this;
        }

        public Recruit Build() {
            return new Recruit(this);
        }
    }
}
