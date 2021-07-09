package com.it.herb.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.herb.member.model.MemberService;
import com.it.herb.member.model.MemberVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {
	private static final Logger logger=LoggerFactory.getLogger(LoginController.class);
	
	private final MemberService memberService;
	
	@GetMapping("/login")
	public void login() {
		logger.info("로그인 화면");
		
	}
	
	@PostMapping("/login")
	public String login_post(@RequestParam String userid, @RequestParam String pwd,
				@RequestParam(required = false) String chkSave, HttpServletRequest request,
				HttpServletResponse response, Model model) {
		//1
		logger.info("로그인 처리, 파라미터 userid={}, pwd={}, chkSave={}",userid, pwd, chkSave);
		
		//2
		int result=memberService.loginProc(userid, pwd);
		logger.info("로그인 처리, 결과 result={}",result);
		
		String msg="로그인 처리 실패", url="/login/login";
		if(result==MemberService.LOGIN_OK) {
			MemberVO vo=memberService.selectByUserid(userid);
			logger.info("로그인 처리, 회원조회 결과 vo={}",vo);
			
			//[1] session에 저장
			HttpSession session=request.getSession();
			session.setAttribute("userid", userid);
			session.setAttribute("userName", vo.getName());
			
			//[2] 쿠키에 저장
			Cookie ck=new Cookie("ck_userid",userid);
			ck.setPath("/");
			if(chkSave !=null && !chkSave.isEmpty()) {
				ck.setMaxAge(1000*24*60*60);
			}else {
				ck.setMaxAge(0);
			}
			response.addCookie(ck);
			
			msg=vo.getName()+"님 로그인되었습니다.";
			url="/";
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}else if(result==MemberService.ID_NONE) {
			msg="해당 아이디가 존재하지 않습니다.";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		logger.info("로그아웃");
		
		//session.invalidate();
		session.removeAttribute("userid");
		session.removeAttribute("userName");
		
		return "redirect:/";
	}
}
