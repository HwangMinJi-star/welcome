package com.it.herb.product.model;

import java.util.List;

import com.it.herb.eventproduct.model.EventProductVO;

public interface ProductService {
	List<ProductVO> selectProductByEvent(String eventName);
	ProductVO selectProductByNO(int productNo);
	List<ProductVO> selectProductByCategory(int categoryNo);
	int insertProduct(ProductVO vo);
	List<ProductVO> selectAll(EventProductVO searchVo);
	int selectTotalRecord(EventProductVO searchVo);
}
