package com.study.shop.util;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class MailVO {
	private String title;
	//메일 수신자는 여러명일 수 있으므로 list로 선언
	private List<String> recipientList;
	private String content;
}
