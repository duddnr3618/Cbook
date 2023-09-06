package com.mysite.book.board.entity;

import java.util.ArrayList;
import java.util.List;

import com.mysite.book.board.dto.BoardDto;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "board")
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
	
	@Column
	private int fileAttached;			// 1 or 0
	
	@OneToMany(mappedBy = "boardEntity" , cascade = CascadeType.REMOVE , 
			orphanRemoval = true , fetch = FetchType.LAZY)
	private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();
	
	
	// 게시글 작성 처리 (dto -> entity 로 변환) -> 파일이 없는경우
	public static BoardEntity toSaveEntity(BoardDto boardDto) {
		
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setWriter(boardDto.getWriter());
		boardEntity.setBoardPass(boardDto.getBoardPass());
		boardEntity.setTitle(boardDto.getTitle());
		boardEntity.setContent(boardDto.getContent());
		boardEntity.setHits(0);
		boardEntity.setFileAttached(0); 		// 파일 없음
		
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

	//게시글 작성 처리 (dto -> entity 로 변환) -> 파일이 있는경우
	public static BoardEntity toSaveFileEntity(BoardDto boardDto) {
		// TODO Auto-generated method stub
		BoardEntity boardEntity = new BoardEntity();
		boardEntity.setWriter(boardDto.getWriter());
		boardEntity.setBoardPass(boardDto.getBoardPass());
		boardEntity.setTitle(boardDto.getTitle());
		boardEntity.setContent(boardDto.getContent());
		boardEntity.setHits(0);
		boardEntity.setFileAttached(1); 		// 파일 있음
		
		return boardEntity;
	
	}
	

	
}
