package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service //1.Ioc에 올라감 2.트랜잭션관리
public class AuthService {
	
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	@Transactional // Write(Insert,Update,Delete)
	public User 회원가입(User user) {
		//회원가입진행 (repository필요)
		String rawPassword = user.getPassword(); //입력된 비밀번호
		String encPassword = bCryptPasswordEncoder.encode(rawPassword); // 비밀번호 해시처리
		user.setPassword(encPassword); // 해시된 비밀번호 셋팅
		user.setRole("ROLE_USER"); // 관리는 ROLE_ADMIN으로 넣을예정
		
		User userEntity = userRepository.save(user); // save를 쓰면 DB에 들어가고 내가 넣은 객체의 타입을 리턴받을 수 있다.
													 // 그리고 그것을 userEntity라는 User객체에 담는다
		return userEntity;
	}
}
