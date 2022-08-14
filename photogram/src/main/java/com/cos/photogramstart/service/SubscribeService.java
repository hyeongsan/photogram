package com.cos.photogramstart.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class SubscribeService {
	
	private final SubscribeRepository subscribeRepository;
	private final EntityManager em; // 모든 Repository는 EntityManager를 구현해서 만들어져있는 구현체
	
	
	@Transactional(readOnly=true)
	public List<SubscribeDto> 구독리스트(int principalId, int pageUserId){
		
		//쿼리준비
		StringBuffer sb = new StringBuffer(); //일단 StringBuffer열어줌
		sb.append("SELECT u.id, u.username, u.profileImageUrl, "); //주의할 점 : 끝에 한칸 꼭 띄어주기
		sb.append("if((SELECT TRUE  FROM subscribe WHERE fromUserId = ? AND toUserId = u.id),1,0) subscribeState, ");
		sb.append("if((?=u.id),1,0) equalUserState ");
		sb.append("FROM user u INNER JOIN subscribe s ");
		sb.append("ON u.id = s.toUserId ");
		sb.append("WHERE s.fromUserId=?"); // 주의할 점: 세미콜론 끝에 절대들어오면 안됨.
				
		// 1.물음표 principalId(로그인아이디)
		// 2.물음표 principalId(로그인아이디)
		// 3.물음표 pageUserId
		
		//쿼리완성
		Query query = em.createNativeQuery(sb.toString())
				.setParameter(1, principalId)
				.setParameter(2, principalId)
				.setParameter(3, pageUserId);
		
		//쿼리실행 ( qlrm 라이브러리 필요 = DTO에 DB결과를 매핑하기 위해서)
		JpaResultMapper result = new JpaResultMapper(); // JpaResultMapper
		List<SubscribeDto> subscribeDtos = result.list(query, SubscribeDto.class);
				
		return subscribeDtos;
	}
	
	@Transactional // insert하거나 delete하는거니까 DB에 영향을 주니까 @Transactional 사용
	public void 구독하기(int fromUserId, int toUserId) {
		try {
			subscribeRepository.mSubscribe(fromUserId,toUserId); //mSubscribe 여기서 m은 내가 만들었다의 약어	
		}catch(Exception e) {
			throw new CustomApiException("이미 구독을 하였습니다.");
		}
				
	}
	
	@Transactional
	public void 구독취소하기(int fromUserId, int toUserId) {
		subscribeRepository.mUnSubscribe(fromUserId, toUserId);
	}
}
