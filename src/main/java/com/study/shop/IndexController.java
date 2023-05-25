package com.study.shop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.study.shop.member.vo.MemberVO;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//프로젝트 시작 시 최초에 실행되는 컨트롤러 (localhost:8081로 접속시)
@Controller
public class IndexController {
	//아이템컨트롤러 이동
	@GetMapping("/")
	public String index(Authentication authentication, HttpServletRequest request) {
		//
		String previousPage = request.getHeader("Referer"); 
		//프로젝트 첫 시작시 : null(이전 페이지가 없기 때문에)
		//일반회원 로그인시 : http://localhost:8081/item/itemList
		
		//이동할 페이지 경로를 담을 path
		String path = "";
		
		//authentication이 null인 경우 : 미로그인시
		if(authentication == null) {
			path = "redirect:/item/itemList";
		}else {
			//로그인한 사람의 권한 정보
			User user = (User)authentication.getPrincipal();
			
			List<GrantedAuthority> authoList = new ArrayList<>(user.getAuthorities());
			
			List<String> strAuthoList = new ArrayList<>();
			for(GrantedAuthority authority : authoList) {
				
				strAuthoList.add(authority.getAuthority());
				
			}
			
			if(strAuthoList.contains("ROLE_ADMIN")) {
				path =  "redirect:/admin/cateManage";			
			}else {//로그인을 하지 않았거나, 일반 회원인 경우
				//처음 들어왔을 경우에는 item_list, 아닐 경우에는 이전 페이지로 이동
				if(previousPage == null) {
					path = "redirect:/item/itemList";
				}else {
					path = "redirect:" + previousPage;
					
					//previousPage(이전 페이지 url)에 '/admin'이라는 글자가 있는지 확인
					//admin 페이지에서 로그아웃 했을 경우 실행됨
					if(previousPage.contains("/admin")) {
						path = "redirect:/item/itemList";
						
					}
				}
			}
			
		}
		
		return path;
	}
	
	//미인가 시 이동할 페이지
	@GetMapping("/accessDeny")
	public String accessDeny() {
		return "content/access_deny";
	}
	
	
}
