package com.it.herb.admin.model;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ManagerVO {
	private int no;
    private String userid;    
    private String name;     
    private String pwd;       
    private String authCode;	
    private Timestamp regdate;
}
