package com.cos.photogramstart.web;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.handler.ex.CustomValidationException;
import com.cos.photogramstart.service.ImageService;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ImageController {
	
	private final ImageService imageService; //@RequiredArgsConstructor 를 통한 DI.
	
	@GetMapping({"/","/image/story"})
	public String story() {
		return "image/story";
	}
	
	@GetMapping("/image/popular")
	public String popular() {
		return "image/popular";
	}
	
	@GetMapping("/image/upload")
	public String upload() {
		return "image/upload";
	}
	
	@PostMapping("/image")
	public String imageUpload(ImageUploadDto imageUploadDto,@AuthenticationPrincipal PrincipalDetails principalDetails) {
		//이미지를 업로드 하기위해선 로그인이 되어있는 유저정보가 필요함
		
		if(imageUploadDto.getFile().isEmpty()) { // 이미지 업로드가 null이면
			throw new CustomValidationException("이미지가 첨부되지 않았습니다.", null);
		}
		
		//서비스 호출
		imageService.사진업로드(imageUploadDto, principalDetails);
		
		return "redirect:/user/"+principalDetails.getUser().getId(); //이미지업로드 후에 내비될 주소
	}
		
}
