package com.study.shop.item.service;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.shop.item.vo.CategoryVO;
import com.study.shop.item.vo.ItemVO;

@Service("itemService")
public class ItemServiceImpl implements ItemService{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<CategoryVO> getCateListInUse() {
		return sqlSession.selectList("itemMapper.getCateListInUse");
	}

	@Override
	public List<ItemVO> getItemListForUser(String cateCode) {
		return sqlSession.selectList("itemMapper.getItemListForUser", cateCode);
	}

	@Override
	public ItemVO getItemDetail(String itemCode) {
		return sqlSession.selectOne("itemMapper.getItemDetail", itemCode);
	}



	
	
}
