package com.it.herb.board.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.it.herb.board.model.BoardService;
import com.it.herb.board.model.BoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private static final Logger logger=LoggerFactory.getLogger(BoardController.class);
	
	private final BoardService boardService;
	
	@RequestMapping("/mainNotice")
	public String mainNotice(Model model) {
		logger.info("메인 공지사항");
		
		List<BoardVO> list=boardService.selectMainNotice();
		logger.info("메인 공지사항 조회 결과, list.size={}",list.size());
		
		model.addAttribute("list", list);
		
		return "inc/mainNotice";
	}
}
