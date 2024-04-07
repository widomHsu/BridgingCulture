package edu.monash.bridgingculture.service.utils;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import javax.servlet.http.HttpServletRequest;

/**
 * This aspect class provides logging functionality for annotated methods.
 */
@Aspect
@Component
@Slf4j
public class LogAspect {

    /**
     * Pointcut to identify methods annotated with @Log.
     */
    @Pointcut("@annotation(edu.monash.bridgingculture.controller.annotation.Log)")
    private void webLog(){}

    /**
     * Advice method to log information before the execution of methods annotated with @Log.
     */
    @Before("webLog()")
    public void info() {
        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        log.info(request.getRemoteAddr() + " " + request.getMethod() + " " + request.getRequestURI());
    }
}
