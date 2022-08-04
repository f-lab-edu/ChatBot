package com.flab.fire_inform.global.config.datasource;

import static com.flab.fire_inform.global.config.datasource.SetDataSource.*;

public class RoutingDataSourceManager {
    // 현재 커넥션은 기본적으로 ThreaLocal에 저장해서 사용한다.
    private static final ThreadLocal<DataSourceType> currentDataSourceName = new ThreadLocal<>();

    public static void setCurrentDataSourceName(DataSourceType dataSourceType) {
        currentDataSourceName.set(dataSourceType);
    }

    public static DataSourceType getCurrentDataSourceName() {
        return currentDataSourceName.get();
    }

    public static void removeCurrentDataSourceName() {
        currentDataSourceName.remove();
    }
}
