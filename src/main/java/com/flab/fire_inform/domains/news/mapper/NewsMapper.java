package com.flab.fire_inform.domains.news.mapper;

import com.flab.fire_inform.domains.crawling.dto.entity.News;
import com.flab.fire_inform.global.config.datasource.SetDataSource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

import static com.flab.fire_inform.global.config.datasource.SetDataSource.*;

@Mapper
public interface NewsMapper {
    @SetDataSource(dataSourceType = DataSourceType.SLAVE)
    List<News> getNewsList(@Param("site") String site, @Param("domain") String domain);

    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    int insertNewsList(News news);
    
    @SetDataSource(dataSourceType = DataSourceType.MASTER)
    int deleteNewsList(@Param("site") String site, @Param("domain") String domain);

}
