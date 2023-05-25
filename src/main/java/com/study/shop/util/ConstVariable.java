package com.study.shop.util;


//상수들만 모아 관리하는 클래스
public class ConstVariable {
	// 첨부파일 저장경로
	// final : 상수로 설정 (더 이상 값이 바뀌지 않도록)
	// \가 두개씩 나오는 이유 : 특수문자를 문자열로 인식시키고 싶은 경우 앞에 \를 붙이기 때문에 자동으로 붙여줌
	// static : 객체를 만들지 않아도 사용 가능.
	// 파일 경로 붙여넣기 후 \\를 붙여 Controller 에서 파일 업로드 기능을 실행할 때 뒤에 파일명이 붙어 경로가 완성될 수 잇도록(\\upload\파일명.jpg)
	public static final String UPLOAD_PATH = "C:\\dev\\workspace\\workspaceSTS\\Shop\\src\\main\\resources\\static\\upload\\";


	//기본 관리자 메뉴 코드
	public static final String DEFAULT_MENU_CODE = "MENU_001";
	
	//상품관리 메뉴의 기본 서브 메뉴 코드
	public static final String DEFAULT_SUB_MENU_CODE_1 = "SUB_MENU_001";

	//회원관리 메뉴의 기본 서브 메뉴 코드
	public static final String DEFAULT_SUB_MENU_CODE_2 = "SUB_MENU_004";

	//주문관리 메뉴의 기본 서브 메뉴 코드
	public static final String DEFAULT_SUB_MENU_CODE_3 = "SUB_MENU_006";
	
	
	//
	public static final String STATUS_INFO_1 = "신규 주문 내역";
	public static final String STATUS_INFO_2 = "주문 확인 내역";
	public static final String STATUS_INFO_3 = "배송 준비 중 내역";
	public static final String STATUS_INFO_4 = "배송 중 내역";
	public static final String STATUS_INFO_5 = "배송 완료 내역";
	

}
