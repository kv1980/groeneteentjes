package be.vdab.groenetenen.aop;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(3)
class MethodLogging {
	private static final Logger LOGGER = LoggerFactory.getLogger(MethodLogging.class);

	/*
	@Before("be.vdab.groenetenen.aop.PointcutExpressions.services()")
	void schrijfAuditVoorUitvoerenMethod(JoinPoint joinPoint) {
		StringBuilder builder = new StringBuilder("\nTijdstip\t").append(LocalDateTime.now());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			builder.append("\nGebruiker\t").append(authentication.getName());
		}
		builder.append("\nMethod\t\t").append(joinPoint.getSignature().toLongString());
		Arrays.stream(joinPoint.getArgs())
			  .forEach(object -> builder.append("\nParameter\t").append(object));
		LOGGER.info(builder.toString());
	}
	*/
	
	@AfterReturning(pointcut = "be.vdab.groenetenen.aop.PointcutExpressions.services()",returning = "returnValue")
	void schrijfAuditNaUitvoerenMethod(JoinPoint joinPoint, Object returnValue) {
		StringBuilder builder = new StringBuilder("\n\tTijdstip\t").append(LocalDateTime.now());
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			builder.append("\n\tGebruiker\t").append(authentication.getName());
		}
		builder.append("\n\tMethod\t\t").append(joinPoint.getSignature().toLongString());
		Arrays.stream(joinPoint.getArgs())
			  .forEach(object -> builder.append("\n\tParameter\t").append(object));
		if (returnValue != null) {
			builder.append("\n\tReturn\t\t");
			if (returnValue instanceof Collection) {
				builder.append(((Collection<?>) returnValue).size()).append(" objects");
			} else {
				builder.append(returnValue.toString());
			}
		}
		LOGGER.info(builder.toString());
	}
}