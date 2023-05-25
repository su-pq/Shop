package com.study.shop.buy.service;

import java.util.List;

import com.study.shop.buy.vo.BuyDetailVO;
import com.study.shop.buy.vo.BuyVO;
import com.study.shop.cart.vo.CartVO;
import com.study.shop.member.vo.MemberVO;

public interface BuyService {
	List<CartVO> getBuyListForCart(CartVO cartVO);
	MemberVO getBuyMemberInfo(String memId);
	int regBuy(BuyVO buyVO);
	String getNextBuyCode();
	void buyFromCart(BuyVO buyVO);
	List<BuyVO> getBuyList(BuyVO buyVO);

}
