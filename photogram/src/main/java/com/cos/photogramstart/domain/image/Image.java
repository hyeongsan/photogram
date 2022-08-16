package com.cos.photogramstart.domain.image;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Transient;

import com.cos.photogramstart.domain.likes.Likes;
import com.cos.photogramstart.domain.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Image {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	private String caption; // 사진 설명임 !!
	private String profileImageUrl; // 사진을 전송받아서 그 사진을 서버에 특정 폴더에 저장 -> DB에 그 저장된 경로를 insert할 것임.
	
	@JsonIgnoreProperties({"images"}) 
	@JoinColumn(name="userId")
	@ManyToOne // 이미지 n : 유저 1
	private User user; // 한명의 유저는 여러 이미지를 등록할 수 있다. 유저1 : 이미지n (유저입장 )
					   // 하나의 이미지는 몇명의 유저가 만들어 낼 수 있는가. 이미지1 : 유저 1 (이미지입장)
	
	//이미지 좋아요	
	@JsonIgnore
	@OneToMany(mappedBy="image")
	private List<Likes> likes;	
	
	//댓글	
	private LocalDateTime createDate; // 시간
	
	@Transient // DB에 컬럼이 만들어지지 않는다.
	private boolean likeState;
	
	@PrePersist //DB에 insert되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		return  "Image [id="+id+", caption="+caption+",profileImageUrl="+profileImageUrl+",user="+user+",createDate="+createDate+"]";
	}
}
