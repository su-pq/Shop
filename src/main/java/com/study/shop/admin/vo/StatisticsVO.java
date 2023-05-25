package com.study.shop.admin.vo;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class StatisticsVO {
	private int month01;
	private int month02;
	private int month03;
	private int month04;
	private int month05;
	private int month06;
	private int month07;
	private int month08;
	private int month09;
	private int month10;
	private int month11;
	private int month12;
	
	//객체가 가진 데이터를 List로 리턴
	public List<Integer> getDataToList() {
		//Reflection으로 변경하기
		List<Integer> list = new ArrayList<>();
		list.add(getMonth01());
		list.add(getMonth02());
		list.add(getMonth03());
		list.add(getMonth04());
		list.add(getMonth05());
		list.add(getMonth06());
		list.add(getMonth07());
		list.add(getMonth08());
		list.add(getMonth09());
		list.add(getMonth10());
		list.add(getMonth11());
		list.add(getMonth12());
		
		return list;
		
	}
	public List<Integer> getDataToListReflection() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Reflection으로 변경하기
		List<Integer> list = new ArrayList<>();
		
		// Reflection을 사용하여 getMonthXX() 메소드들을 호출하여 데이터를 리스트에 추가
        Class<?> cls = this.getClass();
        for (int i = 1; i <= 12; i++) {
                Method method = cls.getDeclaredMethod("getMonth" + String.format("%02d", i));
                int value = (int) method.invoke(this);
                list.add(value);
        }
		
		return list;
		
	}
}
