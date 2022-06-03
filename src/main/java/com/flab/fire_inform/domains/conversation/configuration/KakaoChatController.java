package com.flab.fire_inform.domains.conversation.configuration;

import com.flab.fire_inform.domains.conversation.dto.Answer;
import com.flab.fire_inform.domains.conversation.dto.OutputContext;
import com.flab.fire_inform.domains.conversation.dto.SkillsResponse;
import com.flab.fire_inform.domains.crawling.service.NewsCrawlling;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class KakaoChatController {
    private final NewsCrawlling newsCrawlling;

    public KakaoChatController(NewsCrawlling newsCrawlling){
        this.newsCrawlling = newsCrawlling;
    }


    @RequestMapping(value = "/api/check/{domain}" , method= {RequestMethod.POST , RequestMethod.GET },headers = {"Accept=application/json"})
    public SkillsResponse checkAPI(@PathVariable(required = false) String domain,
                                   @RequestBody(required = false) Map<String, Object> params) throws IOException {

            Answer answer = Answer.builder().dialog("terminate").sentence("뉴스 크롤링 테스트 입니다.").status("Success").build();
            List<OutputContext> outputContextList = newsCrawlling.getNaverNewsEconomyContents(newsCrawlling.convertURL(domain));


            SkillsResponse skillsResponse = SkillsResponse.builder().answer(answer).outputContext(outputContextList).build();

        return skillsResponse;
    }
}
