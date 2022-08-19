package com.cos.photogramstart.handler.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component //RestController,Service 모든 것들이 Component의 구현체임.
@Aspect
public class ValidationAdvice {
	
	//@Before 어떤함수가 실행되기 직전에 실행
	//@After 어떤함수가 실행된 후에 실행
	//@Around 어떤함수가 실행되기 직전에 실행하고 끝나고 나서도 관여
	
	
	// * 자리는 public함수를 할래? protected함수를 할래? 를 정하는자리 => 다할꺼라서 *로 표기
	// *Controller 로 끈나는 모든 Controller
	// *(..) 메소드의 파라미터가 뭐든 상관없는 => 현재 web/api밑에있는 Auth/Image/UserController.java의 모든메소드가 실행될 때 작동한다는 뜻
	@Around("execution(* com.cos.photogramstart.web.api.*Controller.*(..))") 
	public Object apiAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		System.out.println("web api 컨트롤러====================");
		//proceedingJoinPoint는 해당 *(..) 함수의 내부까지 모두 접근할 수있는 변수
		// .proceed()는 그 함수로 다시 돌아가라는 것.
		
		return proceedingJoinPoint.proceed(); // 여기서 *(..) 해당 함수가 실행된다. 
											  // 만약 null 이라고 적으면 해당 함수 실행안됨.
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		System.out.println("web 컨트롤러====================");
		return proceedingJoinPoint.proceed();
	}
	
	
}
