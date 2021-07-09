package com.it.herb.admin.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ManagerDAO {
	List<Map<String, Object>> selectAuthority();
	int insertManager(ManagerVO vo);
	int selectDup(String userid);
	String selectPwd(String userid);
}
