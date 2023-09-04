package com.mysite.book.board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.mysite.book.board.dto.BoardDto;
import com.mysite.book.board.service.BoardService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor	//생성자 주입을 통한 자동으로 생성자가 생성
public class BoardController {
	
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
	public String boardWritepro(@ModelAttribute BoardDto boardDto) {
		boardService.save(boardDto);
		
		return "index";
	}
	
	// 게시글 리스트
	@GetMapping("/board/list")
	public String findAll(Model model) {
		List<BoardDto>boardDtoList =  boardService.findAll();
		model.addAttribute("boardList" , boardDtoList);
		return "/board/list";
		
	}
	
	// 게시글 상세페이지 및 조회수
	@GetMapping("/board/{id}")
    public String findByid (@PathVariable Long id , Model model) {
        boardService.updateHits(id);
        // boardDto 객체에 boardService의 BoardDetail을 가져온다.
        BoardDto boardDto = boardService.findById(id);
        // 가져온 객체를 board라는 파라미터에 담는다.
        model.addAttribute("board" , boardDto);
        return "board/detail"; 
    }
	
	// 게시글 수정 폼
	@GetMapping("/board/update/{id}")
	public String updateForm (@PathVariable Long id , Model model) {
		BoardDto boardDto = boardService.findById(id);
		model.addAttribute("boardUpdate" , boardDto);
		
		return "/board/update";
		
	}
	
	//게시글 수정 처리
	@PostMapping("/board/update")
	public String update (@ModelAttribute BoardDto boardDto , Model model) {
		BoardDto board = boardService.update(boardDto);
		model.addAttribute("board" , board) ;
		return "/board/detail";
	}

	

}
