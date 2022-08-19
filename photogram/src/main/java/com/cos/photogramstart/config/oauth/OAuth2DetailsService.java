package com.cos.photogramstart.config.oauth;

import java.util.Map;
import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class OAuth2DetailsService extends DefaultOAuth2UserService{
	
	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException  {
		System.out.println("OAuth2 서비스 탐");
		OAuth2User oauth2User = super.loadUser(userRequest);
		System.out.println(oauth2User.getAttributes());
		
		/*oauth2User.getAttributes()의 리턴타입은 Map*/
		
		Map<String, Object> userInfo = oauth2User.getAttributes(); 
		
		String username = "facebook_"+(String)userInfo.get("id");
		String password = new BCryptPasswordEncoder().encode(UUID.randomUUID().toString()); // 이 패스워드는 몰라도됨. 이걸로 로그인할게아니니까
																					  // 근데 우리도 모르는 값으로 넣어놓는게 안전
		String name = (String)userInfo.get("name"); // 오브젝트 타입이니까 String으로 다운캐스팅해줘야한다.
		//String email = (String)userInfo.get("email");
		
		User userEntity = userRepository.findByUsername(username);
		
		if(userEntity == null) { // 페이스북 최초로그인
			User user = User.builder()
					.username(username)
					.password(password)
					.email("temp@email.com")
					.name(name)
					.role("ROLE_USER")
					.build();
								
			return new PrincipalDetails(userRepository.save(user),oauth2User.getAttributes()); 
		}else { // 페이스북으로 이미 회원가입이 되어 있다는 뜻
			return new PrincipalDetails(userEntity,oauth2User.getAttributes());
		}

		
	}
}
