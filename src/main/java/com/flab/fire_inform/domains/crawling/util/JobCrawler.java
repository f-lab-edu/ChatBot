package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.entity.Recruit;

import java.util.List;

public interface JobCrawler {

    public List<Recruit> crawling();
}
