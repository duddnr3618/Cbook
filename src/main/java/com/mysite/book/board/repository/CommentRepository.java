package com.mysite.book.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.book.board.entity.BoardEntity;
import com.mysite.book.board.entity.CommentEntity;

@Repository
public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
	// select * from comment where board_id=? order by id desc;
	 List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
}
