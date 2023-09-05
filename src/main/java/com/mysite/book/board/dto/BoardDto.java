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
	
	// 페이징 처리를 위한 entity를 dto객체로 변환
	public BoardDto(Long id, String writer, String title, int hits, LocalDateTime boardCreatedTime) {
		super();
		this.id = id;
		this.writer = writer;
		this.title = title;
		this.hits = hits;
		this.boardCreatedTime = boardCreatedTime;
	}
	
	
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
