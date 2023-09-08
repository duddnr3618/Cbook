package com.mysite.book.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mysite.book.member.dto.MemberDto;
import com.mysite.book.member.entity.MemberEntity;
import com.mysite.book.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {
	private final MemberRepository memberRepository;

	public void save(MemberDto memberDto) {
		// TODO Auto-generated method stub
		MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDto);
		memberRepository.save(memberEntity);
		
		
	}

	public MemberDto login(MemberDto memberDto) {
		// TODO Auto-generated method stub
		// 입력한 이멜일로 DB에서 조회
		Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberDto.getMemberEmail());
		if(byMemberEmail.isPresent()) {
			// 조회 결과가 있을시(회원정보가 존재)
			MemberEntity memberEntity = byMemberEmail.get();
			if(memberEntity.getMemberPassword().equals(memberDto.getMemberPassword())) {
				// 비밀번호가 일치
				// entity -> dto 변환후 리턴
				MemberDto dto = MemberDto.toMemberDto(memberEntity);
				return dto;
			}else {
				//비밀번호가 일치x
				return null;
			}
			
		}else {
			//조회결과가 없을시(회원정보가 존재x)
		
			return null;
		}
		// DB에서 조회한 비밀번화와 사용자가 입력한 비밀번호가 일치하는지 판단
		
	}

	public MemberDto updateForm(String myEmail) {
		// TODO Auto-generated method stub
		Optional<MemberEntity>optionalMemberEntity= memberRepository.findByMemberEmail(myEmail);
		if(optionalMemberEntity.isPresent()) {
			return MemberDto.toMemberDto(optionalMemberEntity.get());
		}else {
			return null;
		}
		
	}

	// 회원정보 수정
	public void update(MemberDto memberDto) {
		// TODO Auto-generated method stub
		// dto -> entity변환 
		memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDto));
		
	}

	//회원정보 삭제
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		memberRepository.deleteById(id);
		
	}

	public String emailCheck(String memberEmail) {
		// TODO Auto-generated method stub
		Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
		if(byMemberEmail.isPresent()) {
			// 조회결과 있을시 해당 이메일 사용할수 없다.
			return null;
		}else {
			//조회결과가 없을시 해당 이메일을 사용가능
			return "ok";
		}
	
	}

}
