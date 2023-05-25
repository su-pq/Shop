package com.study.shop.buy.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.shop.buy.vo.BuyDetailVO;
import com.study.shop.buy.vo.BuyVO;
import com.study.shop.cart.vo.CartVO;
import com.study.shop.member.vo.MemberVO;

@Service("buyService")
public class BuyServiceImpl implements BuyService{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<CartVO> getBuyListForCart(CartVO cartVO) {
		return sqlSession.selectList("buyMapper.getBuyListForCart", cartVO);
	}

	@Override
	public MemberVO getBuyMemberInfo(String memId) {
		return sqlSession.selectOne("buyMapper.getBuyMemberInfo", memId);
	}

	@Override
	public int regBuy(BuyVO buyVO) {
		return sqlSession.insert("buyMapper.regBuy", buyVO);
	}

	@Override
	public String getNextBuyCode() {
		return sqlSession.selectOne("buyMapper.getNextBuyCode");
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public void buyFromCart(BuyVO buyVO) {
		sqlSession.insert("buyMapper.buy", buyVO);
		sqlSession.insert("buyMapper.buyDetails", buyVO);
		sqlSession.insert("buyMapper.regOrderStatus", buyVO);
		//sqlSession.delete("buyMapper.deleteBuyCart", buyVO);
	}

	@Override
	public List<BuyVO> getBuyList(BuyVO buyVO) {
		return sqlSession.selectList("buyMapper.getBuyList", buyVO);
	}



}
