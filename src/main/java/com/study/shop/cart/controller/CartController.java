package com.study.shop.cart.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.shop.cart.service.CartService;
import com.study.shop.cart.vo.CartVO;
import com.study.shop.member.vo.MemberVO;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Resource(name = "cartService")
	private CartService cartService;
	
	@ResponseBody
	@PostMapping("/regCartAjax")
	public int regCartAjax(CartVO cartVO, Authentication authentication) {
		//로그인 아이디 뽑기
		User user = (User)authentication.getPrincipal();
		
		
		cartVO.setMemId(user.getUsername());
		//상품정보 장바구니에 넣기
		//성공여부 리턴(int)
		return cartService.regCart(cartVO);
	}
	
	@GetMapping("/cartList")
	public String cartList(Model model, Authentication authentication){
		User user = (User)authentication.getPrincipal();
		
		/*
		 * if(user == null) { return "redirect:/item/itemList"; }
		 */
		
		//장바구니 목록조회
		List<CartVO> cartList = cartService.getCartList(user.getUsername());
		model.addAttribute("cartList", cartList);
			
		return "content/cart/cart_list";
	}
	@GetMapping("/update")
	public String updateCart(CartVO cartVO) {
		System.out.println("컨트롤러 도착~~~~~~~");
		cartService.updateCart(cartVO);
		System.out.println("쿼리 실행 완료~~~~~~~");
		return "redirect:/cart/cartList";
	}
	@GetMapping("/delete")
	public String deleteCart(String cartCode) {
		cartService.deleteCart(cartCode);
		return "redirect:/cart/cartList";
	}
	@GetMapping("/deleteCarts")
	public String deleteCarts(String[] cartCodes) {
		 
		 CartVO cartVO = new CartVO(); 
		 //리스트로 바꾼 cartCodes 넣기
		 cartVO.setCartCodeList(Arrays.asList(cartCodes));
		 
		 
		 System.out.println(cartVO);
		 
		 cartService.deleteCarts(cartVO);
		 
		
		return "redirect:/cart/cartList";
	}
	
	
}
