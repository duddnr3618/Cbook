package com.mysite.book.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.book.board.dto.BoardDto;
import com.mysite.book.board.entity.BoardEntity;
import com.mysite.book.board.repository.BoardRepository;

import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import java.util.Optional; // 올바른 Optional 

@Service
@RequiredArgsConstructor

public class BoardService {
	
	
	private final BoardRepository boardRepository;
	
	public void save(BoardDto boardDto) {
		BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
		boardRepository.save(boardEntity);
	}

	public List<BoardDto> findAll () {
		List<BoardEntity> boardEntityList = boardRepository.findAll();
		List<BoardDto> boardDtoList = new ArrayList<>();
		for (BoardEntity boardEntity : boardEntityList) {
			boardDtoList.add(BoardDto.toBoardDto(boardEntity));
		}
		return boardDtoList;
		
	}

	// 게시글 조회수 처리
	@Transactional
	public void updateHits(Long id) {
		boardRepository.updateHits(id);
		
	}

	// 게시글 상세보기
	  @Transactional
	  public BoardDto findById(Long id) {
	        Optional<BoardEntity> optionalboardEntity = boardRepository.findById(id);
	        if (optionalboardEntity.isPresent()) {
	        	BoardEntity boardEntity = optionalboardEntity.get();
	        	BoardDto boardDto = BoardDto.toBoardDto(boardEntity);
	            return boardDto;
	        }else {
	        return null;
	    }
	  }

	  // 게시글 수정 처리 
	public BoardDto update(BoardDto boardDto) {
		// dto -> entity 변환
		BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto) ;
		boardRepository.save(boardEntity);
		
		return findById(boardDto.getId());
	}
	    
	    
	
}
