package com.it.herb.reboard.controller;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.it.herb.common.ConstUtil;
import com.it.herb.common.FileUploadUtil;
import com.it.herb.common.PaginationInfo;
import com.it.herb.common.SearchVO;
import com.it.herb.common.Utility;
import com.it.herb.reboard.model.ReBoardService;
import com.it.herb.reboard.model.ReBoardVO;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reBoard")
@RequiredArgsConstructor
public class ReBoardController {
	private static final Logger logger
	=LoggerFactory.getLogger(ReBoardController.class);
	
	private final ReBoardService reBoardService;
	private final FileUploadUtil fileUploadUtil;

	@RequestMapping(value="/write.do", method=RequestMethod.GET)
	public String write() {
		logger.info("글 쓰기 화면 보여주기");
		
		return "reBoard/write";
	}
	
	@RequestMapping(value="/write.do", method =RequestMethod.POST)
	public String write_post(@ModelAttribute ReBoardVO vo, HttpServletRequest request, Model model) {
		//1
		logger.info("글등록 처리, 파라미터   vo={}", vo);
		
		//2
		//파일 업로드 처리
		String fileName="", originalFileName="";
		long fileSize=0;
		try {
			List<Map<String, Object>> list=fileUploadUtil.fileUpload(request,ConstUtil.UPLOAD_FILE_FLAG);
			for(int i=0;i<list.size();i++) {
				Map<String, Object> map=list.get(i);
				fileName=(String) map.get("fileName");
				originalFileName=(String) map.get("originalFileName");
				fileSize=(Long) map.get("fileSize");
			}
			
			logger.info("파일 업로드 성공, fileName={}, fileSize={}",fileName, fileSize);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		vo.setFileName(fileName);
		vo.setFileSize(fileSize);
		vo.setOriginalFileName(originalFileName);
		
		String msg="", url="";
		int cnt=reBoardService.insertReBoard(vo);		
		logger.info("글쓰기 결과, cnt={}", cnt);
		
		if(cnt>0) {
			msg="글쓰기 처리되었습니다.";
			url="/reBoard/list.do";
		}else {
			msg="글쓰기 실패.";
			url="/reBoard/write.do";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/list.do")
	public String list(@ModelAttribute SearchVO searchVo, Model model) {
		//1
		logger.info("글 목록 페이지, 파라미터 searchVo={}", searchVo);
		
		//페이징 처리
		//[1] PaginationInfo 객체 생성
		PaginationInfo pagingInfo = new PaginationInfo();
		pagingInfo.setCurrentPage(searchVo.getCurrentPage());
		pagingInfo.setBlockSize(ConstUtil.BLOCK_SIZE);
		pagingInfo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);
		
		//[2] SearchVo에 paging관련 변수값 셋팅
		searchVo.setFirstRecordIndex(pagingInfo.getFirstRecordIndex());
		searchVo.setRecordCountPerPage(ConstUtil.RECORD_COUNT);
		logger.info("페이지번호 관련 셋팅 후 searchVo={}", searchVo);
		
		//2
		List<ReBoardVO> list=reBoardService.selectAll(searchVo);
		logger.info("글 전체 조회 결과, list.size={}", list.size());
		
		int totalRecord=reBoardService.selectTotalRecord(searchVo);
		logger.info("totalRecord="+totalRecord);
		pagingInfo.setTotalRecord(totalRecord);
		
		//3
		model.addAttribute("list", list);
		model.addAttribute("pagingInfo", pagingInfo);
		
		return "reBoard/list";
	}
	
	@RequestMapping("/countUpdate.do")
	public String countUpdate(@RequestParam(defaultValue = "0") int no,
			Model model) {
		//1
		logger.info("조회수 증가 페이지, 파라미터 no={}", no);
		if(no==0) {
			model.addAttribute("msg", "잘못된 url입니다.");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2
		int cnt=reBoardService.updateReadCount(no);
		logger.info("조회수 증가 결과, cnt={}", cnt);
		
		//3
		return "redirect:/reBoard/detail.do?no="+no;
	}
	
	@RequestMapping("/detail.do")
	public String detail(@RequestParam(defaultValue = "0") int no, HttpServletRequest request ,Model model) {
		//1
		logger.info("상세보기, 파라미터 no={}", no);
		if(no==0) {
			model.addAttribute("msg", "잘못된 url!");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("상세보기 결과, vo={}", vo);
		
		String fileInfo=Utility.getFileInfo(vo.getOriginalFileName(), vo.getFileSize(), request);
		
		//3
		model.addAttribute("vo", vo);
		model.addAttribute("fileInfo", fileInfo);
		
		return "reBoard/detail";
	}
		
	@RequestMapping(value="/edit.do", method=RequestMethod.GET)
	public String edit(@RequestParam(defaultValue = "0") int no, HttpServletRequest request, Model model) {
		//1		
		logger.info("수정화면 보기, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url!");
			model.addAttribute("url", "/reBoard/list.do");
			return "common/message";
		}
		
		//2
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("수정화면-조회,결과 vo={}", vo);
		
		String fileInfo
		=Utility.getFileInfo(vo.getOriginalFileName(), no, request);
		//3
		model.addAttribute("vo", vo);
		model.addAttribute("fileInfo", fileInfo);
		
		return "reBoard/edit";
	}
	
	@RequestMapping(value="/edit.do", method=RequestMethod.POST)
	public String edit_post(@ModelAttribute ReBoardVO vo, 
			@RequestParam String oldFileName
			,HttpServletRequest request, Model model) {
		//1
		logger.info("수정 처리, 파라미터 vo={}, oldFileName={}", vo,oldFileName);
		
		//2
		String msg="글 수정 실패", url="/reBoard/edit.do?no="+vo.getNo();
		if(reBoardService.checkPwd(vo.getNo(), vo.getPwd())) {
			//파일 업로드 처리
			String fileName="", originalFileName="";
			long fileSize=0;
			List<Map<String, Object>> list=null;
			try {
				list=fileUploadUtil.fileUpload(request,ConstUtil.UPLOAD_FILE_FLAG);
				for(Map<String , Object> map : list) {
					fileName=(String) map.get("fileName");
					originalFileName=(String) map.get("originalFileName");
					fileSize=(long) map.get("fileSize");
				}
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			vo.setFileName(fileName);
			vo.setOriginalFileName(originalFileName);
			vo.setFileSize(fileSize);
			
			int cnt=reBoardService.updateReBoard(vo);
			if(cnt>0) {
				msg="글 수정되었습니다.";
				url="/reBoard/detail.do?no="+vo.getNo();
				
				//새로 파일 첨부한 경우, 기존 파일이 존재하면 기존 파일 삭제하기
				if(!list.isEmpty()) {
					if(oldFileName!=null && !oldFileName.isEmpty()) {
						File oldFile=new File(fileUploadUtil.getUploadPath(request,ConstUtil.UPLOAD_FILE_FLAG), oldFileName);
						if(oldFile.exists()) {
							boolean bool=oldFile.delete();
							logger.info("기존파일 삭제 여부:{}",bool);
						}
					}
				}
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping(value="/delete.do", method = RequestMethod.GET)
	public String delete(@RequestParam(defaultValue = "0") int no, Model model) {
		//1
		logger.info("삭제 화면, 파라미터 no={}", no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된  url");
			model.addAttribute("url", "/reBoard/list.do");
			
			return "common/message";
		}
		
		//2		
		//3
		return "reBoard/delete";
	}
	
	@RequestMapping(value="/delete.do",method=RequestMethod.POST)
	public String delete_post(@ModelAttribute ReBoardVO vo,
			HttpServletRequest request, Model model) {
		//1
		logger.info("삭제 처리, 파라미터 vo={}", vo);
		
		//2
		String msg="글 삭제 실패", url="/reBoard/delete.do?no="+vo.getNo()
			+"&groupNo="+vo.getGroupNo()+"&step="+vo.getStep()+"&fileName="+vo.getFileName()
			+"&fileName="+vo.getFileName();
		if(reBoardService.checkPwd(vo.getNo(), vo.getPwd())) {
			Map<String, String> map=new HashMap<>();
			map.put("no", vo.getNo()+"");
			map.put("step", vo.getStep()+"");
			map.put("groupNo", vo.getGroupNo()+"");
			
			reBoardService.deleteReBoard(map);
			
			msg="글 삭제되었습니다.";
			url="/reBoard/list.do";
			
			//첨부파일 존재하면 파일도 삭제
			String fileName=vo.getFileName();
			if(fileName!=null && !fileName.isEmpty()) {
				String upPath=fileUploadUtil.getUploadPath(request,ConstUtil.UPLOAD_FILE_FLAG);
				File file=new File(upPath, fileName);
				if(file.exists()) {
					boolean bool=file.delete();
					logger.info("파일 삭제 여부:{}", bool);
				}
			}
		}else {
			msg="비밀번호가 일치하지 않습니다.";
		}
		
		//3
		model.addAttribute("msg", msg);
		model.addAttribute("url", url);
		
		return "common/message";
	}
	
	@RequestMapping("/download.do")
	public ModelAndView download(@ModelAttribute ReBoardVO vo, HttpServletRequest request) {
		//1
		logger.info("다운로드 처리, 파라미터 vo={}",vo);
		//2
		int cnt=reBoardService.updateDownCount(vo.getNo());
		logger.info("다운로드 수 증가, 결과 cnt={}",cnt);
		
		//3
		Map<String, Object> map=new HashMap<>();
		
		String uploadPath=fileUploadUtil.getUploadPath(request,ConstUtil.UPLOAD_FILE_FLAG);
		File file=new File(uploadPath, vo.getFileName());
		map.put("file", file);
		map.put("originalFileName", vo.getOriginalFileName());
		
		//ModelAndView(String viewName, Map<String, ?> model)
		ModelAndView mav=new ModelAndView("downloadView", map);
		return mav;
	}
	
	@GetMapping("/reply.do")
	public String reply(@RequestParam(defaultValue="0") int no, Model model) {
		logger.info("답변 화면, 파라미터 no={}",no);
		
		if(no==0) {
			model.addAttribute("msg", "잘못된 url");
			model.addAttribute("url", "reBoard/list.do");
			return "common/message";
		}
		
		ReBoardVO vo=reBoardService.selectByNo(no);
		logger.info("답변화면-조회 결과, vo={}",vo);
		
		model.addAttribute("vo", vo);
		
		return "reBoard/reply";
	}
	
	@PostMapping("/reply.do")
	public String reply_post(@ModelAttribute ReBoardVO vo) {
		logger.info("답변하기, 파라미터 vo={}",vo);
		
		int cnt=reBoardService.reply(vo);
		logger.info("답변처리 결과, cnt={}",cnt);
		
		return "redirect:/reBoard/list.do";
	}
}
