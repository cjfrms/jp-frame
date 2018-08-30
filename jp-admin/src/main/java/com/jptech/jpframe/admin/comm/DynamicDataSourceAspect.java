package com.jptech.jpframe.admin.comm;

import com.jptech.jpframe.admin.comm.annotation.TargetDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class DynamicDataSourceAspect {

    private Logger logger =  LoggerFactory.getLogger(this.getClass());

    @Pointcut("execution(* *..service.*Service.*(..))")
    public void pointCut() {
    }

    @Before("@annotation(targetDataSource)")
    public void doBefore(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        String datasourceId = targetDataSource.id();
        logger.info(String.format("设置数据源为  %s", datasourceId));
        DataSourceContextHolder.set(targetDataSource.id());
    }
}
