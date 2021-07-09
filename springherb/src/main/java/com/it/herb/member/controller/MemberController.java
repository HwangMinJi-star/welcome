package com.it.herb.member.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.it.herb.admin.model.ManagerService;
import com.it.herb.member.model.MemberService;
import com.it.herb.member.model.MemberVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
	private Logger logger=LoggerFactory.getLogger(MemberController.class);
	
	private final MemberService memberService;
	private final ManagerService managerService;
	
	@GetMapping("/agreement")
	public void agreement() {
		logger.info("회원약관 화면 보기");
		
		//return "member/agreement";
	}
	
	@RequestMapping("/register")
	public void register() {
		logger.info("회원가입 화면 보기");
		
	}
	
	@PostMapping("/memberWrite")
	public String memberWrite(@ModelAttribute MemberVO vo,
			@RequestParam String email3, Model model) {
		//1
		logger.info("회원가입 처리, 파라미터 vo={}, email3={}",vo,email3);
		//2
		String hp1=vo.getHp1();
		String hp2=vo.getHp2();
		String hp3=vo.getHp3();
		
		if(hp2==null || hp2.isEmpty() || hp3==null || hp3.isEmpty()) {
			vo.setHp1("");
			vo.setHp2("");
			vo.setHp3("");
		}
		
		String email1=vo.getEmail1();
		String email2=vo.getEmail2();
		
		if(email1==null || email1.isEmpty()) {
			email1="";
			email2="";
		}else{
			if(email2.equals("etc")) {
				if(email3!=null && !email3.isEmpty()) {
					email2=email3;
				}else {
					email1="";
					email2="";
				}
			}
		}
		
		vo.setEmail1(email1);
		vo.setEmail2(email2);
		int cnt=memberService.insertMember(vo);
		logger.info("회원가입 결과, cnt={}",cnt);
		
		//3
		String msg="회원가입 실패!", url="/member/register";
		if(cnt>0) {
			msg="회원가입되었습니다.";
			url="/";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "/common/message";
	}
	
	@RequestMapping("/checkUserid")
	public String checkUserid(@RequestParam String userid, Model model
			, @RequestParam String type) {
		//1
		logger.info("아이디 중복확인, 파라미터 userid={}, type={}",userid,type);
		
		//2
		int result=0;
		if(userid!=null && !userid.isEmpty()) {
			if(type.equals("user")) {
				result=memberService.checkDuplicate(userid);
			}else if(type.equals("admin")) {
				result=managerService.checkDuplicate(userid);
			}
			logger.info("아이디 중복확인 결과, result={}",result);
		}
		//3
		model.addAttribute("UNUSABLE_ID", memberService.UNUSABLE_ID);
		model.addAttribute("USABLE_ID", memberService.USABLE_ID);
		model.addAttribute("result", result);
		
		return "member/checkUserid";
	}
	
	@GetMapping("/memberEdit")
	public String memberEdit(HttpSession session, Model model) {
		String userid=(String) session.getAttribute("userid");
		logger.info("회원 수정 화면, 파라미터 userid={}",userid);
		
		/*if(userid ==null || userid.isEmpty()) {
			model.addAttribute("msg", "먼저 로그인하세요");
			model.addAttribute("url", "/login/login");
			return "common/message";
		}*/
		
		MemberVO vo=memberService.selectByUserid(userid);
		logger.info("회원 수정 화면, 조회 결과 vo={}",vo);
		
		model.addAttribute("vo", vo);
		
		return "member/memberEdit";
	}
	
	@PostMapping("/memberEdit")
	public String memberEdit_post(@ModelAttribute MemberVO vo,
			@RequestParam String email3,
			HttpSession session,
			Model model) {
		String userid=(String) session.getAttribute("userid");
		vo.setUserid(userid);
		logger.info("회원수정 처리, 파라미터 vo={}",vo);
		
		String msg="비밀번호 체크 실패", url="/member/memberEdit";
		int result=memberService.loginProc(userid, vo.getPwd());
		logger.info("회원수정 처리, 비밀번호 체크 결과, result={}",result);
		if(result==MemberService.LOGIN_OK) {
			//hp
			String hp2=vo.getHp2();
			String hp3=vo.getHp3();
			
			if(hp2==null || hp2.isEmpty() || hp3==null || hp3.isEmpty()) {
				vo.setHp2("");
				vo.setHp3("");
			}
			//email
			String email1=vo.getEmail1();
			String email2=vo.getEmail2();
			
			if(email1==null || email1.isEmpty()) {
				email1="";
				email2="";
			}else {
				if(email2.equals("etc")) {
					if(email3!=null && email3.isEmpty()) {
						email2=email3;
					}else {
						email1="";
						email2="";
					}
				}
			}
			
			vo.setEmail1(email1);
			vo.setEmail2(email2);
			
			int cnt=memberService.updateMember(vo);
			logger.info("회원수정 결과, cnt={}",cnt);
			if(cnt>0) {
				msg="회원정보 수정되었습니다.";
				url="/";
			}else {
				msg="회원정보 수정 실패!";
			}
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@GetMapping("/memberOut")
	public void memberOut() {
		logger.info("회원탈퇴 화면");
	}
	
	@PostMapping("/memberOut")
	public String memberOut_post(@RequestParam String pwd, HttpSession session,Model model,
			HttpServletResponse response) {
		String userid=(String) session.getAttribute("userid");
		logger.info("회원탈퇴 처리, 파라미터 pwd={}, userid={}",pwd,userid);
		
		String msg="비밀번호 체크 실패", url="/member/memberOut";
		int result=memberService.loginProc(userid, pwd);
		if(result==MemberService.LOGIN_OK) {
			int cnt=memberService.withdrawMember(userid);
			if(cnt>0) {
				msg="회원탈퇴 처리되었습니다.";
				url="/";
				
				//session
				session.invalidate();
				
				//cookie
				Cookie ck=new Cookie("ck_userid",userid);
				ck.setPath("/");
				ck.setMaxAge(0); //쿠키 삭제
				response.addCookie(ck);
			}else {
				msg="회원탈퇴 실패!"; 
				
			}
		}else if(result==MemberService.PWD_DISAGREE) {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
		
		
	}
}
