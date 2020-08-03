package cn.com.chenby.local.test.customizeAnnotation.annotation.aspect;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Aspect
@Component
@Slf4j
public class TestLogAspect {

    @Pointcut("@annotation(cn.com.chenby.local.test.customizeAnnotation.annotation.TestLog)")
    private void testLog(){}

    @Resource
    HttpServletResponse repose;


    @Before("testLog()")
    public void testLogBefore() throws Throwable{
        log.debug("===========================  do before pointcut  =============================");
    }


    @After("testLog()")
    public void testLogAfter() throws Throwable{
        log.debug("===========================  do after pointcut  =============================");
    }

    @Around("testLog()")
    public Object testLogAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
        Object result = proceedingJoinPoint.proceed();
        log.debug("===========================  do around pointcut  =============================");
        return result;
    }

    @AfterReturning("testLog()")
    public void testLogAfterReturning() throws Throwable{
        log.debug("===========================  do afterReturning pointcut  =============================");
    }

    @AfterThrowing("testLog()")
    public void testLogAfterThrowing() throws Throwable{
        log.debug("===========================  do afterThrowing pointcut  =============================");
    }
}
