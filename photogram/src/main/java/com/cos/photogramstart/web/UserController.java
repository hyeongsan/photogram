package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.service.UserService;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class UserController {

	private final UserService userService;
	
	@GetMapping("/user/{pageUserId}")
	public String profile(@PathVariable int pageUserId,Model model,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		UserProfileDto dto = userService.회원프로필(pageUserId,principalDetails.getUser().getId());
		model.addAttribute("dto", dto);
		return "user/profile";
	}
	
	@GetMapping("/user/{id}/update")
	public String update(@PathVariable int id,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		
		//1.세션 쉽게찾는법
		//System.out.println("세션정보:"+principalDetails.getUser());
		
		/*2.세션정보(직접 복잡하게 찾는법)
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		PrincipalDetails mPrincipalDetails = (PrincipalDetails)auth.getPrincipal(); // 다운캐스팅
		
		System.out.println("직접 복잡하게찾는법:" + mPrincipalDetails.getUser());
				*/
		return "user/update";
	}
}
