package com.mysite.book.board.dto;

import java.time.LocalDateTime;

import com.mysite.book.board.entity.BoardEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoardDto {
	
	private Long id;
	private String writer;
	private String boardPass;
	private String title;
	private String content;
	private int hits;
	private LocalDateTime boardCreatedTime;
	private LocalDateTime boardUpdateTime;
	
	
	public static BoardDto  toBoardDto (BoardEntity boardEntity) {
		BoardDto boardDto = new BoardDto ();
		boardDto.setId(boardEntity.getId());
		boardDto.setWriter(boardEntity.getWriter());
		boardDto.setBoardPass(boardEntity.getBoardPass());
		boardDto.setTitle(boardEntity.getTitle());
		boardDto.setContent(boardEntity.getContent());
		boardDto.setHits(boardEntity.getHits());
		boardDto.setBoardCreatedTime(boardEntity.getCreatedTime());
		boardDto.setBoardUpdateTime(boardEntity.getUpdatedTime());
		
		return boardDto;
	
	}

}
