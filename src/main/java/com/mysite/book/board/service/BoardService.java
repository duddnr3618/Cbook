package com.mysite.book.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
	        if (optionalBoardEntity.isPresent()) {
	        	BoardEntity boardEntity = optionalBoardEntity.get();
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

	// 게시글 처리
	public void delete(Long id) {
		boardRepository.deleteById(id);
		
	}

	// 페이징 처리
	public Page<BoardDto> paging(Pageable pageable) {
		// TODO Auto-generated method stub
		int page = pageable.getPageNumber() -1;		// page 위치에 있는 값은 0부터 시작
		int pageLimit = 3;			// 한 페이지에 보여줄 글 갯수
		
		// 한 페이지당 3개씩 보여주고 정렬기준은 id기준으로 내림차순 정렬
		Page<BoardEntity> boardEntities =
		boardRepository.findAll(PageRequest.of(page, pageLimit , Sort.by(Sort.Direction.DESC, "id")));
		
		System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        // Page의 map 객체를 통해서 board의 엔티티를 dto객체로 변환
        Page<BoardDto> boardDto = boardEntities.map(board ->
        	new BoardDto(board.getId(), board.getWriter(), board.getTitle() , board.getHits() ,
        			board.getCreatedTime()));
        
		return boardDto;
	}
	    
	    
	
}
