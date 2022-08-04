package com.flab.fire_inform.global.aop;

import com.flab.fire_inform.global.config.datasource.RoutingDataSourceManager;
import com.flab.fire_inform.global.config.datasource.SetDataSource;
import com.flab.fire_inform.global.exception.CustomException;
import com.flab.fire_inform.global.exception.error.ErrorCode;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import static com.flab.fire_inform.global.config.datasource.SetDataSource.*;

@Aspect
@Component
public class SetDataSourceAspect {

    // AOP를 이용해 커넥션을 연결하기 전에 @annotation 정보를 가지고 어떤 디비에 붙을지 확인한다.
    // 해당 정보를 통해 쓰레드 로컬에 현재 실행할 쿼리는 어떤 쿼리로 보낼건지 저장한다. 종류에 존재하지 않는다면 예외 던짐
    @Before("@annotation(com.flab.fire_inform.global.config.datasource.SetDataSource) && @annotation(target)")
    public void setDataSource(SetDataSource target) throws CustomException{
        if (target.dataSourceType() == DataSourceType.MASTER
                || target.dataSourceType() == DataSourceType.SLAVE) {
            RoutingDataSourceManager.setCurrentDataSourceName(target.dataSourceType());
        } else {
            throw new CustomException(ErrorCode.WRONG_DATASOURCE);
        }
    }
}
