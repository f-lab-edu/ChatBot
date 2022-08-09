package com.flab.fire_inform.global.config.datasource;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
mapper에서 데이터 소스 선택을 위한 @Annotation
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface SetDataSource {
    DataSourceType dataSourceType();
    enum DataSourceType{
        MASTER, SLAVE;
    }
}
