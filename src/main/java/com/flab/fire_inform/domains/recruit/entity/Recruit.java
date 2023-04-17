package com.flab.fire_inform.domains.recruit.entity;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

@Getter
@Alias("recruit")
@ToString
public class Recruit {

    private Long id;
    private String title;
    private String career;
    private String dueDate;
    private String company;
    private String address;
    private String workerType;
    private String link;
    private LocalDateTime addDateTime;
    private LocalDateTime updateDateTime;

    public Recruit() {
    }

    protected Recruit(Long id, String title, String career, String dueDate, String company,
        String address, String workerType, String link, LocalDateTime addDateTime,
        LocalDateTime updateDateTime) {
        this.id = id;
        this.title = title;
        this.career = career;
        this.dueDate = dueDate;
        this.company = company;
        this.address = address;
        this.workerType = workerType;
        this.link = link;
        this.addDateTime = addDateTime;
        this.updateDateTime = updateDateTime;
    }

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

    public void setId(Long id) {
        this.id = id;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Recruit;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getCompany(), getLink());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Recruit)) {
            return false;
        }
        Recruit recruit = (Recruit) o;
        return getTitle().equals(recruit.getTitle()) && getCompany().equals(recruit.getCompany())
            && getLink().equals(recruit.getLink());
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

        public Recruit build() {
            return new Recruit(this);
        }
    }
}
