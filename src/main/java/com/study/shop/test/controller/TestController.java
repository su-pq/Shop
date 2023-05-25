package com.study.shop.test.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.shop.admin.service.AdminService;
import com.study.shop.admin.vo.MenuVO;
import com.study.shop.admin.vo.SubMenuVO;
import com.study.shop.intercepter.MenuIntercepter;
import com.study.shop.item.service.ItemService;
import com.study.shop.item.vo.CategoryVO;
import com.study.shop.test.vo.ClassVO;
import com.study.shop.test.vo.StudentVO;
import com.study.shop.test.vo.TestVO;

import jakarta.annotation.Resource;

@Controller
@RequestMapping("/test")
public class TestController {
	@Resource(name = "itemService")
	private ItemService itemService;
	
	@Resource(name = "adminService")
	private AdminService adminService;
	
	@GetMapping("/map1")
	public String map1(Model model) {
		//각 List 데이터
		List<CategoryVO> cateList = itemService.getCateListInUse();
		List<MenuVO> menuList = adminService.getAdminMenuList();
		List<SubMenuVO> subMenuList = adminService.getAdminSubMenuList("MENU_001");
		//Map에 데이터 넣기
		Map<String, List> mapData = new HashMap<>();
		mapData.put("cateList", cateList);
		mapData.put("menuList", menuList);
		mapData.put("subMenuList", subMenuList);
		//Map 데이터 이동 페이지로 전달
		model.addAttribute("mapData", mapData);
		
		return "test/map1";
	}
	
	//====================================================================
	
	@GetMapping("/test1")
	public String test1() {
		return "test/json_test";
	}
	@ResponseBody
	@PostMapping("/test1")
	public void test1Ajax(String name, int age, String addr, TestVO testVO) {
		System.out.println("test1Ajax 메소드 실행~~");
		System.out.println(name);
		System.out.println(age);
		System.out.println(addr);
		System.out.println(testVO);
	}
	@ResponseBody
	@PostMapping("/test2")
	public void test2Ajax(@RequestBody TestVO testVO) {//문자화된 json 데이터를 받기 위해서는 @RequestBody 어노테이션을 사용해야 한다.
		System.out.println("test2Ajax 메소드 실행~~");
		//System.out.println(name);
		//System.out.println(age);
		//System.out.println(addr);
		System.out.println(testVO);
	}
	@ResponseBody
	@PostMapping("/test3")
	public void test3Ajax(@RequestBody HashMap<String, String> map) {
		System.out.println("test3Ajax 메소드 실행~~");
		System.out.println(map);
		System.out.println(map.get("name"));
		System.out.println(map.get("age"));
		System.out.println(map.get("addr"));
		
		//map으로 받은 데이터를 자바 객체로 변환하는 기본 방식
		TestVO testVO = new TestVO();
		testVO.setName(map.get("name"));
		testVO.setAge(Integer.parseInt(map.get("age")));
		testVO.setAddr(map.get("addr"));
		
		System.out.println("!!!!!!!!!!!!1");
		//map으로 받은 데이터를 자바 객체로 변환하는 두번째 방식
		ObjectMapper mapper = new ObjectMapper();
		// 첫번째 매개변수 : 내가 읽어올 데이터 (map에 있는 데이터를 읽겠다.)
		// 두번째 매개변수 : 
		TestVO result = mapper.convertValue(map, TestVO.class);
		System.out.println(result);
	}
	@ResponseBody
	@PostMapping("/test4")
	public void test4Ajax(@RequestBody List<TestVO> list) {
		System.out.println("test4Ajax 메소드 실행~~");
		System.out.println(list);
  	}
	@ResponseBody
	@PostMapping("/test5")
	public void test5Ajax(List<TestVO> dataArr) {
		System.out.println("test5Ajax 메소드 실행~~");
		System.out.println(dataArr);
	}
	@ResponseBody
	@PostMapping("/test6")
	public void test6Ajax(@RequestBody List<HashMap<String, String>> mapList) {
		System.out.println("test6Ajax 메소드 실행~~");
		System.out.println(mapList);
	}
	@ResponseBody
	@PostMapping("/test7")
	public void test7Ajax(@RequestBody ClassVO classVO) {
		System.out.println("test7Ajax 메소드 실행~~");
		System.out.println(classVO);
	}
	@ResponseBody
	@PostMapping("/test8")
	public void test8Ajax(@RequestBody HashMap<String, Object> map) {
		System.out.println("test8Ajax 메소드 실행~~");
		System.out.println(map);
		System.out.println(map.get("cName"));
		System.out.println(map.get("tName"));
		System.out.println(map.get("stuInfo"));
		
		//데이터는 json 형식의 문자열로 보내므로 받을 때는 형변환해줘야 한다.
		ObjectMapper mapper = new ObjectMapper();
		// stuInfo는 StudentVO가 3개 들어있으므로 배열 형식으로 받아올 수 있다.
		StudentVO[] arr = mapper.convertValue(map.get("stuInfo"), StudentVO[].class);
		//배열을 리스트로 변경해 사용하면 된다.
		List<StudentVO> stuList = Arrays.asList(arr);
		
	}
	
	
	
	
	
	
	
	
	
}
