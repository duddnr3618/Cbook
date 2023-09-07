package com.mysite.book.board.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.book.board.dto.CommentDto;
import com.mysite.book.board.service.CommentService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
	
	private final CommentService commentService;
	
	@PostMapping("/save")
	public ResponseEntity save(@ModelAttribute CommentDto commentDto) {
		System.out.println("commentDto = " + commentDto);
		Long saveResult =  commentService.save(commentDto);
		if(saveResult != null) {
			// 작성 성공시 댓글 목록을 가져와서 리턴
			List<CommentDto>commentDtoList =  commentService.findAll(commentDto.getBoardId());
			return new ResponseEntity<>(commentDtoList, HttpStatus.OK);
		}else {
			return new ResponseEntity<>("해당게시글이 존재 하지 않습니다." , HttpStatus.NOT_FOUND);
		}
	
	}
	

}
