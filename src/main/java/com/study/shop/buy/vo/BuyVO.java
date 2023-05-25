package com.study.shop.buy.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.shop.admin.vo.PageVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyVO extends SearchVO{
//searchVO를 상속시켜서 searchVO의 변수 사용
	private String buyCode;
	private String memId;
	//@JsonProperty("final_price")
	private int buyPrice;
	private String buyDate;
	private int etc;
	//@JsonProperty("detail_info_arr")
	private List<BuyDetailVO> buyDetailList;
	
	
	
}
