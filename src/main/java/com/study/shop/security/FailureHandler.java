package com.study.shop.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//로그인 실패 시 자동으로 실행되는 클래스
//SimpleUrlAuthenticationFailureHandler : 로그인 실패시 자동으로 실행되는 메소드가 선언되어 잇음.

public class FailureHandler extends SimpleUrlAuthenticationFailureHandler{

	//AuthenticationException : 왜 로그인이 실패했는지에 대한 정보를 가짐
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {
		
		//에러 메세지를 담을 변수
		String eMsg = "";
		
		//instanceof : 
		//예외의 종류에 따라 예외가 발생되는 클래스가 달라진다.
		//계정 정보가 잘못된 경우
		if(exception instanceof BadCredentialsException) {
			eMsg = "아이디 혹은 비밀번호를 확인하세요";
		//계정이 아예 없을 경우
		}else if(exception instanceof UsernameNotFoundException) {
			eMsg = "계정이 존재하지 않습니다.";
		//그 외의 나머지 오류 발생 시
		}else {
			eMsg = "알 수 없는 이유로 로그인에 실패했습니다.";
		}
		//로그인 실패 시 입력했던 아이디 데이터
		String memId = request.getParameter("memId");
		//응답할 때 어떤 글자를 작성해서 가져가겠다.
		PrintWriter p =  response.getWriter();
		p.write("fail");
		p.flush();
		
		
		
	}
	
}
