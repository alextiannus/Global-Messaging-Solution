package com.service;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * Created by Donnie on 2017/2/21.
 */
@Aspect
@Component
@Slf4j
public class LogAspect {
    public void doAfter(JoinPoint jp){

        final long parentThread = Thread.currentThread().getId();

        log.debug("method :"+jp.getSignature().getName()+",LogAspect parent thread id is:"+parentThread);

        Object[] args = jp.getArgs();
        for (Object obj: args) {
//            if (obj instanceof SMSLog) {}
        }
    }
}
