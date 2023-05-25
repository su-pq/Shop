package com.study.shop.member.service;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.study.shop.member.vo.MemberVO;

import jakarta.annotation.Resource;

@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService{
	@Resource(name = "memberService")
	private MemberService memberService;
	
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		MemberVO userInfo = memberService.getLoginInfo(username); 
		
		//아이디가 없으면 강제로 오류 발생시키기
		if(userInfo == null) {
			throw new UsernameNotFoundException("오류");
		}
		
		UserDetails userDetails = User.withUsername(userInfo.getMemId())
										.password(userInfo.getMemPw())
										.roles(userInfo.getMemRole().split(","))
										.build();
		
		return userDetails;
	}
	
	

}
