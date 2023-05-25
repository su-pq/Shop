//시작하자마자 세팅


//날짜 세팅
/*
function setDate(){
	const today = new Date();
	const firstDayOfMonth = new Date(today.getFullYear(), today.getMonth(), 1);
	
	const yearFirstDayOfMonth = firstDayOfMonth.getFullYear();
	const monthFirstDayOfMonth = firstDayOfMonth.getMonth() + 1; // 월은 0부터 시작하므로 +1
	const dayFirstDayOfMonth = firstDayOfMonth.getDate();
	const formattedFirstDayOfMonth = `${yearFirstDayOfMonth}-${String(monthFirstDayOfMonth).padStart(2, '0')}-${String(dayFirstDayOfMonth).padStart(2, '0')}`;
	
	const yearToday = today.getFullYear();
	const monthToday = today.getMonth() + 1; // 월은 0부터 시작하므로 +1
	const dayToday = today.getDate();
	const formattedToday = `${yearToday}-${String(monthToday).padStart(2, '0')}-${String(dayToday).padStart(2, '0')}`;
	
	//태그에 날짜 설정
	const startDateInput = document.querySelector('#startDate');
	const endDateInput = document.querySelector('#endDate');
	
	startDateInput.value = formattedFirstDayOfMonth;
	endDateInput.value = formattedToday;
}
*/

//전체펼치기 버튼 클릭시 실행
function toggle_all(btn){
	//현재 상태값 들고오기
	const status = btn.dataset.toggleStatus;
	
	//속성값이 변경되어야 하는 모든 태그가 있는 div 태그 선택
	const all_div = document.querySelectorAll('.accordion-item');
	
	//close일 경우 전체 펼치기	
	if (status == 'close') {
		btn.dataset.toggleStatus = 'open';
		btn.textContent = '전체 접기';
		for(const item of all_div){
			item.querySelector('button').classList.remove('collapsed');
			item.querySelector('button').ariaExpanded = 'true';
			item.querySelector('div[class*="accordion-collapse"]').classList.add('show');
		}
		
		
		//open일 경우 전체 접기		
	} else if (status == 'open') {
		btn.dataset.toggleStatus = 'close';
		btn.textContent = '전체 펼치기';
		for(const item of all_div){
			item.querySelector('button').classList.add('collapsed');
			item.querySelector('button').ariaExpanded = 'false';
			item.querySelector('div[class*="accordion-collapse"]').classList.remove('show');
		}
		
	}
	
}
//전체, 최근1개월, 최근3개월 버튼 클릭시 실행
function get_buy_list(month){
	const month_form = document.querySelector('#month-form');
	month_form.querySelector('input').value = month;
	month_form.submit();
}
