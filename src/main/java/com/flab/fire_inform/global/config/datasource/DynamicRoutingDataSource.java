package com.flab.fire_inform.global.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.flab.fire_inform.global.config.datasource.SetDataSource.*;

/**
 * Datasource를 사건에 따라 동적할당하기 위한 클래스.
 */
public class DynamicRoutingDataSource extends AbstractRoutingDataSource
{
    @Override
    protected Object determineCurrentLookupKey() {
        // .isCurrentTransactionReadOnly()는  현재 진행 중인 트랜잭션이 readonly인지 판단하는 메서드이다. 이를 통해 master/replication의 분기가 가능해진다.
        DataSourceType dataSourceType = RoutingDataSourceManager.getCurrentDataSourceName();

        if(TransactionSynchronizationManager.isActualTransactionActive()){
            if (TransactionSynchronizationManager.isCurrentTransactionReadOnly()) {
                dataSourceType = DataSourceType.SLAVE;
            } else {
                dataSourceType = DataSourceType.MASTER;
            }
        }

        RoutingDataSourceManager.removeCurrentDataSourceName();
        return dataSourceType;
    }
}
