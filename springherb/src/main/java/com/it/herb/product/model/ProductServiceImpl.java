package com.it.herb.product.model;

import java.util.List;

import org.springframework.stereotype.Service;

import com.it.herb.eventproduct.model.EventProductVO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
	private final ProductDAO productDao;

	@Override
	public List<ProductVO> selectProductByEvent(String eventName) {
		return productDao.selectProductByEvent(eventName);
	}

	@Override
	public ProductVO selectProductByNO(int productNo) {
		return productDao.selectProductByNO(productNo);
	}

	@Override
	public List<ProductVO> selectProductByCategory(int categoryNo) {
		return productDao.selectProductByCategory(categoryNo);
	}

	@Override
	public int insertProduct(ProductVO vo) {
		return productDao.insertProduct(vo);
	}

	@Override
	public List<ProductVO> selectAll(EventProductVO searchVo) {
		return productDao.selectAll(searchVo);
	}

	@Override
	public int selectTotalRecord(EventProductVO searchVo) {
		return productDao.selectTotalRecord(searchVo);
	}
	
}
