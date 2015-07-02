package cz.jiripinkas.jba.interceptor;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class ServiceInterceptor {
	
	/**logger */
	private  final Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	@Before("execution(public * cz.jiripinkas.jba.repository.*Repository.*(..))")
	public void logBefore(JoinPoint joinPoint) {
 
		final String method = joinPoint.getTarget().getClass().getName() + "."
				+ joinPoint.getSignature().getName();
		
		final String method2 = joinPoint.getClass().getName() + "."
				+ joinPoint.getSignature().getName();
		
		System.out.println("method name: " + method);
		System.out.println("implementation method name: " + method2);
		System.out.println("logBefore() is running!");
		System.out.println("hijacked : " + joinPoint.getSignature().getName());
		System.out.println("******");
	}
}
