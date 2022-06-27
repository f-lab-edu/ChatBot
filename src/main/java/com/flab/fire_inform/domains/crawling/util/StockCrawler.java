package com.flab.fire_inform.domains.crawling.util;

import com.flab.fire_inform.domains.crawling.dto.StockInformation;

import java.io.IOException;
import java.util.Map;

public interface StockCrawler {
     StockInformation crawling(Map<String,Object> params) throws IOException;
}
