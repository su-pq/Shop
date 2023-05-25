package com.study.shop.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service("mailService")
public class MailService {
	//gradle에 추가한 코드로 인해 사용 가능한 객체
	@Autowired
	private JavaMailSender javaMailSender;
	
	//템플레이트엔진의 일종 : 타임리프
	//템플레이트엔진 객체를 사용하면 html을 바로 보낼 수 있다.
	@Autowired
	private SpringTemplateEngine templateEngine;
	
	
	//단순 문자 메일 보내기
	public void sendSimpleEmail(MailVO mailVO) {
		//단순 문자 메일을 보낼 수 있는 객체 생성
		SimpleMailMessage message = new SimpleMailMessage();
		message.setSubject(mailVO.getTitle()); //메일제목
		//한명 이상에게 메일 발송
		//emailList의 크기와 같은 크기의 배열로 변경해서 setTo한다.
		message.setTo(mailVO.getRecipientList().toArray(new String[mailVO.getRecipientList().size()])); //메일을 받을 사람
		message.setText(mailVO.getContent()); //메일 내용
		
		
		//메일 발송
		javaMailSender.send(message);
		
		//사용 - controller에서 mailService.sendSimpleEmail로 실행
	}
	
	//html 메일 보내기(디자인 가능)
	public void sendHTMLEmail() {
		MimeMessage message = javaMailSender.createMimeMessage();
		String password = createRandomPw();
		
		try {
			message.setSubject("[BOOK SHOP] HTML 이메일 발송");
			//파일 내용, 인코딩방식, 파일 형식 설정
			message.setText(setContext(password), "UTF-8", "html");
			message.addRecipients(MimeMessage.RecipientType.TO, "kkssy9620@naver.com");
			javaMailSender.send(message);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	//6자리의 랜덤 비밀번호 생성
	public String createRandomPw() {
		String[] charSet = new String[]{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z", 
				"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
				"o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
		
		//랜덤한 인덱스 생성
		String imsiPw = "";
		for(int i=0; i<6; i++) {
			int randIndex = (int)(Math.random() * charSet.length); //0 <= x < 1 실수 리턴 0~0.9999
			imsiPw += charSet[randIndex];
		}
		
		return imsiPw;
	}
	
	//HTML 메일 보낼 시 내용 세팅
	public String setContext(String password) {
		//context = html이라고 생각하면 된다.
		Context context = new Context();
		//변수를 설정하겠다.
		context.setVariable("password", password);
		//타임리프 중에서 mail.html을 다루겠다.(경로가 있다면 경로로 넣어야 함)
		//mail.html에 password라는 변수에 매개변수password를 넣어주겠다.
		//mail.html을 리턴하는데, html의 password 변수에 매개변수 password를 넣어 리턴하겠다.
		return templateEngine.process("mail", context);
	}
}
