package com.cos.photogramstart.domain.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe,Integer>{

	/*직접만들어줌 그래서 함수명앞에 mSubscribe이런식으로 m 을 붙여줬다.*/
	@Modifying // INSERT,DELETE,UPDATE를 네이티브 쿼리로 작성하려면 해당 어노테이션 필요 !!
	@Query(value="INSERT INTO subscribe(fromUserId,toUserId,createDate) VALUES(:fromUserId,:toUserId,now())",nativeQuery=true)
	void mSubscribe(int fromUserId,int toUserId); 												
	
	@Modifying
	@Query(value="DELETE FROM subscribe WHERE fromUserId=:fromUserId AND toUserId=:toUserId",nativeQuery=true)
	void mUnSubscribe(int fromUserId, int toUserId);
	
	@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId=:principalId AND toUserId=:pageUserId", nativeQuery=true) // 얘네는 select만 할 것이라서 @Modifying이 필요없음
	int mSubscribeState(int principalId, int pageUserId); // 리턴값이 1이 라는 것은 구독된상태
	
	@Query(value="SELECT COUNT(*) FROM subscribe WHERE fromUserId=:pageUserId", nativeQuery=true)
	int mSubscribeCount(int pageUserId); // 리턴값이 1이라는것을 해당페이지의 주인이 구독한 구독자수를 나타냄
	
	
}
