package com.cos.photogramstart.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.cos.photogramstart.config.oauth.OAuth2DetailsService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EnableWebSecurity // 3.현재 이 파일로 시큐리티를 활성화
@Configuration // 2.Ioc에 띄워줘야함 !!
public class SecurityConfig extends WebSecurityConfigurerAdapter{ // 1.WebSecurityConfigurerAdapter로 상속해줘야함
	
	private final OAuth2DetailsService oAuth2DetailsService;

	@Bean
	public BCryptPasswordEncoder encode() {
		return new BCryptPasswordEncoder();
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*super 삭제 - 기존 시큐리티가 가지고 있는 기능이 다 비활성화됨 ( 오버라이딩되어져서 )*/
		
		http.csrf().disable();
		
		//1.로그인 권한이 없는 페이지로 접속시 로그인화면으로 내비되도록 하겠음
		//2.아래 경로로 접속시 .authenticated() -> 인증이 필요해!
		//3.anyRequest()->그게아닌 모든요청은 permitAll() -> 허용하겠다.
		http.authorizeRequests()
		.antMatchers("/","/user/**","/image/**","/subscribe/**","/comment/**","/api/**").authenticated()
		.anyRequest().permitAll()
		.and()
		.formLogin() //antMatchers에 걸리면 우리는 formLogin()을 하겠다. formLogin()이란 form태그, input태그가 있는 페이지를 말함 
		.loginPage("/auth/signin") //그 formLogin() 페이지가 /auth/signin이라는 뜻이다. -GET방식
		.loginProcessingUrl("/auth/signin") // POST방식 - 스프링시큐리티가 로그인 프로세스 진행
		.defaultSuccessUrl("/") //로그인이 정상적으로 됬으면 어디로 가게 할 것인지 설정
		.and()
		.oauth2Login() // formLogin()도 하는데, oauth2로그인도 할꺼야!!
		.userInfoEndpoint() // oauth2로그인을 하면 최종응답을 code가 아니라 회원정보로 바로받겠다 (email, public_profile)
		.userService(oAuth2DetailsService); 
	}
}
