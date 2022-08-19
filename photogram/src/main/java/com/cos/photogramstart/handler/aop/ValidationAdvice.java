package com.cos.photogramstart.handler.aop;

import java.util.HashMap;
import java.util.Map;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;

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
		Object[] args = proceedingJoinPoint.getArgs();
		for(Object arg:args) {
			System.out.println(arg);	
			if(arg instanceof BindingResult) { // arg인스턴스가 BindingResult라는 타입이 있으면
				System.out.println("유효성 검사를 하는 함수입니다.");
				BindingResult bindingResult = (BindingResult) arg;
				
				if(bindingResult.hasErrors()) { // bindingResult에 에러가 있다는건
					
					Map<String,String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) { //getFieldErrors()는 list를 리턴				
						errorMap.put(error.getField(), error.getDefaultMessage());
						System.out.println("here"+error.getDefaultMessage());
					}			
					//return "오류남";
					throw new CustomValidationApiException("유효성검사실패함",errorMap);
				}
			}
		}
		//proceedingJoinPoint는 해당 *(..) 함수의 내부까지 모두 접근할 수있는 변수
		// .proceed()는 그 함수로 다시 돌아가라는 것.
		
		return proceedingJoinPoint.proceed(); // 여기서 *(..) 해당 함수가 실행된다. 
											  // 만약 null 이라고 적으면 해당 함수 실행안됨.
	}
	
	@Around("execution(* com.cos.photogramstart.web.*Controller.*(..))")
	public Object advice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
		
		System.out.println("web 컨트롤러====================");
		Object[] args = proceedingJoinPoint.getArgs();
		for(Object arg:args) {
			System.out.println(arg);
			if(arg instanceof BindingResult) { // arg인스턴스가 BindingResult라는 타입이 있으면
				
				BindingResult bindingResult = (BindingResult) arg;

				if(bindingResult.hasErrors()) { // bindingResult에 에러가 있다는건
					
					Map<String,String> errorMap = new HashMap<>();
					
					for(FieldError error : bindingResult.getFieldErrors()) { //getFieldErrors()는 list를 리턴				
						errorMap.put(error.getField(), error.getDefaultMessage());
						System.out.println("here"+error.getDefaultMessage());
					}			
					//return "오류남";
					throw new CustomValidationException("유효성검사실패함",errorMap);
				}
			}
		}
		
		return proceedingJoinPoint.proceed();
	}
	
	
}
