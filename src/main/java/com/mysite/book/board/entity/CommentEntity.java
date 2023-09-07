package com.mysite.book.board.entity;

import com.mysite.book.board.dto.CommentDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name="comment")
public class CommentEntity extends BaseEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30 , nullable = false)
	private String commentWriter;
	
	@Column
	private String commentContent;
	
	/*  Board : Comment = 1 : N  */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "board_id")
	private BoardEntity boardEntity;

	public static CommentEntity toSaveEntity(CommentDto commentDto , BoardEntity boardEntity) {
		// TODO Auto-generated method stub
		CommentEntity commentEntity = new CommentEntity();
		commentEntity.setCommentWriter(commentDto.getCommentWriter());
		commentEntity.setCommentContent(commentDto.getCommentContent());
		commentEntity.setBoardEntity(boardEntity);
		return commentEntity;
	}
	
}
