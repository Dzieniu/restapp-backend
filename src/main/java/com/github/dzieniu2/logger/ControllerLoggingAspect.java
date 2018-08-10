package com.github.dzieniu2.logger;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class ControllerLoggingAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    public void restControllerMethods() {}

    @Around("restControllerMethods()")
    public void logRestControllerMethods(ProceedingJoinPoint jp) {
        String methodName = jp.getSignature().getName();
        String parameters = "";
//        Arrays.stream(jp.getArgs()).forEach(arg ->  arg);
        log.info("Executing method: " + jp.getSignature().getDeclaringType().getSimpleName() + "." + methodName + "()");
    }

//    @Pointcut("@within(org.springframework.stereotype.Service)")
//    public void servicesMethods() {}
//
//    @Around("servicesMethods()")
//    public void logServiceMethods(JoinPoint jp) {
//        String methodName = jp.getSignature().getName();
//        log.info("Executing method: " + methodName);
//    }
}
