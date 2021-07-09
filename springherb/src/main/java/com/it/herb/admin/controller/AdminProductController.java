package com.it.herb.admin.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.herb.cartegory.model.CategoryService;
import com.it.herb.cartegory.model.CategoryVO;
import com.it.herb.common.ConstUtil;
import com.it.herb.common.FileUploadUtil;
import com.it.herb.common.PaginationInfo;
import com.it.herb.eventproduct.model.EventProductVO;
import com.it.herb.product.model.ProductService;
import com.it.herb.product.model.ProductVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/product")
public class AdminProductController {
	private static final Logger logger=LoggerFactory.getLogger(AdminProductController.class);
	
	private final CategoryService categoryService;
	private final FileUploadUtil fileUploadUtil;
	private final ProductService productService;
	
	@GetMapping("/productWrite")
	public String pdWrite(Model model) {
		logger.info("상품 등록 화면");
		
		List<CategoryVO> list=categoryService.selectCategory();
		logger.info("상품 등록화면, 카테고리 조회 결과, list.size={}",list.size());
		
		model.addAttribute("list", list);
		return "admin/product/productWrite";
	}
	
	@PostMapping("/productWrite")
	public String pdWrite_post(@ModelAttribute ProductVO vo, HttpServletRequest request, Model model) {
		logger.info("상품 등록 처리, 파라미터 vo={}",vo);
		
		//파일 업로드 처리
		String imageUrl="";
		try {
			List<Map<String, Object>> list=fileUploadUtil.fileUpload(request, ConstUtil.UPLOAD_IMAGE_FLAG);
			for(Map<String, Object> map : list) {
				imageUrl=(String)map.get("fileName");
				
			}
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		vo.setImageUrl(imageUrl);
		//db작업
		int cnt=productService.insertProduct(vo);
		logger.info("상품등록 결과, cnt={}",cnt);
		
		String msg="상품 등록 실패", url="/admin/product/productWrite";
		if(cnt>0) {
			msg="상품 등록되었습니다.";
			url="/admin/product/productList";
		}
		model.addAttribute("msg",msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/productList")
	public String pdList(@ModelAttribute EventProductVO searchVo, Model model) {
		logger.info("상품 목록, 파라미터 searchVo={}",searchVo);
		
		//페이징 처리 관련
		//[1] PaginationInfo
		PaginationInfo pagingInfo=new PaginationInfo();
		pagingInfo.setBlockSize(ConstUtil.BLOCK_SIZE);
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		pagingInfo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);

		//[2] searchVo
		searchVo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		logger.info("셋팅 후 searchVo={}",searchVo);
		
		List<ProductVO> list=productService.selectAll(searchVo);
		logger.info("상품 목록 조회 결과, list.size={}",list.size());
		
		int totalReord=productService.selectTotalRecord(searchVo);
		logger.info("상품 목록, totalRecord={}",totalReord);
		
		pagingInfo.setTotalRecord(totalReord);
		
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "admin/product/productList";
	}
}
