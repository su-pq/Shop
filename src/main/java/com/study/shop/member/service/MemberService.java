package com.study.shop.member.service;

import com.study.shop.cart.vo.CartVO;
import com.study.shop.member.vo.MemberVO;

public interface MemberService {
	boolean isDuplicateMemId(String memId);
	void join(MemberVO memberVO);
	MemberVO getLoginInfo(String memId);
	int addCart(CartVO cartVO);
	
	//비밀번호 찾기 기능 - 이메일 가져오기
	String getMemEmail(MemberVO memberVO);
	//비밀번호 변경
	void updateMemPw(MemberVO memberVO);
	
	//내정보확인 > 개인정보 수정 - 기존 정보 가져오기
	MemberVO getMemberInfo(String memId);
	//개인정보 수정 - 비밀번호 확인
	String getMemPw(String memId);
	//개인정보 수정
	int updateMemberInfo(MemberVO memberVO);
	

}
