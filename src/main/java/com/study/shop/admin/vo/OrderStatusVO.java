package com.study.shop.admin.vo;

import com.study.shop.buy.vo.BuyVO;
import com.study.shop.buy.vo.SearchVO;
import com.study.shop.member.vo.MemberVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderStatusVO extends OrderListSearchVO {
	private int orderNum;
	private int statusCode;
	private String buyCode;
	//private String[] buyCodeArr;
	private String memId;
	private String updateDate;
	private StatusInfoVO statusInfoVO;
	private BuyVO buyVO;
	private MemberVO memberVO;
	
	
	
}
