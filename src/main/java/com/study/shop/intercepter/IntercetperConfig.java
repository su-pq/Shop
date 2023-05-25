package com.study.shop.intercepter;

import org.codehaus.groovy.transform.AutoCloneASTTransformation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class IntercetperConfig implements WebMvcConfigurer{

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		//현재는 각각 pre, post이기 때문에 order가 기능하지 못함. 어떻게 사용하는지만 봐두기.
		//주의 : redirect시에도 intercepter 실행
		
		//관리자 메뉴
		registry.addInterceptor(getMenuIntercepter())
				.order(2)
				.addPathPatterns("/admin/cateManage"
								, "/admin/regItem"
								, "/admin/itemManage"
								, "/admin/searchItemList"
								, "/admin/updateItem"
								, "/admin/memberManage"
								, "/admin/authoManage"
								, "/admin/orderManage"
								, "/admin/saleStatusPerMonth"
								, "/admin/saleStatusByCategory")
				.excludePathPatterns("/admin/regItemProcess");
		
		//사용자 카테고리
		registry.addInterceptor(getCategoryIntercepter())
				.order(3)
				.addPathPatterns("/item/**"
								, "/cart/**"
								, "/buy/**"
								, "/member/updateMemberForm"
								, "/member/loginForm")
				.excludePathPatterns("/**/*Ajax");
		
	}
	
	//menuIntercepter 객체 생성 메소드
	@Bean //Bean : 객체. 리턴되는 데이터를 객체로 생성 (프로젝트를 시작하자마자 제일 먼저 실행된다.)
	public MenuIntercepter getMenuIntercepter() {
		return new MenuIntercepter();
	}
	
	@Bean
	public CategoryIntercepter getCategoryIntercepter() {
		return new CategoryIntercepter();
	}
	
	
}
