package com.cos.photogramstart.web.dto.subscribe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class SubscribeDto {
	private int id; // 쌀이 로그인했는데, 모달에 love가 떠있다. 그 love의 아이디
	private String username; // love
	private String profileImageUrl; // 프로필사진
	private Integer subscribeState; // 구독상태
	private Integer equalUserState; // 지금 모달에 뜬 사람이 로그인한 사람과 동일인물인지 아닌지.
}
