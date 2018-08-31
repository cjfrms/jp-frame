package com.jptech.jpframe.admin.comm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import java.util.Map;

public class DynamicRoutingDataSource extends AbstractRoutingDataSource {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Override
    public void setTargetDataSources(Map<Object, Object> targetDataSources) {
        super.setTargetDataSources(targetDataSources);
    }

    @Override
    protected Object determineCurrentLookupKey() {
        logger.info("XXX:"+DataSourceContextHolder.getDataSourceId());
        return DataSourceContextHolder.getDataSourceId();
    }
}
