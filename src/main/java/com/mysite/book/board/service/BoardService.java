package com.mysite.book.board.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.mysite.book.board.dto.BoardDto;
import com.mysite.book.board.entity.BoardEntity;
import com.mysite.book.board.entity.BoardFileEntity;
import com.mysite.book.board.repository.BoardFileRepository;
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
	private final BoardFileRepository boardFileRepository;
	
	// 기존 게시글 작성 폼
//	public void save(BoardDto boardDto) {
//		BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
//		boardRepository.save(boardEntity);
//	}
	
	// 게시글 작성 -> 파일 첨부 여부에 따라 로직을 분리해야한다.
	public void save(BoardDto boardDto) throws  Exception {	
		if(boardDto.getBoardFile().isEmpty()) {
			// 첨부 파일 없는 경우
			BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDto);
			boardRepository.save(boardEntity);
		}else {
			//첨부 파일이 있는 경우
			// 6. DB의 테이블에서 해당 데이터 save 처리 (게시글 정보) -> boardEntity로 변환 -> 파일이 여러개인 경우
			// -> 부모데이터가 먼저 저장되어야 한다.
			BoardEntity boardEntity = BoardEntity.toSaveFileEntity(boardDto);
			Long saveId = boardRepository.save(boardEntity).getId();
			BoardEntity board = boardRepository.findById(saveId).get();		// DB에서 id값을 가져온다
			for(MultipartFile boardFile : boardDto.getBoardFile()) {	
			// 1. Dto에 담긴 파일을 꺼낸다.
			//MultipartFile boardFile = boardDto.getBoardFile();	-> 파일이 여러개인경우 반복문을 통해서 파일을 꺼내기 때문에 필요없어진다.
			// 2. 파일의 이름을 가져온다.
			String originalFileName = boardFile.getOriginalFilename();
			// 3. 서버 저장용 이름을 만든다. (UUID or System.currentTimeMillis() )
			String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
			// 4. 저장 경로 설정
			String savePath = "C:/springboot_img/" + storedFileName;
			// 5. 해당 경로에 파일 저장 처리를 한다.
			boardFile.transferTo(new File(savePath));
			
			// 7. DB의 file테이블의 해당 데이터 save 처리(해당 파일정보)
			BoardFileEntity boardFileEntity = BoardFileEntity.toBoardFileEntity(board, originalFileName,
						storedFileName);
			boardFileRepository.save(boardFileEntity);
		}
		}
		
	}
	
	

	@Transactional
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
	  
// 특정 게시글 불러오기
	  public BoardEntity boardView(Long id) {
		  
		  return boardRepository.findById(id).get();
		  
	  }

	  
	  // 게시글 수정 처리 
	public BoardDto update(BoardDto boardDto) {
		// dto -> entity 변환
		BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDto) ;
		boardRepository.save(boardEntity);
		
		return findById(boardDto.getId());
	}

	// 게시글 삭제처리
	public void delete(Long id) {
		boardRepository.deleteById(id);
		
	}

	// 페이징 처리
	public Page<BoardDto> paging(Pageable pageable) {
		// TODO Auto-generated method stub
		int page = pageable.getPageNumber() -1;		// page 위치에 있는 값은 0부터 시작
		int pageLimit = 3;			// 한 페이지에 보여줄 글 갯수
		
		// 한 페이지당 3개씩 보여주고 정렬기준은 id기준으로 내림차순 정렬
		
		
		System.out.println("페이징 1");
		Page<BoardEntity> boardEntities =
		boardRepository.findAll(PageRequest.of(page, pageLimit , Sort.by(Sort.Direction.DESC, "id")));
		
		System.out.println("페이징 2");
		
		
		
		
		/*
		System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

*/ 
        // Page의 map 객체를 통해서 board의 엔티티를 dto객체로 변환
        Page<BoardDto> boardDto = boardEntities.map(board ->
        	new BoardDto(board.getId(), board.getWriter(), board.getTitle() , board.getHits() ,
        			board.getCreatedTime()));
        
		return boardDto;
	}
	    
	    
	
}
