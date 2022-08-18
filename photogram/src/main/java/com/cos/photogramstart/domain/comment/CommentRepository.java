package com.cos.photogramstart.domain.comment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment,Integer>{

//	@Modifying
//	@Query(value="INSERT INTO comment(content,imageId,userId,createDate) VALUES(:content, :imageId, :userId, now())",nativeQuery=true)
//	Comment mSave(String content, int imageId, int userId);
//직접만들어서 할려고 했는데 이렇게 하면 Comment를 리턴하지못하고 int로 1 또는 -1 이런식으로만 리턴 될 수 있다.
}
