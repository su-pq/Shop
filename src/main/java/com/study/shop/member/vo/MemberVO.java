package com.study.shop.member.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MemberVO {
	private String memId;
	private String memPw;
	private String memName;
	private String memEmail;
	private String gender;
	private String memTell;
	private String[] memTells;
	private String memAddr;
	private String addrDetail;
	private String memRole;
	private int memStatus;
	private String joinDate;
	

	
}
