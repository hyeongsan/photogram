package com.cos.photogramstart.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.domain.likes.LikesRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class LikesService {
	private final LikesRepository liesRepository;

	@Transactional// DB에 영향을 주는것이므로 @Transactional
	public void 좋아요(int imageId, int principalId) {

		liesRepository.mLikes(imageId, principalId); // 좋아요를 누르면 insert
		}
	
	@Transactional
	public void 좋아요취소(int imageId, int principalId) {

		liesRepository.mUnLikes(imageId, principalId); // 좋아요를 누르면 delete
		}
}
