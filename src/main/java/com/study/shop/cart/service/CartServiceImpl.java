package com.study.shop.cart.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.shop.cart.vo.CartVO;


@Service("cartService")
public class CartServiceImpl implements CartService{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public int regCart(CartVO cartVO) {
		return sqlSession.insert("cartMapper.regCart", cartVO);
	}

	@Override
	public List<CartVO> getCartList(String memId) {
		return sqlSession.selectList("cartMapper.getCartList", memId);
	}

	@Override
	public int updateCart(CartVO cartVO) {
		return sqlSession.update("cartMapper.updateCart", cartVO);
	}

	@Override
	public int deleteCart(String cartCode) {
		return sqlSession.delete("cartMapper.deleteCart", cartCode);
	}

	@Override
	public int deleteCarts(CartVO cartVO) {
		return sqlSession.delete("cartMapper.deleteCarts", cartVO);
	}
}
