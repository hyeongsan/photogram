package com.cos.photogramstart.domain.user;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

import com.cos.photogramstart.domain.image.Image;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//JPA - Java Persistence API (자바로 데이터를 영구적으로 저장(DB) 할 수 있는 API를 제공)

@Data
@Builder
@AllArgsConstructor // 모~든 필드 값을 파라미터로 받는 생성자를 만들어준다.
@NoArgsConstructor // 파라미터가 없는 기본 생성자를 생성한다.
@Entity // JPA가 관리하는 클래스, 해당클래스와 테이블이 매핑
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // 번호증가전략이 데이터베이스를 따라간다.
	private int id;
	
	@Column(length=20, unique = true)
	private String username;
	@Column(nullable = false)
	private String password;
	@Column(nullable = false)
	private String name;
	private String website; //웹사이트
	private String bio; // 자기소개
	@Column(nullable = false)
	private String email;
	private String phone;
	private String gender;
	
	private String profileImageUrl; // 사진
	private String role; // 권한
	
	// 나는 연관관계의 주인이아니다. 그러므로 테이블에 컬럼을 만들지마.
	// User를 select할 떄 해당 User id로 등록된 image들을 다 가져와
	// Lazy = User를 select할 때, 해당 User id로 등록된 image들을 가져오지마 => 대신 getImages()함수의 image들이 호출될 때 가져와.
	// Eager = User를 select할 때, 해당 User id로 등록된 image들을 전부 Join해서 가져와
	@OneToMany(mappedBy="user",fetch=FetchType.LAZY)
	@JsonIgnoreProperties({"user"})//JSON으로 파싱해서 응답할 때의 messageConverter인데 images내부에 있는 user는 하지마
	private List<Image> images;//양방향 매핑
	
	private LocalDateTime createDate; //JPA는 항상 이게 필요함 = 이 데이터가 언제 들어왔는지 파악
	
	/*위의 field값들은 우리가 집어넣어서 db에 입력할게 아니라 자동으로 들어가지게 할 것임*/
	/*그래서 이렇게 할것임*/
	@PrePersist //=> 이 함수는 데이터베이스에 값이 insert되기 직전에 실행하겠다.
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}
	
	@Override
	public String toString() {
		return "User [id=" + id + ", username="
				+ username + ", password=" + password + ", name="
				+ name + ", website=" + website + ", bio="
				+ bio + ", email=" + email + ", phone=" +phone +", gender="+ gender+", profileImageUrl="
				+ profileImageUrl
				+ ", role="+ role+ "]";
	}
}
