package com.study.shop.member.service;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.shop.cart.vo.CartVO;
import com.study.shop.member.vo.MemberVO;

@Service("memberService")
public class MemberServiceImpl implements MemberService{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public boolean isDuplicateMemId(String memId) { //isDuplicateMemId
		int result = sqlSession.selectOne("memberMapper.isDuplicateMemId", memId);
		//중복이 아닐 경우 false
		return result != 0 ? true : false;
	}

	@Override
	public void join(MemberVO memberVO) {
		sqlSession.insert("memberMapper.join", memberVO);
	}

	@Override
	public MemberVO getLoginInfo(String memId) {
		return sqlSession.selectOne("memberMapper.login", memId);
	}

	@Override
	public int addCart(CartVO cartVO) {
		return sqlSession.insert("memberMapper.addCart", cartVO);
	}

	@Override
	public String getMemEmail(MemberVO memberVO) {
		return sqlSession.selectOne("memberMapper.getMemEmail", memberVO);
	}

	@Override
	public void updateMemPw(MemberVO memberVO) {
		sqlSession.update("memberMapper.updateMemPw", memberVO);
	}

	@Override
	public MemberVO getMemberInfo(String memId) {
		return sqlSession.selectOne("memberMapper.getMemberInfo", memId);
	}

	@Override
	public String getMemPw(String memId) {
		return sqlSession.selectOne("memberMapper.getMemPw", memId);
	}

	@Override
	public int updateMemberInfo(MemberVO memberVO) {
		return sqlSession.update("memberMapper.updateMemberInfo", memberVO);
	}
	
}
