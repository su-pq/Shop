package com.study.shop.item.controller;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.study.shop.admin.service.AdminService;
import com.study.shop.item.service.ItemService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/item")
public class ItemController {
	@Resource(name = "itemService")
	private ItemService itemService;
	@Resource(name = "adminService")
	private AdminService adminService;
	
	
	@GetMapping("/itemList")
	public String itemList(Model model, String cateCode) {
		model.addAttribute("itemList", itemService.getItemListForUser(cateCode));
		return "content/item/item_list";
	}
	
	@GetMapping("/detail")
	public String itemDetail(String itemCode, Model model, HttpServletRequest request) {
		//요청한 페이지 url을 알려줌(어떤페이지에서 Controller로 왔는지 이전 페이지 경로)
		String data = request.getHeader("Referer");
		System.out.println(data);// 출력 : http://localhost:8081/item/itemList
		
		//쿼리 실행
		model.addAttribute("itemVO", itemService.getItemDetail(itemCode));
		return "content/item/item_detail";
	}
	
	
	
	
}
