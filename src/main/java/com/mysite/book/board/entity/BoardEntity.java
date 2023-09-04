package com.mysite.book.board.entity;

import com.mysite.book.board.dto.BoardDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "Cboard")
public class BoardEntity extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 20, nullable = false)
	private String writer;
	
	@Column
	private String boardPass;
	
	@Column
	private String title;
	
	@Column(length = 1500)
	private String content;
	
	@Column
	private int hits;

	// 게시글 작성 처리 (dto -> entity 로 변환)
	public static BoardEntity toSaveEntity(BoardDto boardDto) {
		
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setWriter(boardDto.getWriter());
		boardEntity.setBoardPass(boardDto.getBoardPass());
		boardEntity.setTitle(boardDto.getTitle());
		boardEntity.setContent(boardDto.getContent());
		boardEntity.setHits(0);
		
		return boardEntity;
		
	}

	// 수정 처리시 id값을 통해서 insert인지 수정인지 판단한다.
	public static BoardEntity toUpdateEntity(BoardDto boardDto) {
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setId(boardDto.getId());			// id값을 set해준다.
		boardEntity.setWriter(boardDto.getWriter());
		boardEntity.setBoardPass(boardDto.getBoardPass());
		boardEntity.setTitle(boardDto.getTitle());
		boardEntity.setContent(boardDto.getContent());
		boardEntity.setHits(boardDto.getHits());		// 조회수도 DB에서  가져온다.
		
		return boardEntity;
		
	}
	
	
	
}
