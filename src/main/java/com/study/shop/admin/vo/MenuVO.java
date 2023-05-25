package com.study.shop.admin.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MenuVO {
	private String menuCode;
	private String menuName;
	private String menuUrl;
	private List<SubMenuVO> adminSubMenuList;
}
