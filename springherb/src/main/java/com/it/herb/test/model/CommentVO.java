package com.it.herb.test.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CommentVO {
	private int commentNo; 
	private String userId;                
    private Timestamp regDate;
    private String commentContent;    
}
