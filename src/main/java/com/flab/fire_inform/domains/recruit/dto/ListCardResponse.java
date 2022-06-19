package com.flab.fire_inform.domains.recruit.dto;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
public class ListCardResponse {
    private final String version;
    private final Template template;

    private ListCardResponse(String version, Template template) {
        this.version = version;
        this.template = template;
    }

    public static ListCardResponse of(List<Recruit> recruits) {
        List<Item> items = new ArrayList<>();
        for (Recruit recruit : recruits) {
            StringBuilder sb = new StringBuilder();
            sb.append(recruit.getCompany());

            if(recruit.getCareer() != null) {
                sb.append("/").append(recruit.getCareer());
            }

            if(recruit.getWorkerType() != null) {
                sb.append("/").append(recruit.getWorkerType());
            }

            if(recruit.getDueDate() != null) {
                sb.append("/").append(recruit.getDueDate());
            }

            if(recruit.getAddress() != null) {
                sb.append("/").append(recruit.getAddress());
            }

            Item item = new Item(recruit.getTitle(), sb.toString(), new Link(recruit.getLink()));
            items.add(item);
        }

        List<Output> outputs = new ArrayList<>();
        String now = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth();
        outputs.add(new Output(new ListCard(new Header(now + " 신규 채용 공고"), items)));

        return new ListCardResponse("2.0", new Template(outputs));
    }

    @Getter
    private static class Template{
        private final List<Output> outputs;

        private Template(List<Output> outputs) {
            this.outputs = outputs;
        }
    }

    @Getter
    private static class Output{
        private final ListCard listCard;

        private Output(ListCard listCard) {
            this.listCard = listCard;
        }
    }

    @Getter
    private static class ListCard {
        private final Header header;
        private final List<Item> items;

        private ListCard(Header header, List<Item> items) {
            this.header = header;
            this.items = items;
        }
    }

    @Getter
    private static class Header {
        private final String title;

        private Header(String title) {
            this.title = title;
        }
    }

    @Getter
    private static class Item {
        private final String title;
        private final String description;
        private final Link link;

        private Item(String title, String description, Link link) {
            this.title = title;
            this.description = description;
            this.link = link;
        }
    }

    @Getter
    private static class Link {
        private final String web;

        private Link(String web) {
            this.web = web;
        }
    }
}
