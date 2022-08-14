package com.cos.photogramstart.service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.image.Image;
import com.cos.photogramstart.domain.image.ImageRepository;
import com.cos.photogramstart.web.dto.image.ImageUploadDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ImageService {
	
	private final ImageRepository imageRepository;
	
	@Transactional(readOnly=true) // select만할꺼니까 readOnly, 그리고 readOnly면 영속성 컨텍스트 변경감지를 해서, 더티체킹, flush(반영) X
	public Page<Image> 이미지스토리(int principalId, Pageable pageable){
		Page<Image> images = imageRepository.mStory(principalId,pageable);
		return images;
	}
	
	@Value("${file.path}") // application.yml 의 file.path
	private String uploadFolder;
	
	//사진업로드 함수 만들꺼임
	public void 사진업로드(ImageUploadDto imageUploadDto, PrincipalDetails principalDetails){
				
		UUID uuid = UUID.randomUUID(); // uuid 사용자가 동일 1.jpg라는 사진을 업로드할 경우 덮어씌어지므로
									   // uuid란? 네트워크 상에서 고유성이 보장되는 id를 만들기 위한 표준 규악.
		String imageFileName = uuid+"_"+imageUploadDto.getFile().getOriginalFilename(); // 이렇게하면 imageFileName에 실제 파일이름이 들어감
		
		System.out.println("이미지파일이름:"+imageFileName);
		
		Path imageFilePath = Paths.get(uploadFolder+imageFileName);
		
		// 통신 혹은 I/O 가 일어날 때 -> 예외가 발생 할 수 있다.
		try {
			Files.write(imageFilePath, imageUploadDto.getFile().getBytes()); //이미지가 업로드되다가 여기서 실패를 하면 익셉션 처리 해줘야함
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		//image 테이블에 저장
		Image image = imageUploadDto.toEntity(imageFileName,principalDetails.getUser());// imageFileName : 5cf513d-ct34-343b-e5235d_1.jpg이렇게만 넣을 건데
																						// 이러면 앞의 경로는 모르지않느냐? ㄴㄴ yml에 적혀있으니 문제될거없음
		Image imageEntity = imageRepository.save(image);
		
		System.out.println(imageEntity.toString());
				
	}
}
