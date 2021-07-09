package com.it.herb.reboard.model;

import java.util.List;
import java.util.Map;

import com.it.herb.common.SearchVO;

public interface ReBoardService {
	public int insertReBoard(ReBoardVO vo);
	public List<ReBoardVO> selectAll(SearchVO searchVo);
	int selectTotalRecord(SearchVO searchVo);
	public int updateReadCount(int no);
	public ReBoardVO selectByNo(int no);
	public boolean checkPwd(int no, String pwd);
	public int updateReBoard(ReBoardVO vo);
	public void deleteReBoard(Map<String, String> map);
	public int updateDownCount(int no);
	public int reply(ReBoardVO vo);
	
	/*
	public List<ReBoardVO> selectMainNotice();
	*/
}
