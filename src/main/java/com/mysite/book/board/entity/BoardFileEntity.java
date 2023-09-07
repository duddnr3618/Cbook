package com.mysite.book.board.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="board_file_table")
public class BoardFileEntity extends BaseEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column
	private String originalFileName ;
	
	@Column
	private String storedFileName;
	
	// board와 연관관계 설정 board  : file = 1 : N
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private BoardEntity boardEntity;
	
	
	// boardFileEntity로 변환
	public static BoardFileEntity toBoardFileEntity (BoardEntity boardEntity , 
			String originalFileName , String storedFileName) {
		BoardFileEntity boardFileEntity = new BoardFileEntity ();
		boardFileEntity.setOriginalFileName(originalFileName);
		boardFileEntity.setStoredFileName(storedFileName);
		boardFileEntity.setBoardEntity(boardEntity);
		return boardFileEntity;
		
	}
	
	@Override
	public String toString() {
	    return "BoardFileEntity{" +
	           "id=" + id +
	           ", originalFileName='" + originalFileName + '\'' +
	           ", storedFileName='" + storedFileName + '\'' +
	           '}';
	}


}
