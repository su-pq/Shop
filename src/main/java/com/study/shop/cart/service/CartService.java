package com.study.shop.cart.service;

import java.util.List;

import com.study.shop.cart.vo.CartVO;

public interface CartService {
	int regCart(CartVO cartVO);
	List<CartVO> getCartList(String memId);
	int updateCart(CartVO cartVO);
	int deleteCart(String cartCode);
	int deleteCarts(CartVO cartVO);
}
