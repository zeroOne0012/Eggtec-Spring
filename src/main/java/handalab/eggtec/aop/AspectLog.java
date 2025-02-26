package handalab.eggtec.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
@Component
public class AspectLog {

    @Around("execution(* handalab.eggtec..*(..))")
    public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log!] {}", joinPoint.getSignature()); // join point 시그니처
		return joinPoint.proceed();
	}
}
