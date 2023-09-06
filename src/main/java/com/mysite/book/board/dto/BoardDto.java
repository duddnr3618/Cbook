package com.mysite.book.board.dto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.mysite.book.board.entity.BoardEntity;
import com.mysite.book.board.entity.BoardFileEntity;

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
	
	// 파일을 요청시에 필요한 객체 -> write.html -> controller 파일 담는 용도 -> 파일이 여러개일경우 List로 받아준다.
	private List<MultipartFile> boardFile;
	private List<String> originalFileName;		//원본 파일 이름
	private List<String> storedFileName;	//서버 저장용 파일이름
	private int fileAttached;					//파일 첨부 여부 (첨부 1 , 미첨부 0)
	
	
	// 페이징 처리를 위한 entity를 dto객체로 변환
	public BoardDto(Long id, String writer, String title, int hits, LocalDateTime boardCreatedTime) {
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
		
		if(boardEntity.getFileAttached() == 0) {
			boardDto.setFileAttached(boardEntity.getFileAttached());		// 0
		}else {
			List<String> originalFileNameList = new ArrayList<>();
			List<String> storedFileNameList = new ArrayList<>();
			boardDto.setFileAttached(boardEntity.getFileAttached());		// 1
			for(BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()) {
			//파일 이름을 가져감 (파일이 한개일 경우)
			// originalFileName , storedFileName : board_file_table(BoardFileEntity)
			// join : select * from board_table b , board_file_table bf where b.id=bf.board_id and where b.id=?
			// 부모객체를 통해서 자식객체로 접근이 가능하다.(파일 하나)
			//boardDto.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
			//boardDto.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
			
			// 파일이 여러개일 경우
			originalFileNameList.add(boardFileEntity.getOriginalFileName());
			storedFileNameList.add(boardFileEntity.getStoredFileName());	
			}
			boardDto.setOriginalFileName(originalFileNameList);
			boardDto.setStoredFileName(storedFileNameList);
			
			
		}
		
		return boardDto;
	
	}




}
