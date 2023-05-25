package com.study.shop.buy.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchVO {
	private int month;
	private String fromDate;
	private String toDate;
	
	//내 코드(주문관리-검색기능)
	private String searchOption;
	private String searchValue;
	private int rowNumber;
}
