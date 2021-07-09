package com.it.herb.member.model;

public interface MemberService {
	//아이디 중복확인에 사용하는 상수
	public static final int USABLE_ID=1; //사용가능한 아이디
	public static final int UNUSABLE_ID=2; //이미 존재하는 아이디-사용불가

	//로그인 처리에 사용하는 상수
	int LOGIN_OK=1;  //로그인 성공
	int PWD_DISAGREE=2; //비밀번호 불일치
	int ID_NONE=3; //아이디 존재하지 않음
	
	public int insertMember(MemberVO vo);
	public int checkDuplicate(String userid);
	public int loginProc(String userid, String pwd);
	public MemberVO selectByUserid(String userid);
	public int updateMember(MemberVO vo);
	public int withdrawMember(String userid);
}
