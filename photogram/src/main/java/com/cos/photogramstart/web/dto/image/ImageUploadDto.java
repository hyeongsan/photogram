package com.cos.photogramstart.web.dto.image;

import org.springframework.web.multipart.MultipartFile;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class ImageUploadDto {
	
	/*파일과 caption을 input name을 통해 받을꺼니까*/
	private MultipartFile file; // 파일은 MultipartFile을 통해 받을 수 있음
	private String caption;
	
	
	public Image toEntity(String profileImageUrl,User user) {
		return Image.builder() 
				.caption(caption)
				.profileImageUrl(profileImageUrl) //1.jpg만있는게 아니라 UUID가 붙은걸 여기 넣어야하므로 파라미터로 받아와야함
				.user(user) // user정보를 넣는이유는 이미지객체는 user정보가 필요하다 -> 누가 등록했는지 알아야하니까
				.build();
	}
}
