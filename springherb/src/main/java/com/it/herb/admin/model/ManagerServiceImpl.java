package com.it.herb.admin.model;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.it.herb.member.model.MemberService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ManagerServiceImpl implements ManagerService{
	private final ManagerDAO managerDao;
	
	@Override
	public List<Map<String, Object>> selectAuthority() {
		return managerDao.selectAuthority();
	}

	@Override
	public int insertManager(ManagerVO vo) {
		return managerDao.insertManager(vo);
	}

	@Override
	public int checkDuplicate(String userid) {
		int result=0;
		int count=managerDao.selectDup(userid);
		if(count>0) { //이미 존재
			result=MemberService.UNUSABLE_ID;
		}else { //사용 가능
			result=MemberService.USABLE_ID;
		}
		return result;
	}

	@Override
	public int loginProc(String userid, String pwd) {
		int result=0;
		String dbPwd=managerDao.selectPwd(userid);
		if(dbPwd==null || dbPwd.isEmpty()) {
			result=MemberService.ID_NONE;
		}else {
			if(dbPwd.equals(pwd)) {
				result=MemberService.LOGIN_OK;
			}else {
				result=MemberService.PWD_DISAGREE;
			}
		}
		return result;
	}
	
}
