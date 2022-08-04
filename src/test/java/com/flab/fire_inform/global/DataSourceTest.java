package com.flab.fire_inform.global;


import com.flab.fire_inform.global.config.datasource.DynamicRoutingDataSource;
import com.flab.fire_inform.global.config.datasource.SetDataSource;
import com.zaxxer.hikari.HikariDataSource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static com.flab.fire_inform.global.config.datasource.SetDataSource.*;
import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class DataSourceTest {

    @Autowired
    private Environment environment;

    private static final String Test_Method_Name = "determineCurrentLookupKey";

    @Test
    @DisplayName("마스터 데이터 소스 테스트")
    void masterDataSourceTest(@Qualifier("MASTER") final DataSource masterDataSource) {
        // given
        String url = environment.getProperty("spring.datasource.hikari.master.jdbc-url");
        String username = environment.getProperty("spring.datasource.hikari.master.username");
        String driverClassName = environment.getProperty("spring.datasource.hikari.master.driver-class-name");
        Boolean readOnly = Boolean.valueOf(environment.getProperty("spring.datasource.hikari.master.read-only"));

        // when
        HikariDataSource hikariDataSource = (HikariDataSource) masterDataSource;

        // then
        verifyOf(readOnly, url, username, driverClassName, hikariDataSource);
    }

    @Test
    @DisplayName("레플리케이션 데이터 소스 테스트")
    void slaveDataSourceTest(@Qualifier("SLAVE") final DataSource masterDataSource) {
        // given
        String url = environment.getProperty("spring.datasource.hikari.slave.jdbc-url");
        String username = environment.getProperty("spring.datasource.hikari.slave.username");
        String driverClassName = environment.getProperty("spring.datasource.hikari.slave.driver-class-name");
        Boolean readOnly = Boolean.valueOf(environment.getProperty("spring.datasource.hikari.slave.read-only"));

        // when
        HikariDataSource hikariDataSource = (HikariDataSource) masterDataSource;

        // then
        verifyOf(readOnly, url, username, driverClassName, hikariDataSource);
    }

    @Test
    @DisplayName("리드온리가 트루일 때 분기로 slave가 잘 타지는가에 대한 테스트 커넥션 정보랑은 상관 없음")
    @Transactional(readOnly = true)
    void readOnlyTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        // when
        Method determineCurrentLookupKey = DynamicRoutingDataSource.class.getDeclaredMethod(Test_Method_Name);
        determineCurrentLookupKey.setAccessible(true);

        DataSourceType result = (DataSourceType)determineCurrentLookupKey.invoke(dynamicRoutingDataSource);
        // then

        assertThat(result).isEqualTo(DataSourceType.SLAVE);
    }

    @Test
    @DisplayName("쓰기 분기 테스트")
    @Transactional(readOnly = false)
    void writeOnlyTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        // given
        DynamicRoutingDataSource dynamicRoutingDataSource = new DynamicRoutingDataSource();
        // when
        Method determineCurrentLookupKey = DynamicRoutingDataSource.class.getDeclaredMethod(Test_Method_Name);
        determineCurrentLookupKey.setAccessible(true);


        DataSourceType result = (DataSourceType) determineCurrentLookupKey.invoke(dynamicRoutingDataSource);
        // then

        assertThat(result).isEqualTo(DataSourceType.MASTER);
    }



    private void verifyOf(Boolean readOnly, String url, String username, String driverClassName, HikariDataSource hikariDataSource) {
        assertThat(hikariDataSource.isReadOnly()).isEqualTo(readOnly);
        assertThat(hikariDataSource.getJdbcUrl()).isEqualTo(url);
        assertThat(hikariDataSource.getUsername()).isEqualTo(username);
        assertThat(hikariDataSource.getDriverClassName()).isEqualTo(driverClassName);
    }
}
