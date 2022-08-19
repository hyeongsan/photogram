package com.cos.photogramstart.web.api;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.photogramstart.config.auth.PrincipalDetails;
import com.cos.photogramstart.domain.comment.Comment;
import com.cos.photogramstart.handler.ex.CustomValidationApiException;
import com.cos.photogramstart.service.CommentService;
import com.cos.photogramstart.web.dto.CMRespDto;
import com.cos.photogramstart.web.dto.comment.CommentDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class CommentApiController {

	private final CommentService commentService;
	
	@PostMapping("/api/comment")
	public ResponseEntity<?> commentSave(@Valid @RequestBody CommentDto commentDto,BindingResult bindingResult,@AuthenticationPrincipal PrincipalDetails principalDetails){ // 그냥 CommentDto로 하면 x-www-~ key/value로만 받기에, json으로 받으려면 @RequestBody필요함 
		
		if(bindingResult.hasErrors()) { // bindingResult에 에러가 있다는건
					
			Map<String,String> errorMap = new HashMap<>();
			
			for(FieldError error : bindingResult.getFieldErrors()) { //getFieldErrors()는 list를 리턴				
				errorMap.put(error.getField(), error.getDefaultMessage());
				System.out.println("here"+error.getDefaultMessage());
			}			
			//return "오류남";
			throw new CustomValidationApiException("유효성검사실패함",errorMap);
		}
		
		Comment comment = commentService.댓글쓰기(commentDto.getContent(), commentDto.getImageId(),principalDetails.getUser().getId()); // content, imageId , userId를 날리면된다
		return new ResponseEntity<>(new CMRespDto<>(1,"댓글쓰기성공",comment),HttpStatus.CREATED);
	}
	
	@DeleteMapping("/api/comment/{id}")
	public ResponseEntity<?> commentDelete(@PathVariable int id){
		commentService.댓글삭제(id);
		return new ResponseEntity<>(new CMRespDto<>(1,"댓글삭제성공",null),HttpStatus.OK);
	}
}
