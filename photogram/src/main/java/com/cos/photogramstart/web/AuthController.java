package com.cos.photogramstart.web;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.AuthService;
import com.cos.photogramstart.web.dto.auth.SignupDto;

import lombok.RequiredArgsConstructor;

/*컨트롤러가 되기위해선 @Controller 어노테이션이 붙어있어야한다. */
/*@Controller=>1.Ioc에 등록이 됬다는 의미이자 2. 파일을 리턴하겠다는 의미*/
@RequiredArgsConstructor // final 필드를 DI 할 때 사용
@Controller 
public class AuthController {
	
	private final AuthService authService;
	
	@GetMapping("/auth/signin")
	public String signinForm() {
		return "auth/signin";
	}
	
	@GetMapping("/auth/signup")
	public String signupForm() {
		return "auth/signup";
	}
	
	@PostMapping("/auth/signup") // post요청
	public String signup(@Valid SignupDto signupDto, BindingResult bindingResult) { // key = value (x-www-form-urlencoded 방식)
		
		if(bindingResult.hasErrors()) { // bindingResult에 에러가 있다는건
			
			Map<String,String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) { //getFieldErrors()는 list를 리턴				
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("here"+error.getDefaultMessage());
			}			
			//return "오류남";
			throw new CustomValidationException("유효성검사실패함",errorMap);
		}else {
			User user = signupDto.toEntity();
			User userEntity = authService.회원가입(user); //여기서 userEntity는 DB에 들어간 field값이 박혀 리턴된 User객체
			System.out.println("확인용"+userEntity);
			return "auth/signin"; // 회원가입이 성공하면 로그인페이지로 가도록
		}			
	}
}
