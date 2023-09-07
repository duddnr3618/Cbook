package com.mysite.book.board.dto;

import java.time.LocalDateTime;

import com.mysite.book.board.entity.CommentEntity;

import lombok.Data;

@Data
public class CommentDto {

	private Long id;
	private String commentWriter;
	private String commentContent;
	private Long boardId;
	private LocalDateTime commentCreatedTime;
	
	
	public static CommentDto toCommentDto(CommentEntity commentEntity, Long boardId) {
		// TODO Auto-generated method stub
		CommentDto commentDto = new CommentDto () ;
		commentDto.setId(commentEntity.getId());
		commentDto.setCommentWriter(commentEntity.getCommentWriter());
		commentDto.setCommentContent(commentEntity.getCommentContent());
		commentDto.setCommentCreatedTime(commentEntity.getCreatedTime());
		commentDto.setBoardId(boardId);
		return commentDto;
	}

}
