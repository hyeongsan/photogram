package com.cos.photogramstart;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//@Controller //얘는 테스트용이었으니까 일단 주석처리해놓음. (메모리에 안올라가도록)
public class ViewControllerTest {

	@GetMapping("/auth/signup")
	public String signupPage() {
		return "auth/signup";
	}
	
	@GetMapping("/auth/signin")
	public String signinPage() {
		return "auth/signin";
	}
	
	@GetMapping("/image/story")
	public String storyPage() {
		return "image/story";
	}
	
	@GetMapping("/image/popular")
	public String popularPage() {
		return "image/popular";
	}
	
	@GetMapping("/image/upload")
	public String uploadPage() {
		return "image/upload";
	}
	
	@GetMapping("/user/profile")
	public String profilePage() {
		return "user/profile";
	}

	@GetMapping("/user/update")
	public String updatePage() {
		return "user/update";
	}
}
