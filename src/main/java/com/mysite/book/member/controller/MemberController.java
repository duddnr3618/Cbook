package com.mysite.book.member.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mysite.book.member.dto.MemberDto;
import com.mysite.book.member.service.MemberService;

import ch.qos.logback.core.model.Model;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
	
	private final MemberService memberService;
	
	// 회원가입 폼
	@GetMapping("/save")
	public String saveForm () {
		return "member/save";
	}
	
	// 회원가입 처리
	@PostMapping("/save") 
		public String save (@ModelAttribute MemberDto memberDto) {
		System.out.println("회원가입 처리 : " + memberDto);
		memberService.save(memberDto);
		return "member/login";
	}
	
	// 로그인 폼
	@GetMapping("/login")
	public String loginForm () {
		
		return "member/login";
	}
	
	
	// 로그인 처리
	@PostMapping("/login")
	public String login (@ModelAttribute MemberDto memberDto , HttpSession session) {
		MemberDto loginResult = memberService.login(memberDto);
		if(loginResult != null) {
			// login 성공
			session.setAttribute("loginEmail", loginResult.getMemberEmail());
			System.out.println("로그인 성공");
			return "main";
		}else {
			// login 실패
			
			return "member/login";
		}
	}
	
	// 회원정보 수정
	@GetMapping("/update")
	public String updateForm (HttpSession session , org.springframework.ui.Model model) {
		String myEmail = (String) session.getAttribute("loginEmail");
	
		MemberDto memberDto = memberService.updateForm(myEmail);
		model.addAttribute("updateMember" , memberDto);
		return "member/update";
	}
	
	// 회원정보 수정 처리
	@PostMapping("/update")
	public String update (@ModelAttribute MemberDto memberDto) {
		memberService.update(memberDto);
		return "index";
		// return "redirect:/member/" + memberDto.getId();
	}
	
	// 회원정보 삭제
	@GetMapping("/delete/{id}")
	public String deleteById (@PathVariable Long id) {
		System.out.println("회원정보 삭제 요청");
		memberService.deleteById(id);
		System.out.println("회원정보 삭제 완료");

		return "index";
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout (HttpSession session) {
		session.invalidate();
		return "index";
	}
	
	// 이메일 중복체크
	@PostMapping("/email-check")
	public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail) {
		System.out.println("memberEmail" + memberEmail);
		String checkResult = memberService.emailCheck(memberEmail);
		if(checkResult !=null) {
			return "ok";
		}else {
			return "no";
		}
	}
	
	
}
