package com.study.shop.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.print.CancelablePrintJob;

//날짜 관련 기능을 가지는 DateUtill 클래스
public class DateUtil {
	//오늘 날짜를 문자열로 리턴
	//객체를 생성하지 않고 객체명.변수명으로 바로 접근할 수 있도록 static으로 생성
	public static String getNowDateToString() {
		//달력 역할을 하는 Calendar 클래스 (java.util의)
		//Calendar는 싱글톤 패턴으로 만들어져 있음.(싱글톤 패턴 : 아래 설명)
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//년 2023
		//Calendar.MONTH는 내부적으로 배열로 만들어져 있어서 0부터 시작하기 때문에 
		//현재 달에서 -1한 값이 나오므로 +1시켜서 사용해야 한다.
		int month = cal.get(Calendar.MONTH) + 1;//월 3+1
		int date = cal.get(Calendar.DATE);//일 12
		
		//2023-4-12
		//type이 date인 input태그의 value에는 무조건 yyyy-mm-dd 형식으로 들어와야 한다.(4글자-2글자-2글자)
		//String.format (맞출 형식, 사용할 데이터)
		//%02d : 형식을 정수 2자리로 맞추고, 자리수가 부족할 경우 0으로 채운다.
		return year + "-" + String.format("%02d", month) + "-" + String.format("%02d", date);
	}; 

	public static String getNowDateToString(String seperator) {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//년 2023

		int month = cal.get(Calendar.MONTH) + 1;//월 3+1
		int date = cal.get(Calendar.DATE);//일 12
		// "-" 대신 seperator 사용 2000-12-12
		return year + seperator + String.format("%02d", month) + seperator + String.format("%02d", date);
	}; 
	//이달의 첫번재 날 가져오기
	public static String getFirstDateOfMonth() {
		return getNowDateToString().substring(0, 8) + "01";
	}
	//현재 년도 가져오기
	public static int getNowYear() {
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR);//년 2023
		return year;
	}
	/*
	 * //현재 년도 -5년 가져오기 public static List<Integer> get5Year(){ List<Integer>
	 * orderManageYear = new ArrayList<>();
	 * 
	 * Calendar cal = Calendar.getInstance(); int year = cal.get(Calendar.YEAR);
	 * 
	 * for(int i=0; i<5; i++) { orderManageYear.add((year - i)); }
	 * 
	 * return orderManageYear; }
	 */
	
	
	
}


//싱글톤 패턴
/*
 * 일반적인 클래스의 생성자는 다른 클래스에서 객체를 생성할 수 있도록 public으로 생성한다. 
 * 이런 경우, 객체를 여러개 생성 가능하다.
 * 하지만 Calendar라는 클래스는 생성자가 private로 생성되어 있다. 
 * 객체를 여러개를 만들 필요가 없는 클래스는 하나만 만들어서 사용하도록 private로 사용한다. 
 * 이러한 패턴을 싱글톤 패턴이라고 한다.
 * */
class AAA{
	//객체 생성
	static AAA a = new AAA();
	
	//생성자
	private AAA() {}
	//AAA의 객체를 리턴하는 메소드 getAAA
	public static AAA getAAA() {
		//AAA 객체를 하나만 생성하도록 하는 코드
		if(a == null) {
			return new AAA();
		}else {
			return a;			
		}
	}
}

class BBB{
	//getAAA가 static이므로 AAA.getAAA()로 접근
	AAA a1 = AAA.getAAA(); // AAA 객체를 생성해 돌려줌
	AAA a2 = AAA.getAAA(); // 이미 만들어져 있는 AAA를 돌려줌
	AAA a3 = AAA.getAAA(); // 이미 만들어져 있는 AAA를 돌려줌
}
