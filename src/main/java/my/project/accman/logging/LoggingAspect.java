package my.project.accman.logging;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.*;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * This class defines various aspects which can be used for logging Method data.
 */
@Slf4j
@Aspect
@Component
public class LoggingAspect {

    /**
     * All methods annotated with LogExecutionTime will log their execution time.
     * @param joinPoint an Aspect joinpoint
     * @return any data
     * @throws Throwable
     */
    @Around("@annotation(LogExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;
        log.info(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

    /**
     * All methods annotated with LogMethod will log their signature data, arguments, return data and thrown exceptions.
     * @param joinPoint an Aspect joinpoint
     * @return any data
     * @throws Throwable
     */
    @AfterThrowing
    @Around("@annotation(LogMethod)")
    public Object logMethodInfo(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();

        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        Object[] args = joinPoint.getArgs();

        log.info("Start call: " + className + ":" + methodName + "()" + " with arguments: " + mapper.writeValueAsString(args));
        Object object = null;
        try {
            object = joinPoint.proceed();
            log.info("End call: " + className + ":" + methodName + "()" + " returned: " + mapper.writeValueAsString(object));
        } catch (Exception e) {
            log.info("End call: " + className + ":" + methodName + "()" + " returned Exception: " + e.getClass().getSimpleName());
            throw e;
        }

        return object;
    }

    /**
     * All methods annotated with LogMethodSimple will only log a methods class and name.
     * @param joinPoint an Aspect joinpoint
     * @return any data
     * @throws Throwable
     */
    @Before("@annotation(LogMethodSimple)")
    public void LogMethodSimple(JoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().toString();
        log.info("Start call: " + className + ":" + methodName + "()");
    }
}