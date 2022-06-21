package com.flab.fire_inform.domains.recruit.dto;

import com.flab.fire_inform.domains.recruit.entity.Recruit;
import lombok.Getter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class SimpleTextResponse implements CommonResponse{
    private final String version;
    private final Template template;

    private SimpleTextResponse(String version, Template template) {
        this.version = version;
        this.template = template;
    }

    public static SimpleTextResponse empty() {
        String now = LocalDate.now().getMonthValue() + "월 " + LocalDate.now().getDayOfMonth() + "일";
        List<Output> outputs = new ArrayList<>();
        outputs.add(new Output(new SimpleText(now + " 신규 추가된 채용공고가 존재하지 않습니다.\n신규 채용정보는 매일 오전 8시에 업데이트됩니다.")));

        return new SimpleTextResponse("2.0", new Template(outputs));
    }

    public static SimpleTextResponse of(List<Recruit> recruits) {
        Map<String, Integer> companies = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        String now = LocalDate.now().getMonthValue() + "/" + LocalDate.now().getDayOfMonth();
        sb.append(now);
        sb.append(" 신규 채용 공고\n\n");
        for (Recruit recruit : recruits) {
            String company = recruit.getCompany();
            if(!companies.containsKey(company)) {
                sb.append("\n<");
                sb.append(company);
                sb.append(">\n");
                companies.put(company, 0);
            }

            sb.append("- ");
            sb.append(recruit.getTitle());
            sb.append("\n");

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

            sb.append("\n");
            sb.append(recruit.getLink());
            sb.append("\n");
        }

        List<Output> outputs = new ArrayList<>();
        outputs.add(new Output(new SimpleText(sb.toString())));

        return new SimpleTextResponse("2.0", new Template(outputs));
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
        private final SimpleText simpleText;

        private Output(SimpleText simpleText) {
            this.simpleText = simpleText;
        }
    }

    @Getter
    private static class SimpleText {
        private final String text;

        private SimpleText(String text) {
            this.text = text;
        }
    }
}
