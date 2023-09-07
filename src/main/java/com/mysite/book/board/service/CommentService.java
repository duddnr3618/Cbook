package com.mysite.book.board.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mysite.book.board.dto.CommentDto;
import com.mysite.book.board.entity.BoardEntity;
import com.mysite.book.board.entity.CommentEntity;
import com.mysite.book.board.repository.BoardRepository;
import com.mysite.book.board.repository.CommentRepository;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.dynamic.DynamicType.Builder.FieldDefinition.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
	
	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;

	 public Long save(CommentDto commentDto) {
	        /* 부모엔티티(BoardEntity) 조회 */
	        java.util.Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());
	        if (optionalBoardEntity.isPresent()) {
	            BoardEntity boardEntity = optionalBoardEntity.get();
	            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto, boardEntity);
	            return commentRepository.save(commentEntity).getId();
	        } else {
	            return null;
	        }
	    }

	public List<CommentDto> findAll(Long boardId) {
		// TODO Auto-generated method stub
		// select * from comment where board_id=? order by id desc;
		  BoardEntity boardEntity = boardRepository.findById(boardId).get();
	        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);
	        /* EntityList -> DtoList */
	        List<CommentDto> commentDtoList = new ArrayList<>();
	        for (CommentEntity commentEntity: commentEntityList) {
	            CommentDto commentDto = CommentDto.toCommentDto(commentEntity, boardId);
	            commentDtoList.add(commentDto);
	        }
	        return commentDtoList;
	}
}
