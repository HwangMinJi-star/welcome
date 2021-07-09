package com.it.herb.member.model;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService{
	private final MemberDAO memberDao;
	
	public int insertMember(MemberVO vo){
		int cnt=memberDao.insertMember(vo);
		return cnt;
	}
	
	public int checkDuplicate(String userid){
		int result=0;
		
		int count=memberDao.checkDuplicate(userid);
		if(count>0) {
			result=UNUSABLE_ID;
		}else {
			result=USABLE_ID;
		}
		return result;
	}

	@Override
	public int loginProc(String userid, String pwd) {
		String dbPwd=memberDao.selectPwd(userid);
		int result=0;
		if(dbPwd==null || dbPwd.isEmpty()) {
			result=ID_NONE;
		}else {
			if(dbPwd.equals(pwd)) {
				result=LOGIN_OK;
			}else {
				result=PWD_DISAGREE;
			}
		}
		return result;
	}

	@Override
	public MemberVO selectByUserid(String userid) {
		return memberDao.selectByUserid(userid);
	}

	@Override
	public int updateMember(MemberVO vo) {
		return memberDao.updateMember(vo);
	}

	@Override
	public int withdrawMember(String userid) {
		return memberDao.withdrawMember(userid);
	}

}
