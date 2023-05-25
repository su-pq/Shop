package com.study.shop.buy.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.shop.buy.service.BuyService;
import com.study.shop.buy.vo.BuyDetailVO;
import com.study.shop.buy.vo.BuyVO;
import com.study.shop.cart.vo.CartVO;
import com.study.shop.item.service.ItemService;

import com.study.shop.util.DateUtil;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/buy")
public class BuyController {
	@Resource(name = "buyService")
	private BuyService buyService;
	@Resource(name = "itemService")
	private ItemService itemService;
	
	
	//구매(쌤)
	@ResponseBody
	@PostMapping("/buysAjax")
	public void buysAjax(@RequestBody Map<String, Object> mapData, Authentication authentication, BuyVO buyVO) {
		//구매정보 데이터 세팅
		buyVO.setBuyPrice(Integer.parseInt((String)(mapData.get("final_price"))));
		buyVO.setBuyCode(buyService.getNextBuyCode());
		
		User user = (User)authentication.getPrincipal();
		buyVO.setMemId(user.getUsername());
		
		//상세정보 데이터 세팅
		ObjectMapper mapper = new ObjectMapper();
		BuyDetailVO[] arr = mapper.convertValue(mapData.get("detail_info_arr"), BuyDetailVO[].class);
		List<BuyDetailVO> buyDetailList = Arrays.asList(arr);		
		
		buyVO.setBuyDetailList(buyDetailList);
		
		for(BuyDetailVO detail : buyVO.getBuyDetailList()) {
			detail.setBuyCode(buyVO.getBuyCode());
		}
		
		//구매 쿼리 실행
		//(구매정보 등록 + 구매상세정보 등록)
		buyService.buyFromCart(buyVO);
		//구매상세정보에 INSERT
		
		
	}
	//구매내역 버튼 클릭시(버튼 클릭시 실행되는 post 방식과 구매내역 메뉴 클릭시 실행되는 get방식 모두 실행되도록 requestMapping 사용)
	@RequestMapping("/buyList")
	public String gobuyList(Model model, Authentication authentication, BuyVO buyVO) {
		//오늘날짜, 이달의 첫째날을 fromDate, toDate에 세팅
		String nowDate = DateUtil.getNowDateToString();
		String firstDate = DateUtil.getFirstDateOfMonth();
		
		User user = (User)authentication.getPrincipal();
		buyVO.setMemId(user.getUsername());		
		
		model.addAttribute("buyList", buyService.getBuyList(buyVO));
		
		//넘어온 날짜 데이터가 없다면 기본 값으로 날짜 세팅(검색을 하지 않았다면)
		if(buyVO.getFromDate() == null) {
			buyVO.setFromDate(firstDate);			
		}
		if(buyVO.getToDate() == null) {
			buyVO.setToDate(nowDate);
		}
		
		return "/content/buy/buy_list";
	}
	
	
	
	
	@GetMapping("/goBuyForm")
	public String goBuy(String cartCodes, Model model, Authentication authentication) {
		
		User user = (User)authentication.getPrincipal();
		
		
		//구매목록 정보 전달
		System.out.println("!!!!!!!!!!!!!!!!!!!!!!" + cartCodes);
		//cartCode값 세팅
		String[] cartCodeArr = cartCodes.split(",");
		List<String> cartCodeList = Arrays.asList(cartCodeArr);
		
		CartVO cartVO = new CartVO();
		cartVO.setCartCodeList(cartCodeList);
		System.out.println(cartVO);
		
		model.addAttribute("buyList", buyService.getBuyListForCart(cartVO));
		
		//구매자 정보 전달
		
		model.addAttribute("buyMember", buyService.getBuyMemberInfo(user.getUsername()));
		//
		return "/content/buy/buy";
	}
	
	@ResponseBody
	@PostMapping("/regBuyAjax")
	public int regBuy(BuyVO buyVO, Authentication authentication) {
		//쿼리 실행 데이터 세팅(memId)
		User user = (User)authentication.getPrincipal();
		buyVO.setMemId(user.getUsername());
		
		return buyService.regBuy(buyVO);
	}
	
}
