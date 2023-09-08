package com.mysite.book.member.entity;



import com.mysite.book.member.constant.Role;
import com.mysite.book.member.dto.MemberDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "member")
public class MemberEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true)
	private String memberEmail;
	
	@Column
	private String memberPassword;
	
	@Column
	private String memberName;
	
	@Column
	private String memberMobile;
	
	@Enumerated(EnumType.STRING)
	private Role role;

	// dto -> entity 변환
	public static MemberEntity toMemberEntity(MemberDto memberDto) {
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setMemberEmail(memberDto.getMemberEmail());
		// 비밀번호 암호화 하여 DB에 저장
		//String password = passwordEncoder.encode(memberDto.getMemberPassword());
		memberEntity.setMemberPassword(memberDto.getMemberPassword());
		memberEntity.setMemberName(memberDto.getMemberName());
		memberEntity.setMemberMobile(memberDto.getMemberMobile());
		memberEntity.setRole(Role.USER);
		
		return memberEntity;
	}
	
	public static MemberEntity toUpdateMemberEntity(MemberDto memberDto) {
		// TODO Auto-generated method stub
		MemberEntity memberEntity = new MemberEntity();
		memberEntity.setId(memberDto.getId());
		memberEntity.setMemberEmail(memberDto.getMemberEmail());
		memberEntity.setMemberPassword(memberDto.getMemberPassword());
		memberEntity.setMemberName(memberDto.getMemberName());
		memberEntity.setMemberMobile(memberDto.getMemberMobile());
		
		return memberEntity;
	}
	


}
