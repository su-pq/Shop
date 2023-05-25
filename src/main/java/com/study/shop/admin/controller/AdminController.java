package com.study.shop.admin.controller;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.study.shop.admin.service.AdminService;
import com.study.shop.admin.vo.OrderStatusVO;
import com.study.shop.admin.vo.PageVO;
import com.study.shop.admin.vo.StatisticsByCategoryVO;
import com.study.shop.admin.vo.StatisticsVO;
import com.study.shop.admin.vo.StatusInfoVO;
import com.study.shop.admin.vo.SubMenuVO;
import com.study.shop.buy.vo.BuyDetailVO;
import com.study.shop.buy.vo.BuyVO;
import com.study.shop.item.service.ItemService;
import com.study.shop.item.vo.CategoryVO;
import com.study.shop.item.vo.ImgVO;
import com.study.shop.item.vo.ItemVO;
import com.study.shop.util.ConstVariable;
import com.study.shop.util.DateUtil;
import com.study.shop.util.UploadUtil;

import groovyjarjarantlr4.v4.parse.GrammarTreeVisitor.mode_return;
import jakarta.annotation.Resource;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Resource(name = "adminService")
	AdminService adminService;
	@Resource(name = "itemService")
	ItemService itemService;
	
	//카테고리 관리 페이지
	@GetMapping("/cateManage")
	public String cateManage(Model model, SubMenuVO subMenuVO) {
		subMenuVO.setMenuInfo(ConstVariable.DEFAULT_MENU_CODE, ConstVariable.DEFAULT_SUB_MENU_CODE_1);
		
		model.addAttribute("cateList", adminService.getCateListForAdmin());
		return "content/admin/cate_manage";
	}
	
	//카테고리 등록
	@ResponseBody
	@PostMapping("/regCateAjax")
	public int regCategory(String cateName) {
		return adminService.regCate(cateName);
	}
	
	//카테고리명 중복 체크
	@ResponseBody
	@PostMapping("/checkCateNameAjax")
	public int checkCateName(String cateName) {
		return adminService.checkCateName(cateName);
	}
	//등록한 후의 카테고리 정보 가져오기
	@ResponseBody
	@PostMapping("/getCateListAjax")
	public List<CategoryVO> getCateListAjax() {
		return adminService.getCateListForAdmin();
	}
	@ResponseBody
	@PostMapping("/updateIsUseAjax")
	public int updateIsUse(String cateCode) {
		return adminService.updateIsUse(cateCode);
	}
	
	@GetMapping("/deleteCate")
	public String deleteCate(String cateCode) {
		System.out.println("컨트롤러 도착");
		adminService.deleteCate(cateCode);
		System.out.println("쿼리 실행");
		return "redirect:/admin/cateManage";
	}
	
	//상품 등록 페이지
	@GetMapping("/regItem")
	public String regItemForm(Model model, SubMenuVO subMenuVO) {
		//상품 등록 후 redirect시의 오류 방지
		subMenuVO.setMenuInfo("MENU_001", "SUB_MENU_002");
		
		//사용중인 카테고리 목록 조회
		model.addAttribute("cateList", itemService.getCateListInUse());
		
		return "content/admin/reg_item";
	}
	//상품 등록(redirect시에도 intercepter 실행)
	@PostMapping("/regItemProcess")
	public String regItem(ItemVO itemVO, MultipartFile mainImg, MultipartFile[] subImg) { // mainImg : html의 name값
		//등록될 상품코드 조회
		String itemCode = adminService.getNextItemCode();
		itemVO.setItemCode(itemCode);
		
		//---파일 첨부---//
		//---메인 이미지 첨부---//
		ImgVO attachedImgVO = UploadUtil.uploadFile(mainImg);
		//---서브 이미지들 첨부---//
		List<ImgVO> attachedImgList = UploadUtil.multiFileUpload(subImg);
		
		//업로드될 이미지 정보 세팅
		List<ImgVO> imgList = attachedImgList;
		imgList.add(attachedImgVO);
		for(ImgVO e : imgList) {
			e.setItemCode(itemVO.getItemCode());
		}
		itemVO.setImgList(imgList);
		
		
		
		//상품 등록 쿼리 실행
		adminService.regItem(itemVO);
		
		return "redirect:/admin/regItem";
	}
	//상품 관리 페이지
	// @RequestMapping 어노테이션으로 요청을 받으면 get, post 둘 다 받을 수 잇음.
	@GetMapping("/itemManage")
	public String stockManage(Model model, SubMenuVO subMenuVO, ItemVO itemVO) {
		subMenuVO.setMenuInfo("MENU_001", "SUB_MENU_003");
		model.addAttribute("cateList", adminService.getCateListForAdmin());
		model.addAttribute("itemList", adminService.getItemListForItemManage(itemVO));
		
		return "content/admin/item_manage";
	}
	//상품목록 검색
	@PostMapping("/searchItemList")
	public String searchItemList(Model model, ItemVO itemVO, SubMenuVO subMenuVO) {
		subMenuVO.setMenuInfo("MENU_001", "SUB_MENU_003");		
		model.addAttribute("cateList", adminService.getCateListForAdmin());
		model.addAttribute("itemList", adminService.getItemListForItemManage(itemVO));
		return "content/admin/item_manage";
	}
	
	@ResponseBody
	@PostMapping("/getItemInfoAjax")
	public Map<String, Object> getItemInfoAjax(String itemCode) {
		//상품 상세 데이터 + 카테고리 목록 데이터 리턴
		List<CategoryVO> cateList = itemService.getCateListInUse();
		ItemVO item =  adminService.getItemForItemManage(itemCode);
		Map<String, Object> mapData = new HashMap<>();
		mapData.put("item", item);
		mapData.put("cateList", cateList);
		return mapData;
	}
	
	@PostMapping("/updateItem")
	public String updateItem(ItemVO itemVO) {
		//item 업데이트
		adminService.updateItem(itemVO);
		
		//이미지 업데이트
		return "redirect:/admin/itemManage?menuCode=MENU_001&subMenuCode=SUB_MENU_003";
	}
	
	//회원관리 페이지
		@GetMapping("/memberManage")
		public String memberManage(SubMenuVO subMenuVO) {
			subMenuVO.setMenuInfo("MENU_002", ConstVariable.DEFAULT_SUB_MENU_CODE_2);
			
			return "content/admin/member_manage";
		}
	//권한관리 페이지
	@GetMapping("/authoManage")
	public String authoManage(SubMenuVO subMenuVO) {
		subMenuVO.setMenuInfo("MENU_002", "SUB_MENU_005");
		
		return "content/admin/autho_manage";
	}
		//주문관리 페이지
		@RequestMapping("/orderManage")
		public String orderManage(SubMenuVO subMenuVO, Model model, OrderStatusVO orderStatusVO) {
			subMenuVO.setMenuInfo("MENU_003", ConstVariable.DEFAULT_SUB_MENU_CODE_3);
			
			//페이지 정보 세팅
			orderStatusVO.setTotalDataCnt(adminService.getBuyCntForOrderManage(orderStatusVO));
			orderStatusVO.setNowPage(orderStatusVO.getNowPage());
			orderStatusVO.setPageInfo();
			
			
			
			//정렬기준이 없다면 기본값으로 날짜순으로 정렬하도록 세팅
			if(orderStatusVO.getOrderBy() == null) {
				orderStatusVO.setOrderBy("BUY_DATE_ORDER");
			}
			
			//주문 목록 조회
			model.addAttribute("orderListPage", adminService.getOrderListPage(orderStatusVO));
			
			//주문 상태별 주문 정보를 모두 담을 수 있는 map
			Map<Integer, List<OrderStatusVO>> orderStatusMap = new TreeMap<>();
			
			//주문 상태별 주문 정보
			List<StatusInfoVO> statusInfoList = adminService.getStatusInfoList();
			for(StatusInfoVO statusInfo : statusInfoList) {
				orderStatusMap.put(statusInfo.getStatusCode(), adminService.getOrderListByStatus(statusInfo.getStatusCode()));
			}

			//상태별 주문 정보 보내기
			model.addAttribute("orderStatusMap", orderStatusMap);
			//상태별 테이블의 제목 정보 보내기
			model.addAttribute("titleMap", getStatusInfoTitle());
			//버튼의 글자 정보 보내기(상태 정보)
			model.addAttribute("statusInfoList", statusInfoList);
			
			return "content/admin/order_manage";
		}
	
		//주문관리 - 주문상태 변경
		@ResponseBody
		@PostMapping("/changeStatusAjax")
		public Map<String, List<OrderStatusVO>> changeStatusAjax(@RequestBody HashMap<String, Object> mapData, Authentication authentication) {
			System.out.println("statusCode = " + mapData.get("statusCode"));
			System.out.println("orderNumLsit = " + mapData.get("orderNumList"));
			User user = (User)authentication.getPrincipal();
			
			mapData.put("memId", user.getUsername());
			//주문상태 변경
			adminService.changeStatus(mapData);
			
			
			//변경 후 화면에 보여줘야 할 리스트 목록 2개 조회(상태가 변경된 2개)
			int afterStatusCode = Integer.parseInt(mapData.get("statusCode").toString());
			//int afterStatusCode = (Integer)mapData.get("statusCode");
			int beforeStatusCode = (Integer)mapData.get("statusCode")-1;
			
			Map<String, List<OrderStatusVO>> result = new HashMap<>();
			result.put("firstOrderList", adminService.getOrderListByStatus(beforeStatusCode));
			result.put("secondOrderList", adminService.getOrderListByStatus(afterStatusCode));
			
			return result;
		}
		
		@ResponseBody
		@PostMapping("/getBuyDetailListAjax")
		public List<BuyDetailVO> getBuyDetailListAjax(String buyCode){
			return adminService.getBuyDetailList(buyCode);
		}
		
	
	//월별 매출 현황 페이지
	@GetMapping("/saleStatusPerMonth")
	public String saleStatusPerMonth(SubMenuVO subMenuVO, Model model,@RequestParam(required = false, defaultValue = "0") int year) {
		//▲▲▲ year라는 이름으로 들어오는 데이터가 없으면 null이 들어오는데, 자료형이 int이므로 자료형 불일치 오류
		//RequestParam 어노테이션을 사용해야 함. 기본값 : (required=true) : 무조건 값이 넘어옴. (required=false) : 넘어올 때도, 안 넘어올 때도 있음
		//defaultValue에는 문자열만 들어올 수 있는데, 자료형이 int고, 숫자로 변경 가능한 값은 int로 변경해 넣는다.
		
		//임시코드
		subMenuVO.setMenuInfo("MENU_003", "SUB_MENU_007");
		
		if(year == 0) {
			year = DateUtil.getNowYear();
		}
		
		List<Map<String, Integer>> mapList = adminService.getSaleStatusPerMonth2(year);
		
		List<Map<String, Integer>> resultList = new ArrayList<>();
		for(Map<String, Integer> map : mapList) {
			//정렬되도록 treeMap 생성
			//()안에 map을 넣으면 map의 데이터를 갖는 새로운 map1을 생성
			Map<String, Integer> map1 = new TreeMap<>(map);
			resultList.add(map1);
			
			//map에 들어있는 모든 키 값
			Set<String> keySet = map1.keySet();
			/* 데이터 확인
			 * for(String key : keySet) { System.out.println("key : " + key);
			 * System.out.println("value : " + map1.get(key)); ; } System.out.println();
			 */
		}
		model.addAttribute("mapList", resultList);
		model.addAttribute("year", year);
		model.addAttribute("nowYear", DateUtil.getNowYear());
		
		return "content/admin/sale_status_per_month";
	}
	
	@ResponseBody
	@PostMapping("/getChartDataAjax")
	public Map<String, List<Integer>> getChartDataAjax(int year) {
		
		
		List<StatisticsVO> list = adminService.getSaleStatusPerMonth(year);
				
		//판매건수 List
		List<Integer> cntList = list.get(0).getDataToList();
		
		//판매금액 List
		List<Integer> saleList = list.get(1).getDataToList();

		Map<String, List<Integer>> map = new HashMap<>();
		map.put("cntList", cntList);
		map.put("saleList", saleList);
		
		return map;
		
	}
	//카테고리별 통계 페이지 이동
	@GetMapping("/saleStatusByCategory")
	public String saleStatusByCategory(SubMenuVO subMenuVO) {
		return "content/admin/sale_status_by_category";
	}
	@ResponseBody
	@PostMapping("/getSaleStatusByCategoryAjax")
	//public Map<String, List<String>> getSaleStatusByCategoryAjax(){ //vo로 가져오는 방법
	public List<Map<String, Object>> getSaleStatusByCategoryAjax(){   //map으로 가져오는 방법
		//=============== vo로 쿼리 실행결과 가져올 경우 ===================//
		 List<StatisticsByCategoryVO> statisticsByCategoryList = adminService.getSaleStatusByCategory();
		 
		 //cateName리스트
		 List<String> cateNameList = new ArrayList<>();
		 for(StatisticsByCategoryVO vo : statisticsByCategoryList) {
			cateNameList.add(vo.getCateName()); 
		 }
		 
		 //saleCnt 리스트
		 List<String> saleCntList = new ArrayList<>();
		 for(StatisticsByCategoryVO vo : statisticsByCategoryList) {
			 saleCntList.add(vo.getSaleCnt());
		 }
		 
		 Map<String, List<String>> map = new HashMap<>();
		 map.put("cateNameList", cateNameList);
		 map.put("saleCntList", saleCntList);
		 //=============== map으로 쿼리 실행결과 가져올 경우(쌤) ===================//
		 List<Map<String, Object>> mapList = adminService.getSaleStatusByCategory2();
		 //Map<String, List<String>> map2 = new HashMap<>();
		 
		 return mapList;
	}
	
	
	//===========기능을 위한 메소드============
	
	//상태별 테이블의 제목 리턴
	public Map<Integer, String> getStatusInfoTitle() {
		Map<Integer, String> titleMap = new HashMap<>();
		titleMap.put(1, ConstVariable.STATUS_INFO_1);
		titleMap.put(2, ConstVariable.STATUS_INFO_2);
		titleMap.put(3, ConstVariable.STATUS_INFO_3);
		titleMap.put(4, ConstVariable.STATUS_INFO_4);
		titleMap.put(5, ConstVariable.STATUS_INFO_5);
		
		return titleMap;
	}
	
}
