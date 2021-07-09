package com.it.herb.admin.model;

import java.util.List;
import java.util.Map;

public interface ManagerService {
	List<Map<String, Object>> selectAuthority();
	int insertManager(ManagerVO vo);
	int checkDuplicate(String userid);
	int loginProc(String userid, String pwd);
}
