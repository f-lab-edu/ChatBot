package com.flab.fire_inform.domains.recruit.entity;

import lombok.Getter;
import lombok.ToString;
import org.apache.ibatis.type.Alias;

import java.time.LocalDateTime;
import java.util.Objects;

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

    public Recruit() {}

    protected Recruit(Long id, String title, String career, String dueDate, String company, String address, String workerType, String link, LocalDateTime addDateTime, LocalDateTime updateDateTime) {
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

    public boolean equals(final Object o) {
        if (o == this) return true;
        if (!(o instanceof Recruit)) return false;
        final Recruit other = (Recruit) o;
        if (!other.canEqual(this)) return false;
        final Object this$id = this.getId();
        final Object other$id = other.getId();
        if (!Objects.equals(this$id, other$id)) return false;
        final Object this$title = this.getTitle();
        final Object other$title = other.getTitle();
        if (!Objects.equals(this$title, other$title)) return false;
        final Object this$career = this.getCareer();
        final Object other$career = other.getCareer();
        if (!Objects.equals(this$career, other$career)) return false;
        final Object this$dueDate = this.getDueDate();
        final Object other$dueDate = other.getDueDate();
        if (!Objects.equals(this$dueDate, other$dueDate)) return false;
        final Object this$company = this.getCompany();
        final Object other$company = other.getCompany();
        if (!Objects.equals(this$company, other$company)) return false;
        final Object this$address = this.getAddress();
        final Object other$address = other.getAddress();
        if (!Objects.equals(this$address, other$address)) return false;
        final Object this$workerType = this.getWorkerType();
        final Object other$workerType = other.getWorkerType();
        if (!Objects.equals(this$workerType, other$workerType))
            return false;
        final Object this$link = this.getLink();
        final Object other$link = other.getLink();
        return Objects.equals(this$link, other$link);
    }

    protected boolean canEqual(final Object other) {
        return other instanceof Recruit;
    }

    public int hashCode() {
        final int PRIME = 59;
        int result = 1;
        final Object $id = this.getId();
        result = result * PRIME + ($id == null ? 43 : $id.hashCode());
        final Object $title = this.getTitle();
        result = result * PRIME + ($title == null ? 43 : $title.hashCode());
        final Object $career = this.getCareer();
        result = result * PRIME + ($career == null ? 43 : $career.hashCode());
        final Object $dueDate = this.getDueDate();
        result = result * PRIME + ($dueDate == null ? 43 : $dueDate.hashCode());
        final Object $company = this.getCompany();
        result = result * PRIME + ($company == null ? 43 : $company.hashCode());
        final Object $address = this.getAddress();
        result = result * PRIME + ($address == null ? 43 : $address.hashCode());
        final Object $workerType = this.getWorkerType();
        result = result * PRIME + ($workerType == null ? 43 : $workerType.hashCode());
        final Object $link = this.getLink();
        result = result * PRIME + ($link == null ? 43 : $link.hashCode());
        final Object $addDateTime = this.getAddDateTime();
        result = result * PRIME + ($addDateTime == null ? 43 : $addDateTime.hashCode());
        final Object $updateDateTime = this.getUpdateDateTime();
        result = result * PRIME + ($updateDateTime == null ? 43 : $updateDateTime.hashCode());
        return result;
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
