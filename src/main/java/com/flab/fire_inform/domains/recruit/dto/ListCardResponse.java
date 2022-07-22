package com.flab.fire_inform.domains.recruit.dto;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@Slf4j
public class ListCardResponse implements CommonResponse {
    private final String version;
    private final Template template;
    private static final Map<String, String> logoUrlMap = Map.of("카카오", "https://t1.kakaocdn.net/kakaocorp/corp_thumbnail/Kakao.png", "네이버", "https://blog.kakaocdn.net/dn/XEAGa/btqB10c0bQ1/RZk86MYViwrP4OaBdpfhn0/img.png");

    private ListCardResponse(String version, Template template) {
        this.version = version;
        this.template = template;
    }

    public static ListCardResponse of(List<Recruit> recruits) {
        String now = LocalDate.now().getMonthValue() + "월 " + LocalDate.now().getDayOfMonth() + "일";
        List<Output> outputs = new ArrayList<>();
        int totalCount = recruits.size();
        int totalPage = totalCount % 5 == 0 ? totalCount / 5 : totalCount / 5 + 1;
        int currentPage = 1;
        int currentIndex = 0;
        log.info("totalCount: {}", totalCount);
        log.info("totalPage: {}", totalPage);

        while(currentIndex < totalCount) {
            log.info("currentIndex: {}", currentIndex);
            log.info("totalCount: {}", totalCount);
            List<Item> items = new ArrayList<>();
            for(int i = currentIndex; i < totalCount; i++) {
                Recruit recruit = recruits.get(i);
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

                Item item = new Item(recruit.getTitle(), sb.toString(), logoUrlMap.get(recruit.getCompany()), new Link(recruit.getLink()));
                items.add(item);
                if(i == totalCount - 1 || i - currentIndex == 4) {
                    outputs.add(new Output(new ListCard(new Header(now + " 신규 채용 공고 (" + currentPage++ + "/" + totalPage + ")"), items)));
                    currentIndex = i + 1;
                    break;
                }
            }
        }

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
        private final String imageUrl;
        private final Link link;

        public Item(String title, String description, String imageUrl, Link link) {
            this.title = title;
            this.description = description;
            this.imageUrl = imageUrl;
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
