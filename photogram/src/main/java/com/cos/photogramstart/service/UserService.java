package com.cos.photogramstart.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UserRepository;
import com.cos.photogramstart.handler.ex.CustomException;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.web.dto.user.UserProfileDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	
	private final UserRepository userRepository; // userRepository 필요
	private final BCryptPasswordEncoder bCryptPasswordEncoder;
	private final SubscribeRepository subscribeRepository;
	
	@Transactional(readOnly = true)
	public UserProfileDto 회원프로필(int pageUserId, int principalId) {
		
		UserProfileDto dto = new UserProfileDto();
		
		//해당유저가 가지고 있는 모든 사진을 가져올 것이다.
		// SELECT * FROM image WHERE userId = :userId;
		User userEntity = userRepository.findById(pageUserId).orElseThrow(()->{
			//해당 유저아이디로 검색이 될 수도 있고, 안될 수도 있다. 안될 수도 있기 때문에 orElseThrow를 적어주었음
			//해당유저를 못찾으면 익셉션 발동
			throw new CustomException("해당 프로필 페이지는 없는 페이지입니다.");
		});
		
		//userEntity.getImages().get(0);
		
		dto.setUser(userEntity);
		dto.setPageOwnerState(pageUserId==principalId); // 1은 페이지 주인, -1은 주인이 아님
		dto.setImageCount(userEntity.getImages().size());
		
		int subscribeState = subscribeRepository.mSubscribeState(principalId, pageUserId);
		int subscribeCount = subscribeRepository.mSubscribeCount(pageUserId);
		
		dto.setSubscribeState(subscribeState==1); // SubscribeState은 boolean 타입이라서 파라미터 이렇게
		dto.setSubscribeCount(subscribeCount);
		
		//좋아요 카운트 추가하기
		userEntity.getImages().forEach((image)->{			
			image.setLikeCount(image.getLikes().size());
		});
		
		return dto;
	}
	
	@Transactional
	public User 회원수정(int id, User user) {
		
		//0.영속화
		//1.무조건 찾았다. 걱정마 get() 2.못찾았어 익셉션 발동시킬게 orElseThrow()라는 함수
		User userEntity = userRepository.findById(id).orElseThrow(()->{				
			return new CustomValidationApiException("찾을 수 없는 id입니다.");			
		}); 

		
		//2. 이 영속화되어있는 데이터에 수정을 해주면 DB에도 반영이 되어진다.
		userEntity.setName(user.getName());
		
		String rawPassword = user.getPassword();
		String encPassword = bCryptPasswordEncoder.encode(rawPassword);
		
		userEntity.setPassword(encPassword);
		userEntity.setBio(user.getBio());
		userEntity.setWebsite(user.getWebsite());
		userEntity.setPhone(user.getPhone());
		userEntity.setGender(user.getGender());
		return userEntity;	//더티체킹이 일어나지고 업데이트(수정)가 완료됨.
				
	}
}
