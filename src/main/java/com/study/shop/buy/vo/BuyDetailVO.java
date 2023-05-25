package com.study.shop.buy.vo;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.shop.item.vo.ItemVO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BuyDetailVO {
	private String buyDetailCode;
	@JsonProperty("item_code")
	private String itemCode;
	@JsonProperty("buy_cnt")
	private int buyCnt;
	@JsonProperty("detail_buy_price")
	private int detailBuyPrice;
	private String buyCode;
	private ItemVO itemVO;
}
