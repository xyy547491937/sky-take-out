package com.sky.aop;

import ch.qos.logback.core.ContextBase;
import com.sky.annotation.AutoFill;
import com.sky.constant.AutoFillConstant;
import com.sky.context.BaseContext;
import com.sky.enumeration.OperationType;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {
    /*切入点*/
    @Pointcut("execution(* com.sky.mapper.*.*(..)) && @annotation(com.sky.annotation.AutoFill)")
    public void autoPointCut() {
    }

    /*
     * 前置通知
     * */

    @Before("autoPointCut()")
    public void autoFill(JoinPoint joinPoint) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        //
        log.info("公共字段填充拦截");

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill annotation = signature.getMethod().getAnnotation(AutoFill.class);

        OperationType operationType = annotation.value();
        Object[] args = joinPoint.getArgs();
        if(args ==null || args.length ==0) {
            return ;
        }


        Object arg = args[0];

        LocalDateTime now = LocalDateTime.now();
        Long currentId = BaseContext.getCurrentId();

        Method setCreateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
        Method setCreateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_CREATE_USER, Long.class);
        Method setUpdateTime = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
        Method setUpdateUser = arg.getClass().getDeclaredMethod(AutoFillConstant.SET_UPDATE_USER, Long.class);

        if(operationType == OperationType.INSERT) {

            setCreateTime.invoke(arg,now);
            setCreateUser.invoke(arg,currentId);
            setUpdateTime.invoke(arg,now);
            setUpdateUser.invoke(arg,currentId);
        }else if(operationType ==OperationType.UPDATE) {
            setUpdateTime.invoke(arg,now);
            setUpdateUser.invoke(arg,currentId);
        }

    }
}
