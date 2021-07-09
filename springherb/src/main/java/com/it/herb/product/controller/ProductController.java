package com.it.herb.product.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.herb.product.model.ProductService;
import com.it.herb.product.model.ProductVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/shop/product")
@RequiredArgsConstructor
public class ProductController {
	private static final Logger logger=LoggerFactory.getLogger(ProductController.class);
	
	private final ProductService productService;
	@RequestMapping("/productEvent")
	public String productEvent(@RequestParam String eventName, Model model) {
		logger.info("이벤트별 상품 조회, 파라미터 eventName={}",eventName);
		
		List<ProductVO> list=productService.selectProductByEvent(eventName);
		logger.info("이벤트별 조회 결과, list.size={}",list.size());
		
		model.addAttribute("list", list);
		
		return "shop/product/productEvent";
	}
	
	@RequestMapping("/productDetail")
	public String detail(@RequestParam(defaultValue ="0") int productNo, Model model) {
		logger.info("상품 상세보기, 파라미터 productNo={}",productNo);
		if(productNo==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/");
			
			return "common/message";
		}
		ProductVO vo=productService.selectProductByNO(productNo);
		logger.info("상품 상세보기 결과, vo={}",vo);
		
		model.addAttribute("vo", vo);
		return "shop/product/productDetail";
		
	}
	
	@RequestMapping("/productByCategory")
	public String pdBycg(@RequestParam(defaultValue = "0") int categoryNo, @RequestParam String categoryName,Model model) {
		//1 파라미터 가져오기
		logger.info("카테고리별 상품 조회, 파라미터 categoryNo={}, categoryName={}",
				categoryNo, categoryName);
		
		//2 DB작업
		List<ProductVO> list=productService.selectProductByCategory(categoryNo);
		logger.info("카테고리별 상품 조회, 결과 list.size()={}",list.size());
		
		//3 모델에 결과 저장, 뷰페이지 리턴
		model.addAttribute("list", list);
		
		return "shop/product/productByCategory";
	}
	
	@RequestMapping("/productImage")
	public String pdImg(@RequestParam String imageUrl) {
		logger.info("큰 이미지 보여주기, 파라미터 imageUrl={}",imageUrl);
		return "shop/product/productImage";
	}
}
