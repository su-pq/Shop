package com.study.shop.admin.service;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.shop.admin.vo.MenuVO;
import com.study.shop.admin.vo.OrderStatusVO;
import com.study.shop.admin.vo.StatisticsByCategoryVO;
import com.study.shop.admin.vo.StatisticsVO;
import com.study.shop.admin.vo.StatusInfoVO;
import com.study.shop.admin.vo.SubMenuVO;
import com.study.shop.buy.vo.BuyDetailVO;
import com.study.shop.buy.vo.BuyVO;
import com.study.shop.item.vo.CategoryVO;
import com.study.shop.item.vo.ItemVO;

@Service("adminService")
public class AdminServiceImpl implements AdminService{
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Override
	public List<CategoryVO> getCateListForAdmin() {
		return sqlSession.selectList("adminMapper.getCateListForAdmin");
	}

	@Override
	public int regCate(String cateName) {
		return sqlSession.insert("adminMapper.regCate", cateName);
	}


	@Override
	public int checkCateName(String cateName) {
		return sqlSession.selectOne("adminMapper.checkCateName", cateName);
	}

	@Override
	public int updateIsUse(String cateCode) {
		return sqlSession.update("adminMapper.updateIsUse", cateCode);
	}

	@Override
	public int deleteCate(String cateCode) {
		return sqlSession.delete("adminMapper.deleteCate", cateCode);
	}

	@Override
	public List<MenuVO> getAdminMenuList() {
		return sqlSession.selectList("adminMapper.getAdminMenuList");
	}

	@Override
	public List<SubMenuVO> getAdminSubMenuList(String menuCode) {
		return sqlSession.selectList("adminMapper.getAdminSubMenuList", menuCode);//오류
	}

	@Override
	public String getNextItemCode() {
		return sqlSession.selectOne("adminMapper.getNextItemCode");
	}
	
	
	//Transactional : 해당 메소드 내의 쿼리 실행은 트랜젝션 처리
	//				  (두 개의 쿼리가 모두 성공해야지만 commit, 아니면 rollback시키는 어노테이션)
	//rollbackFor : 언제 rollback할 것인지(어떤 오류가 발생하면 rollback할 것인지)
	//Exception : 모든 예외에 대한 부모클래스 (아래의 경우 : 어떤 오류라도 나면 무조건 rollback)
	@Override
	@Transactional(rollbackFor = Exception.class)
	public void regItem(ItemVO itemVO) {
		sqlSession.insert("adminMapper.regItem", itemVO);
		sqlSession.insert("adminMapper.regImgs", itemVO);
	}
	
	
	@Override
	public List<ItemVO> getItemListForItemManage(ItemVO itemVO) {
		System.out.println(itemVO.getSearchItemVO());
		return sqlSession.selectList("adminMapper.getItemListForItemManage", itemVO);
	}

	@Override
	public ItemVO getItemForItemManage(String itemCode) {
		return sqlSession.selectOne("adminMapper.getItemForItemManage", itemCode);
	}

	@Override
	public void updateItem(ItemVO itemVO) {
		sqlSession.update("adminMapper.updateItem", itemVO);
	}

	@Override
	public List<StatisticsVO> getSaleStatusPerMonth(int year) {
		return sqlSession.selectList("adminMapper.getSaleStatusPerMonth", year);
	}

	@Override
	public List<Map<String, Integer>> getSaleStatusPerMonth2(int year) {
		return sqlSession.selectList("adminMapper.getSaleStatusPerMonth2", year);
	}

	@Override
	public List<StatisticsByCategoryVO> getSaleStatusByCategory() {
		return sqlSession.selectList("adminMapper.getSaleStatusByCategory");
	}

	@Override
	public List<Map<String, Object>> getSaleStatusByCategory2() {
		return sqlSession.selectList("adminMapper.getSaleStatusByCategory2");
	}
	
	@Override
	public List<OrderStatusVO> getOrderListByStatus(int statusCode) {
		return sqlSession.selectList("adminMapper.getOrderListByStatus", statusCode);
	}

	@Override
	public List<OrderStatusVO> getOrderListPage(OrderStatusVO orderStatusVO) {
		return sqlSession.selectList("adminMapper.getOrderListPage", orderStatusVO);
	}

	@Override
	public int getBuyCntForOrderManage(OrderStatusVO orderStatusVO) {
		return sqlSession.selectOne("adminMapper.getBuyCntForOrderManage", orderStatusVO);
	}

	@Override
	public int changeStatus(Map<String, Object> map) {
		return sqlSession.update("adminMapper.changeStatus", map);
	}

	@Override
	public List<StatusInfoVO> getStatusInfoList() {
		return sqlSession.selectList("adminMapper.getStatusInfoList");
	}

	@Override
	public List<BuyDetailVO> getBuyDetailList(String buyCode) {
		return sqlSession.selectList("adminMapper.getBuyDetailList", buyCode);
	}

}
