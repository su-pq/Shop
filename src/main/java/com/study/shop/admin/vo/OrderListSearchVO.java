package com.study.shop.admin.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


//주문 관리 - 주문 목록 조회 시 사용하는 searchVO
@Getter
@Setter
@ToString
public class OrderListSearchVO extends PageVO{
	private List<Integer> statusCodeList;
	private String searchFromDate;
	private String searchToDate;
	private String searchKeyword;
	private String searchValue;
	private String orderBy;
	
	private int rowNumber;
	
	
	
}
