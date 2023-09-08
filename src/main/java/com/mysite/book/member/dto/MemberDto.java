package com.mysite.book.member.dto;

import com.mysite.book.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

	private Long id;
	private String memberEmail;
	private String memberPassword;
	private String memberName;
	private String memberMobile;
	
	
	
	//entity -> dto로 변환
	public static MemberDto toMemberDto(MemberEntity memberEntity) {
		// TODO Auto-generated method stub
		MemberDto memberDto = new MemberDto ();
		memberDto.setId(memberEntity.getId());
		memberDto.setMemberEmail(memberEntity.getMemberEmail());
		memberDto.setMemberPassword(memberEntity.getMemberPassword());
		memberDto.setMemberMobile(memberEntity.getMemberMobile());
		
		return memberDto;
	}
	
}
