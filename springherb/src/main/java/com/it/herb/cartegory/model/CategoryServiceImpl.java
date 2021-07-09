package com.it.herb.cartegory.model;

import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService{
	private final CategoryDAO categoryDao;
	
	public List<CategoryVO> selectCategory() {
		return categoryDao.selectCategory();
				
	}

}
