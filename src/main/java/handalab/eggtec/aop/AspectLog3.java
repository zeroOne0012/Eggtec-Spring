
package handalab.eggtec.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class AspectLog3 {

	@Pointcut("execution(* handalab.eggtec..*(..))")
	public void aopLog() {}

	// 타입 패턴이 *Service
	@Pointcut("execution(* *..*Service.*(..))")
	public void allService() {
	}

    @Pointcut("aopLog() && allService()")
	public void isService() {
	}

	@Around("isService()")
	public Object doLog(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("[log!!!] {}", joinPoint.getSignature()); // join point 시그니처
		return joinPoint.proceed();
	}
}
