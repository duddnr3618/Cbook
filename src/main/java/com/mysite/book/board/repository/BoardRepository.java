package com.mysite.book.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mysite.book.board.entity.BoardEntity;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity, Long>{

	// 게시글 조회수 
	@Modifying
	@Query(value = "update BoardEntity b set b.hits=b.hits+1 where b.id=:id")
	void updateHits(@Param("id") Long id);
	
}
