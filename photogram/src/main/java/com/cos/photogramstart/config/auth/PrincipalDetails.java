package com.cos.photogramstart.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.photogramstart.domain.user.User;

import lombok.Data;

@Data
public class PrincipalDetails implements UserDetails{


	private static final long serialVersionUID = 1L;
	
	private User user; // 이걸적은이유는 여기(PrincipalDetails.java)에다가 내 User객체를 담고싶어서					
	
	public PrincipalDetails(User user) {
		this.user=user;
	}

	//권한: 한개가 아닐 수 있음.(3개 이상의 권한)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() { //권한을 가져오는 함수
		
		Collection<GrantedAuthority> collector = new ArrayList<>();
		collector.add(()-> {//collector에는 아무런권한이 없으므로 넣어준다.
					
				return user.getRole();
		});
		
		return collector;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	
	/*false가 있으면 로그인이 안됨*/
	@Override
	public boolean isAccountNonExpired() {
		return true; // 니 계정이 만료가 되었니? 아니 -> true
	}

	@Override
	public boolean isAccountNonLocked() { // 이게 false 떨어지면 로그인이 안됨
		return true; // 니 계정이 잠겼니? 아니 -> true
	}

	@Override
	public boolean isCredentialsNonExpired() { 
		return true; // 니 계정의 Credential이 만기되지않았니 ? 비밀번호가 1년이 지났는데 한번도 안바뀐거 아니니 ?
	}

	@Override
	public boolean isEnabled() {
		return true; // 니 계정이 활성화 되어있니 ? 한 10년동안 로그인안하면 비활성화 되겠죠?
	}
	
}
