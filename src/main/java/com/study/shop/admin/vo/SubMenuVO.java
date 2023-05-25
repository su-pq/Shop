package com.study.shop.admin.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubMenuVO {
	private String subMenuCode;
	private String subMenuName;
	private String subMenuUrl;
	private String menuCode;
	
	//메뉴정보가 없을 경우 세팅시키는 메소드
	public void setMenuInfo(String menuCode, String subMenuCode) {
		if(getMenuCode() == null) {
			setMenuCode(menuCode);
		}
		if(getSubMenuCode() == null) {
			setSubMenuCode(subMenuCode);
		}
	}
}
