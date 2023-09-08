package com.mysite.book.board.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysite.book.board.dto.BoardDto;
import com.mysite.book.board.entity.BoardEntity;
import com.mysite.book.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor	//생성자 주입을 통한 자동으로 생성자가 생성
public class BoardController {
	@Autowired
	private final BoardService boardService;
	
	@GetMapping("/")
	public String index() {
		return "index";
	}
	
	// 게시글 작성폼 
	@GetMapping("/board/write")
	public String boardWriteForm () {
		return "board/write";
	}
	
	// 게시글 작성 처리 
	@PostMapping("/board/save")
	public String save(@ModelAttribute BoardDto boardDto) throws Exception {
		System.out.println("boardDto = " + boardDto);
		boardService.save(boardDto);
		return "/";
	}
	
//	// 게시글 리스트
//	@GetMapping("/board/list")
//	public String findAll(Model model) {
//		List<BoardDto>boardDtoList =  boardService.findAll();
//		model.addAttribute("boardList" , boardDtoList);
//		return "/board/list";
//		
//	}
	
	// 게시글 리스트
	// 페이지 요청 처리 : /board/paging?=1
	@GetMapping("/board/list")
	public String paging (@PageableDefault(page=1) Pageable pageable , Model model) {
		
		System.out.println(">>>>>: 1");
		pageable.getPageNumber();
		System.out.println(">>>>>: 2");
	    Page<BoardDto> boardList = boardService.paging(pageable);
		System.out.println(">>>>>: 3");
		// 보여지는 페이지 갯수 처리 (3개)
		int blockLimit = 3;
		int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
	    System.out.println(">>>>>: 4");
	    
	     model.addAttribute("boardList", boardList);
	     model.addAttribute("startPage", startPage);
	     model.addAttribute("endPage", endPage);
	     System.out.println(">>>>>: 5");
	     return "board/list";	
	}

	// 게시글 상세페이지 및 조회수
	@GetMapping("/board/{id}")
    public String findByid (@PathVariable Long id , Model model) {
        boardService.updateHits(id);
        // boardDto 객체에 boardService의 BoardDetail을 가져온다.
        BoardDto boardDto = boardService.findById(id);
        
        System.out.println(">>>>>>>>>>" +boardDto.getStoredFileName());

        // 가져온 객체를 board라는 파라미터에 담는다.
        model.addAttribute("board" , boardDto);
        return "board/detail"; 
    }
	
	// 게시글 수정 폼
	@GetMapping("/board/update/{id}")
	public String updateForm (@PathVariable Long id , Model model) {
		BoardDto boardDto = boardService.findById(id);
		model.addAttribute("boardUpdate" , boardDto);
		System.out.println("수정폼 완료");

		return "/board/update";
		
	}
	
	
//	// 게시글 수정 처리
//	@PostMapping("/board/update")
//	public String update (@ModelAttribute BoardDto boardDto , Model model) {
//		// update 메소드 호출
//		BoardDto board = boardService.update(boardDto);
//		System.out.println("수정 처리 완료1" + board);
//		model.addAttribute("board", board);
//
//		System.out.println("수정 처리 완료2");
//		return "board/detail";
//	}
//	
	// 게시글 수정 처리
	@PostMapping("/board/update/{id}")
	public String update (@PathVariable("id") Long id , BoardEntity board) {
		BoardEntity boardTemp = boardService.boardView(id);
		boardTemp.setWriter(board.getWriter());
		boardTemp.setTitle(board.getTitle());
		boardTemp.setContent(board.getContent());
		
		return "board/list";
	}
	

	// 게시글 삭제 처리
	@GetMapping("/board/delete/{id}")
	public String delete(@PathVariable Long id) {
		boardService.delete(id);
		return "redirect:/board/list";
		
	}
	

	
}
