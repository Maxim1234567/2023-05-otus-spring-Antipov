package ru.otus.logging;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
    @Around("@annotation(ru.otus.logging.Logging)")
    public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("Start work method: " + joinPoint.getSignature().getName());

        Object result = joinPoint.proceed();

        System.out.println("End work method: " + joinPoint.getSignature().getName());

        return result;
    }
}
