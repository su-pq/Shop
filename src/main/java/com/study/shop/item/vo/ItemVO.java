package com.study.shop.item.vo;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ItemVO {
	private String itemCode;
	private String cateCode;
	private String[] cateCodes;
	private String itemName;
	private int itemPrice;
	private int itemStock;
	private String itemIntro;
	private int itemStatus;
	private String itemStatusStr;
	private String cateName;
	private int minItemStock;
	private int maxItemStock;
	private List<ImgVO> imgList;
	private CategoryVO categoryVO;
	private SearchItemVO searchItemVO;
}
