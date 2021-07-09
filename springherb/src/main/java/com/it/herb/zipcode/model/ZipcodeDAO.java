package com.it.herb.zipcode.model;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ZipcodeDAO {
	public List<ZipcodeVO> selectZipcode(String dong);
}
