package com.example.bookmanagement.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class TimeAspect {
    @Around("execution(* com.example.bookmanagement.controller.*.*(..))")
    public Object timeCost(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long end = System.currentTimeMillis();
        log.info(joinPoint.getSignature() + "的消耗时间为: " + (end - start) + "ms");
        return result;
    }
}
