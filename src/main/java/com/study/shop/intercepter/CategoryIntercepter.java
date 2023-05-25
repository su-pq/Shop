package com.study.shop.intercepter;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.study.shop.item.service.ItemService;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryIntercepter implements HandlerInterceptor{
	@Resource(name = "itemService")
	private ItemService itemService;

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
			modelAndView.addObject("cateList", itemService.getCateListInUse());
	}
	
	
}
