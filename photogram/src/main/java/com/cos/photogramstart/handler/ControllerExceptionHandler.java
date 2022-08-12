package com.cos.photogramstart.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.util.Script;
import com.cos.photogramstart.web.dto.CMRespDto;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
	
	@ExceptionHandler(CustomValidationException.class) // RuntimeException을 발생하는 모든 exception을 이 함수가 가로챔
	public String validationException(CustomValidationException e) {
		System.out.println("나 발동됨?");
		//CMRespDto, Script비교
		//1.클라이언트에게 응답할 때는 Script 좋음
		//2.Ajax 통신 - CMRespDto 개발자는 코드로 받는게 처리하기 쉬우니까
		//3.Android 통신 - CMRespDto 개발자는 코드로 받는게 처리하기 쉬우니까
		
		if(e.getErrorMap()==null) {
			return Script.back(e.getMessage());
		}else {
			return Script.back(e.getErrorMap().toString());
		}
	}
	
	@ExceptionHandler(CustomException.class) 
	public String exception(CustomException e) {
		return Script.back(e.getMessage());
	}
	
	
	@ExceptionHandler(CustomValidationApiException.class) // RuntimeException을 발생하는 모든 exception을 이 함수가 가로챔
	public ResponseEntity<?> validationApiException(CustomValidationApiException e) {
		System.out.println("나실행됨?");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),e.getErrorMap()),HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(CustomApiException.class) // RuntimeException을 발생하는 모든 exception을 이 함수가 가로챔
	public ResponseEntity<?> apiException(CustomApiException e) {
		System.out.println("나실행됨?");
		return new ResponseEntity<>(new CMRespDto<>(-1,e.getMessage(),null),HttpStatus.BAD_REQUEST);
	}
}
 