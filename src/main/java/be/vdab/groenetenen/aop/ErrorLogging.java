package be.vdab.groenetenen.aop;

import java.time.LocalDateTime;
import java.util.Arrays;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
class ErrorLogging {
	private static final Logger LOGGER = LoggerFactory.getLogger(ErrorLogging.class);
	
	@AfterThrowing(pointcut ="be.vdab.groenetenen.aop.PointcutExpressions.web()", throwing = "ex")
	void schrijfException(JoinPoint joinPoint,Throwable ex) {
		StringBuilder builder = new StringBuilder("Tijdstip\t").append(LocalDateTime.now());
		Authentication authentication =SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {
			builder.append("\nGebruiker\t").append(authentication.getName());
		}
		builder.append("\nMethod\t\t").append(joinPoint.getSignature().toLongString());
		Arrays.stream(joinPoint.getArgs())
			  .forEach(object -> builder.append("\nParameter\t").append(object));
		LOGGER.error(builder.toString(), ex);
	}
}
