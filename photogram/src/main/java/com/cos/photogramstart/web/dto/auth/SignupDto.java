package com.cos.photogramstart.web.dto.auth;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data // Getter, Setter 생성
public class SignupDto { // Dto - 통신할 때 필요한 데이터를 담아주는 오브젝트
	//field
	@Size(min=2,max=20)
	@NotBlank
	private String username;
	@NotBlank // 무조건받겠다는 뜻
	private String password;
	@NotBlank
	private String email;
	@NotBlank
	private String name;
	
	public User toEntity() {
		return User.builder()
				.username(username)
				.password(password)
				.email(email)
				.name(name)
				.build();
	}
}
