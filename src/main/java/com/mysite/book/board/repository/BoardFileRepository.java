package com.mysite.book.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysite.book.board.entity.BoardFileEntity;

@Repository
public interface BoardFileRepository extends JpaRepository<BoardFileEntity , Long> {

}
