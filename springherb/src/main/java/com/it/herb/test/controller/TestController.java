package com.it.herb.test.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.it.herb.test.model.CommentService;
import com.it.herb.test.model.CommentVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TestController {
	private static final Logger logger=LoggerFactory.getLogger(TestController.class);
	
	private final CommentService commentService;
	@GetMapping("/test/write")
	public String write() {
		logger.info("comment2 화면");
		
		return "test/write";
	}
	
	@PostMapping("/test/write")
	public String write_post(@ModelAttribute CommentVO vo) {
		logger.info("comment2 등록, 파라미터 vo = {}", vo);
		
		int cnt=commentService.insertCmt(vo);
		logger.info("등록 결과, cnt={}",cnt);
		
		return "redirect:/test/list";
	}
	
	@GetMapping("/test/list")
	public String list(Model model) {
		//1 파라미터 읽어오기
		logger.info("comment2 리스트");
		//2 DB작업
		List<CommentVO> list= commentService.selectAll();
		logger.info("목록결과, list.size={}",list.size());
		
		//3 model에 결과 저장, view 페이지 리턴
		model.addAttribute("list", list);
		return "test/list";
	}
}
