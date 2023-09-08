package com.mysite.book.member.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mysite.book.member.entity.MemberEntity;

public interface MemberRepository extends JpaRepository<MemberEntity, Long>{
	
	// 이메일로 회원정보 조회
	Optional<MemberEntity> findByMemberEmail(String memberEmail);
	
}
