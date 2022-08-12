package com.cos.photogramstart.config.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service // 이걸 붙이는 순간 Ioc에 올라감
public class PrincipalDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	
	/*1.패스워드는 알아서 체킹하니까 신경 쓸 필요없다.*/
	/*2.리턴이 잘되면 자동으로 UserDetails타입을 세션을 만들어준다 (내부적으로)*/
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity==null) { // null이면 못찾았다는 뜻 (DB에 없다는 뜻)
			return null; // 그럼 null
		}else { //찾았으면
		  //return userEntity; // userEntity를 리턴하려고 했는데 리턴 데이터타입은 UserDetails이다.
		  //return null; // 임시방편 null
			return new PrincipalDetails(userEntity); //해당userEntity를 세션에 저장
		}
		
		
	}
	
}
