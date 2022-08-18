package com.cos.photogramstart.domain.comment;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;

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
public class Comment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int id;
	
	@Column(length = 100, nullable = false) // 제약조건
	private String content;
	
	@JsonIgnoreProperties("images")
	@JoinColumn(name="userId") // DB에 만들어질 컬럼명 , 이렇게 해놓으면 user객체의 PK키인 Id가 userId라는 컬럼명으로 DB에 저장되는 듯
	@ManyToOne(fetch = FetchType.EAGER) // ManyToOne은 기본전략 EAGER
	private User user; // 한명의 유저는 댓글을 여러개
	
	@JoinColumn(name="imageId")
	@ManyToOne(fetch = FetchType.EAGER)
	private Image image; // 하나의 이미지는 댓글을 여러개
	
	private LocalDateTime createDate; // 시간
	
	@PrePersist //DB에 insert되기 직전에 실행
	public void createDate() {
		this.createDate = LocalDateTime.now();
	}

}
