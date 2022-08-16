package com.cos.photogramstart.domain.likes;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor 
@NoArgsConstructor 
@Entity 
@Table(uniqueConstraints= {
		@UniqueConstraint(
				name="likes_uk",
				columnNames= {"imageId","userId"}//ssar이라는애가 1.jpg를 여러번 좋아할 수 없으니, 중복유니크 키로 묶은 것이다.
				
				)
})
public class Likes { // N(이미지와는), N(user와는)- 한명의유저는 좋아요를 여러번가능
		
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@JoinColumn(name="imageId") // 데이터베이스에 어떤이름으로 만들어질것인지
	@ManyToOne // ManyToOne은 기본페치전략이 Eager전략이다. ( likes테이블 생성시 image,user join해서 가져옴)
	private Image image; // 어떤이미지를, 1
	
	@JsonIgnoreProperties({"images"})
	@JoinColumn(name="userId") // 데이터베이스에 어떤이름으로 만들어질것인지
	@ManyToOne
	private User user;	 // 누가 좋아했는지 필요, 1
	
	private LocalDateTime createDate; // 시간
	
	@PrePersist //DB에 insert되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
}
















