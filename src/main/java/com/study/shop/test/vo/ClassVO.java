package com.study.shop.test.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ClassVO {
	//js에서 teacher_name이라는 값이 넘어오면 teacherName에 담겠다.
	@JsonProperty("teacher_name")
	private String teacherName;
	@JsonProperty("class_name")
	private String className;
	@JsonProperty("stu_info")
	private List<StudentVO> stuInfo;
}
