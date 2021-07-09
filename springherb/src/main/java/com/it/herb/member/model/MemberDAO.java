package com.it.herb.member.model;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberDAO {
	public int insertMember(MemberVO vo);
	public int checkDuplicate(String userid);
	String selectPwd(String userid);
	public MemberVO selectByUserid(String userid);
	public int updateMember(MemberVO vo);
	public int withdrawMember(String userid);
}
