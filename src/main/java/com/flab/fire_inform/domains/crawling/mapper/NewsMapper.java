package com.flab.fire_inform.domains.crawling.mapper;

import com.flab.fire_inform.domains.crawling.dto.entity.News;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NewsMapper {

    List<News> getNewsList(@Param("site") String site, @Param("domain") String domain);

    int insertNewsList(News news);

    int deleteNewsList(@Param("site") String site, @Param("domain") String domain);

}
