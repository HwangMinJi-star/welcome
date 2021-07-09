package com.it.herb.admin.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.herb.admin.model.ManagerService;
import com.it.herb.admin.model.ManagerVO;
import com.it.herb.member.model.MemberService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {
	private static final Logger logger=LoggerFactory.getLogger(AdminController.class);
	
	private final ManagerService managerService;
	
	@RequestMapping("/adminMain")
	public void adminMain() {
		logger.info("관리자 메인 화면");
	}
	
	@GetMapping("/manager/managerWrite")
	public String write(Model model) {
		//1
		logger.info("관리자 화면");
		
		//2
		List<Map<String, Object>> list=managerService.selectAuthority();
		logger.info("권한 조회 결과, list.size={}",list.size());
		
		//3
		model.addAttribute("list", list);
		return "admin/manager/managerWrite";
	}
	
	@PostMapping("/manager/managerWrite")
	public String write_post(@ModelAttribute ManagerVO vo,Model model) {
		//1
		logger.info("관리자 등록, 파라미터 vo={}",vo);
		//2
		int cnt=managerService.insertManager(vo);
		logger.info("관리자 등록 결과, cnt={}",cnt);
		String msg="관리자 등록 실패", url="/admin/manager/managerWrite";
		if(cnt>0) {
			msg="관리자 등록되었습니다.";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@GetMapping("/login/adminLogin")
	public void login() {
		logger.info("관리자 로그인 화면");
	}
	
	@PostMapping("/login/adminLogin")
	public String login_post(@ModelAttribute ManagerVO vo, 
			@RequestParam(required = false) String chkSave, ModelMap model,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("관리자 로그인 처리, 파라미터 vo={}, chkSave={}",vo, chkSave);
		
		int result=managerService.loginProc(vo.getUserid(), vo.getPwd());
		logger.info("관리자 로그인 결과, result={}",result);
		
		String msg="관리자 로그인 처리 실패", url="/admin/login/adminLogin";
		if(result==MemberService.LOGIN_OK) {
			msg="관리자 로그인되었습니다.";
			url="/admin/adminMain";
			//session - adminUserid
			request.getSession().setAttribute("adminUserid", vo.getUserid());
			
			//cookie - ck_admin_userid
			Cookie ck=new Cookie("ck_admin_userid", vo.getUserid());
			ck.setPath("/");
			if(chkSave!=null && !chkSave.isEmpty()) {
				ck.setMaxAge(1000*24*60*60);
			}else {
				ck.setMaxAge(0);
			}
			response.addCookie(ck);
			
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}else if(result==MemberService.ID_NONE) {
			msg="해당 아이디가 존재하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/login/logout")
	public String logout(HttpSession session) {
		logger.info("관리자 로그아웃");
		
		session.removeAttribute("adminUserid");
		
		return "redirect:/admin/login/adminLogin";
	}
}
