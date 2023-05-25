package com.study.shop.item.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchItemVO {
	private String searchItemName;
	private String[] searchCateCode;
	private int searchItemStatus;
	private String searchMinItemStock;
	private String searchMaxItemStock;
	
}
