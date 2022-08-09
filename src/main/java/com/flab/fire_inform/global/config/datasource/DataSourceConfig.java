package com.flab.fire_inform.global.config.datasource;

import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.flab.fire_inform.global.config.datasource.SetDataSource.*;

@Configuration
@EnableTransactionManagement //이거 같은 경우에는 TransactionAutoConfiguration에 있다. 시작할 때 자동으로 등록된다.
@MapperScan(basePackages = "com.flab.fire_inform.domains.*.mapper")
public class DataSourceConfig {

    @Bean("MASTER")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean("SLAVE")
    @ConfigurationProperties(prefix = "spring.datasource.hikari.slave")
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create().type(HikariDataSource.class).build();
    }

    @Bean
    public DataSource routingDataSource(
            @Qualifier("MASTER") DataSource masterDataSource,
            @Qualifier("SLAVE") DataSource slaveDataSource
        ){
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceType.MASTER, masterDataSource);
        dataSourceMap.put(DataSourceType.SLAVE, slaveDataSource);

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(masterDataSource);

        return routingDataSource;
    }

    // 만약 이 메서드의 이름을 dataSource로 두게 되면 @Primary는 붙여줘야한다.
    @Bean
    public DataSource lazyRoutingDataSource( @Qualifier("routingDataSource") DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }

    @Bean
    public PlatformTransactionManager transactionManager(@Qualifier("lazyRoutingDataSource") DataSource lazyRoutingDataSource) {

        DataSourceTransactionManager transactionManager = new DataSourceTransactionManager();
        transactionManager.setDataSource(lazyRoutingDataSource);

        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory( @Qualifier("lazyRoutingDataSource") DataSource lazyRoutingDataSource)
            throws Exception
    {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factoryBean.setDataSource(lazyRoutingDataSource);
        factoryBean.setConfigLocation(resolver.getResource("mybatis-config.xml"));
        factoryBean.setMapperLocations(resolver.getResources("mappers/*/*.xml"));
        factoryBean.setTypeAliasesPackage("com.flab.fire_inform.domains");

        return factoryBean.getObject();
    }



    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {

        return new SqlSessionTemplate(sqlSessionFactory);

    }
}
