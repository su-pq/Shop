package com.study.shop.item.service;

import java.util.List;

import com.study.shop.item.vo.CategoryVO;
import com.study.shop.item.vo.ItemVO;

public interface ItemService {

	List<CategoryVO> getCateListInUse();
	List<ItemVO> getItemListForUser(String cateCode);
	ItemVO getItemDetail(String itemCode);
}
