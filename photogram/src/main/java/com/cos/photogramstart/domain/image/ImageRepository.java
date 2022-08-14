package com.cos.photogramstart.domain.image;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ImageRepository extends JpaRepository<Image,Integer> {
	
	@Query(value="select * from image where userId IN (select toUserId from subscribe where fromUserId= :principalId) ORDER BY id DESC", nativeQuery=true)
	Page<Image> mStory(int principalId,Pageable pageable); // 이 mStory를 가져올 때, 3건씩 정렬해서 가져옴 
}
