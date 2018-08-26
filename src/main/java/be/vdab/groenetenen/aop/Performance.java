package be.vdab.groenetenen.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(2)
class Performance {
	private static final Logger LOGGER = LoggerFactory.getLogger(Performance.class);
	
	@Around("be.vdab.groenetenen.aop.PointcutExpressions.services()")
	Object schrijfPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
		long voor = System.nanoTime();
		try {
			return joinPoint.proceed();
		} finally {
			long duurtijd = (System.nanoTime() - voor)/1000000;
			LOGGER.info("\n\t{} duurde {} milliseconden",joinPoint.getSignature().toLongString(), duurtijd);
		}
	}
}