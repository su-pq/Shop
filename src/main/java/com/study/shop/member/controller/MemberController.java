package com.study.shop.member.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.shop.member.service.MemberService;
import com.study.shop.member.vo.MemberVO;
import com.study.shop.util.MailService;
import com.study.shop.util.MailVO;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
public class MemberController {
	@Resource(name = "memberService")
	private MemberService memberService;
	@Autowired
	private PasswordEncoder encoder;
	@Resource(name = "mailService")
	private MailService mailService;
	
	//아이디 중복체크
	@ResponseBody
	@PostMapping("/isDuplicateMemId")
	public boolean isDuplicateMemId(String memId) {
		return memberService.isDuplicateMemId(memId);
	}
	
	@PostMapping("/join")
	public String join(MemberVO memberVO) {
		memberVO.setMemPw(encoder.encode(memberVO.getMemPw()));
		memberService.join(memberVO);
		return "redirect:/";
	}
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "redirect:/";
	}
	
	//security 인증 실패 시 강제로 이동되는 로그인 페이지로 이동
	@GetMapping("/loginForm")
	public String loginForm() {
		return "content/member/login2";
	}
	//비밀번호 찾기
	@ResponseBody
	@PostMapping("/findPwAjax")
	public boolean findPw(MemberVO memberVO) {
		//임시 비밀번호 생성 + 암호화 작업
		String imsiPw = mailService.createRandomPw();
		memberVO.setMemPw(encoder.encode(imsiPw));
		//비밀번호 변경
		memberService.updateMemPw(memberVO);

		//이메일주소 찾기 쿼리 실행
		String memEmail = memberService.getMemEmail(memberVO);
		
		if(memEmail != null) {
			MailVO mailVO = new MailVO();
			
			mailVO.setTitle("[BOOK SHOP] 임시비밀번호 발송");
			
			List<String> emailList = new ArrayList<>();
			emailList.add(memEmail);
			mailVO.setRecipientList(emailList);
			mailVO.setContent("임시 비밀번호 : " + imsiPw);
			
			mailService.sendSimpleEmail(mailVO);
			
		}
		
		return memEmail != null ? true : false;
	}
	@GetMapping("/updateMemberForm")
	public String updateMemberForm(Model model, Authentication authentication) {
		User user =  (User)authentication.getPrincipal();
		model.addAttribute("member", memberService.getMemberInfo(user.getUsername()));
		
		return "content/member/update_member_form";
	}
	
	@ResponseBody
	@PostMapping("/getIsMachedPwAjax")
	public boolean getIsMachedPwAjax(String memPw, Authentication authentication) {
		//비밀번호가 일치하는지 확인
		User user = (User)authentication.getPrincipal();
		String resultPw = memberService.getMemPw(user.getUsername());
		if(encoder.matches(memPw, resultPw)) {
			return true;
		}else {
			return false;
		}
	}
	
	@PostMapping("/updateMemberInfo")
	public int updateMemberInfo(MemberVO memberVO, Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		memberVO.setMemId(user.getUsername());
		//수정 쿼리 실행
		return memberService.updateMemberInfo(memberVO);
	}
	
	
	
	
}
