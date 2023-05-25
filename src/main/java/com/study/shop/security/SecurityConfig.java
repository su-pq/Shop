package com.study.shop.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity security) throws Exception {
		security.csrf().disable()
				.authorizeHttpRequests()
					.requestMatchers("/",
									"/item/itemList",
									"/item/itemDetail",
									"/member/join",
									"/member/isDuplicateMemId",
									"/member/loginForm",
									"/member/login",
									"/member/findPwAjax",
									"/logout"
									).permitAll()
					.requestMatchers("/admin/**").hasRole("ADMIN")
					//매개변수의 권한 중 하나라도 있으면 페이지 접근 허가
					//.requestMatchers("/admin/**").hasAnyRole("ADMIN", "MANAGER")
					.anyRequest().authenticated()
				.and()
					.formLogin()
					.loginPage("/member/loginForm")
					.loginProcessingUrl("/member/login")
					.usernameParameter("memId")
					.passwordParameter("memPw")
					.successHandler(getSuccessHandler())
					//로그인 실패시 추가 작업
					//실패했을 때 실행되는 클래스 객체를 넣으면 해당 클래스가 실행
					.failureHandler(getFailureHandler())
					.permitAll()
				.and()
					.logout()
					.invalidateHttpSession(true)//세션이 있는 데이터 지움
					.logoutUrl("/logout")
					.logoutSuccessUrl("/item/itemList")
				.and()
					.exceptionHandling()//예외 핸들링
					.accessDeniedPage("/accessDeny")//엑세스가 거부되었을 때(페이지 권한이 없을 때) 찾아갈 페이지
					
				;
		
		return security.build();
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	//js, css, img 파일을 로그인하지 않아도 사용할 수 있도록 설정
	@Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
		// 아래 내가 나열하는 경로에는 시큐리티 인증을 사용하지 않겠다.
        return (web) -> web.ignoring().requestMatchers("/js/**", "/css/**", "/img/**", "/upload/**");
    }
	
	//로그인 실패시 실행되는 클래스 객체 생성 메소드
	@Bean
	public FailureHandler getFailureHandler() {
		return new FailureHandler();
	}
	
	@Bean
	public SuccessHandler getSuccessHandler() {
		return new SuccessHandler();
	}
}
