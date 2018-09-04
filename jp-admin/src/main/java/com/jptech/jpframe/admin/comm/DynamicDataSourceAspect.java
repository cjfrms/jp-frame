package com.jptech.jpframe.admin.comm;

import com.google.common.base.Strings;
import com.jptech.jpframe.admin.comm.annotation.TargetDataSource;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Objects;

@Order(0)
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
        if(!Strings.isNullOrEmpty(datasourceId)){
            DataSourceContextHolder.set(targetDataSource.id());
            logger.debug(String.format("数据源切换为 ==> %s", datasourceId));
        }
    }

    @After("@annotation(targetDataSource)")
    public void doAfter(JoinPoint joinPoint, TargetDataSource targetDataSource) {
        if(!Strings.isNullOrEmpty( DataSourceContextHolder.getDataSourceId())){
            DataSourceContextHolder.clear();
            logger.debug(String.format("清理数据源 ==> %s", targetDataSource.id()));
        }

    }

    @Before(value = "pointCut()")
    public void doBeforeWithSlave(JoinPoint joinPoint) throws NoSuchMethodException {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //当前切点方法
        Method method = methodSignature.getMethod();
        //判断是否为接口的方法
        if (method.getDeclaringClass().isInterface()) {
            method = joinPoint.getTarget().getClass()
                    .getDeclaredMethod(joinPoint.getSignature().getName(), method.getParameterTypes());
        }

        if (Objects.isNull(method.getAnnotation(TargetDataSource.class))) {
        }
    }
}
