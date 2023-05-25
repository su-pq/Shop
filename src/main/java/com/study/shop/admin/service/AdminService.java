package com.study.shop.admin.service;

import java.util.List;
import java.util.Map;

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

public interface AdminService {
	//카테고리 목록 조회
	List<CategoryVO> getCateListForAdmin();
	
	//카테고리명 중복 체크
	int checkCateName(String cateName);
	
	//카테고리 등록
	int regCate(String cateName);
	
	//isUse값 변경
	int updateIsUse(String cateCode);
	
	//카테고리 삭제
	int deleteCate(String cateCode);
	
	List<MenuVO> getAdminMenuList();
	List<SubMenuVO> getAdminSubMenuList(String menuCode);
	
	void regItem(ItemVO itemVO);
	
	String getNextItemCode();
	//상품 관리 - 상품 목록 조회
	List<ItemVO> getItemListForItemManage(ItemVO itemVO);
	//상품 관리 - 상품 상세 조회
	ItemVO getItemForItemManage(String itemCode);
	//상품 관리 - 상품 수정
	void updateItem(ItemVO itemVO);
	
	//통계 조회(두가지 방법 연습)
	List<StatisticsVO> getSaleStatusPerMonth(int year);
	List<Map<String, Integer>> getSaleStatusPerMonth2(int year);
	
	//카테고리별 통계 조회(두가지 방법 연습)
	List<StatisticsByCategoryVO> getSaleStatusByCategory();
	List<Map<String, Object>> getSaleStatusByCategory2();
	
	//주문관리 - 전체 목록 조회
	List<OrderStatusVO> getOrderListPage(OrderStatusVO orderStatusVO); //페이징 처리 버전
	//주문관리 - 주문상태정보에 따른 목록 조회
	List<OrderStatusVO> getOrderListByStatus(int statusCode);
	//주문관리 - 주문상태정보 목록(매개변수로 사용할) 조회
	List<StatusInfoVO> getStatusInfoList();
	
	//주문관리 - 전체 주문 개수 조회 (페이징 처리)
	int getBuyCntForOrderManage(OrderStatusVO orderStatusVO);
	
	//주문관리 - 주문 상태 변경
	int changeStatus(Map<String, Object> map);
	
	//주문관리 - 상세 구매정보 조회
	List<BuyDetailVO> getBuyDetailList(String buyCode);

}
