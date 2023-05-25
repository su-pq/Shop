package com.study.shop.util;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PageVO {
	//현재 선택된 페이지 번호를 담는 nowPage ( '이전 1 2 3 4 5 다음' 중 선택한 값) 
		private int nowPage;
		//전체 데이터 수(전체 게시글 수) 
		private int totalDataCnt;
		//한 페이지에 10개씩 보여주고 전체 데이터가 150개가 있다고 가정하면 페이지 번호가 1~16번까지 있어야 한다.
		//따라서 1 2 3 4 5 에서 '다음'을 누르면 6 7 8 9 10 으로 선택 가능한 페이지 번호를 설정할 변수가 필요하다.
		// 화면에 보이는 첫번째 페이지 번호
		private int beginPage;
		// 화면에 보이는 마지막 페이지 번호
		private int endPage;
		//한 페이지에 보여지는 데이터(게시글) 수 (한 페이지에 게시글을 10개씩 보여주겠다.)
		private int displayCnt;
		//한 번에 보여지는 페이지 수 (이전 1 2 3 4 5 6 7 8 9 10 다음 일 경우의 displayPageCnt = 10)
		private int displayPageCnt;
		//'이전' 버튼의 유무(첫 페이지가 포함될 경우 false)
		private boolean prev;
		//'다음' 버튼의 유무(마지막 페이지가 포함될 경우 false)
		private boolean next;
		//시작rowNum
		private int startNum;
		//마지막 RowNum
		private int endNum;
		
		//생성자로 기본값 세팅
		public PageVO() {
			nowPage = 1;
			displayCnt = 5;
			displayPageCnt = 5;
		}
		//이 메소드 실행시 페이지 처리를 위한 모든 변수값을 세팅
		public void setPageInfo() {
			//마지막에 보이는 페이지 번호 // 3/5 = 0.~~~(올림해서 1) * 5 = 5 // 9/5.0 = 1.~~~(올림해서 2)*5 = 10
			endPage = displayPageCnt * (int)Math.ceil(nowPage / (double)displayPageCnt);
			//처음에 보이는 페이지 번호
			beginPage = endPage - displayPageCnt + 1;
			//전체 페이지 수  // 156/5 = 31.1(올림해서 32)
			int totalPageCnt = (int)Math.ceil(totalDataCnt / (double)displayCnt);
			//next 버튼 유무
			if(endPage < totalPageCnt) {
				next = true;
			}else {
				next = false;
				endPage = totalPageCnt;
			}
			//prev 버튼 유무
			prev = beginPage == 1 ? false : true;
			//검색의 시작과 마지막 ROW_NUM // 1(1~10) 2 (11~20) 3(21~30)
			startNum = (nowPage - 1) * displayCnt + 1 ;
			endNum = nowPage * displayCnt;
		}
}
