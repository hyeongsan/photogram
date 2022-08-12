package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomValidationApiException extends RuntimeException{

	//시리얼번호는 객체를 구분할 때 쓰는것 = 중요한거 아님
	private static final long serialVersionUID = -807520916259076805L;

	/*field*/
	//private String message;
	private Map<String,String>errorMap;
	
	/*constructor*/
	public CustomValidationApiException(String message,Map<String,String>errorMap) {
		super(message);
		//this.message = message;
		this.errorMap = errorMap;
	}
	
	public CustomValidationApiException(String message) {
		super(message);
	}
	
	/*getter*/
	public Map<String,String>getErrorMap(){
		return errorMap;
	}
	
}
