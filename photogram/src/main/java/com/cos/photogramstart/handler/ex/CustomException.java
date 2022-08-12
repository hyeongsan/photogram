package com.cos.photogramstart.handler.ex;

import java.util.Map;

public class CustomException extends RuntimeException{

	//시리얼번호는 객체를 구분할 때 쓰는것 = 중요한거 아님
	private static final long serialVersionUID = -807520916259076805L;

	/*constructor*/
	public CustomException(String message) {
		super(message);
	}
}
