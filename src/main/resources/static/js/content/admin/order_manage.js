//화면 로딩 후 바로 실행
init();

//날짜순, 상태순 버튼 색상 변경(선택한 버튼에 따라)
function init(){
	const order = document.querySelector('#orderBy');
	const buyDateDesc = document.querySelector('#buyDateDesc');
	const orderStatusDesc = document.querySelector('#orderStatusDesc');
	if(order.value == 'BUY_DATE_ORDER'){
		buyDateDesc.classList.toggle('btn-primary');
		orderStatusDesc.classList.toggle('btn-secondary');
	}else{
		buyDateDesc.classList.toggle('btn-secondary');
		orderStatusDesc.classList.toggle('btn-primary');
	}
}

//검색버튼 클릭 시 실행
function searchOrderList(){
	const searchForm = document.querySelector('#searchForm');
	const checkedCnt = searchForm.querySelectorAll('input[type="checkbox"]:checked').length;
	
	if(checkedCnt == 0){
		alert('검색할 주문상태를 선택하세요.');
		return ;
	}
	
	searchForm.submit();
}
//날짜순, 상태순 버튼 클릭 시 실행
//정렬된 상태로 주문상태목록 조회
function getOrderListDesc(orderBy){
	document.querySelector('#orderBy').value = orderBy;
	
	
	searchOrderList();
}
//페이지 버튼 클릭 시 실행
function getOrderListPage(nowPage){
	document.querySelector('#nowPage').value = nowPage;
	
	searchOrderList();
}


//주문상태 변경 버튼 클릭시 실행(5개 모두 하나의 함수로 실행)
//쌤코드
function changeStatus(btn, statusCode) {
	//체크된 체크박스의 value값
	const checkedChkArr = btn.closest('.row').querySelectorAll('.chk:checked');
	
	const orderNumArr = [];
	for(let i=0; i<checkedChkArr.length; i++){
		orderNumArr[i] = checkedChkArr[i].value;
	}
	
	//map 데이터로 던지기 위해 변환
	//paramData = 넘어가야하는 데이터
	paramData = {
		'statusCode': statusCode,
		'orderNumList': orderNumArr 
	};
	console.log(`statusCode = ${statusCode}`);
	console.log(`orderNumArr = ${orderNumArr}`);
	
	//ajax start
	$.ajax({
		url: '/admin/changeStatusAjax', //요청경로
		type: 'post',
		async: true, //동기/비동기
		contentType: 'application/json; charset=UTF-8',
		//contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: JSON.stringify(paramData), //필요한 데이터
		success: function(result) {
			//수정된 내용으로 테이블 다시 그리기
			updatePageInfo(btn, statusCode, result);
			
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}
//주문 상태 변경 후 테이블 다시 그리기
function updatePageInfo(btn, statusCode, result){
	//클릭한 버튼이 있는 테이블의 내용부 지우기
	const row_1 = btn.closest('.row');
	row_1.querySelector('tbody').replaceChildren();
	
	//위 테이블의 다음 테이블의 내용부 지우기
	const row_2 = row_1.nextElementSibling;
	row_2.querySelector('tbody').replaceChildren();

	console.log(result);
	//테이블 다시 그리기
	drawTable(row_1.querySelector('tbody'), result['firstOrderList']);
	drawTable(row_2.querySelector('tbody'), result['secondOrderList']);
	

}
//테이블 다시 그리기(상태 변경 후)
function drawTable(p_tag, draw_data){
	let str = ``;
	
	if(draw_data.length == 0){
		str += `<tr>
					<td colspan="8">조회된 데이터가 없습니다.</td>
				</tr>`;
	}else{
		draw_data.forEach(function(order, index){
			str += `<tr>
						<td><input type="checkbox"
									value="${order.orderNum}"
									class="form-check-input chk"
									onclick="statusChkControl(${1});"></td>
						<td>${draw_data.length - index}</td>
						<td>${order.buyCode}</td>
						<td>${order.buyVO.memId}</td>
						<td>${order.memberVO.memTell}</td>
						<td>${order.buyVO.buyPrice.toLocaleString('ko-KR', { style: 'currency', currency: 'KRW' })}</td>
						<td>${order.statusInfoVO.statusName}</td>
						<td>${order.updateDate}</td>
					</tr>`;
		});
		
	}
	p_tag.insertAdjacentHTML('afterbegin', str);
}


//내코드
function changeStatus2(btn, statusCode) {
	//statusCode = statusCode.toString();
	
	const selector = '.statusChk' + statusCode + ':checked';
	//console.log(selector);
	const checkedOrderArr = document.querySelectorAll(selector);
	//console.log(checkedOrderArr);

	
	const orderNumArr = [];
	for(const checkedOrder of checkedOrderArr){
		let orderNum = checkedOrder.value;
		orderNumArr.push(orderNum);
	}
	//console.log('buyCodeArr = ' + buyCodeArr);
	
	
	//map 데이터로 던지기 위해 변환
	updateInfo = {
		'statusCode': statusCode,
		'orderNumList': orderNumArr 
	};
	
	
	//ajax start
	$.ajax({
		url: '/admin/changeStatusAjax', //요청경로
		type: 'post',
		async: true, //동기/비동기
		contentType: 'application/json; charset=UTF-8',
		//contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: JSON.stringify(updateInfo), //필요한 데이터
		success: function(result) {
			if(result == 1){
				let addTags = ``;
				//html 추가/삭제
				for(const checkedOrder of checkedOrderArr){
					//추가할 html 문자열 생성
					const tag = checkedOrder.parentElement.parentElement.innerHTML.toString();
					addTags += `<tr>`;
					addTags += tag;
					addTags += `</tr>`;
					
					//추가
					const addTagSelectorStr = '.statusTable' + updateStatusCode + ' tbody';
					const addTag = document.querySelector(addTagSelectorStr);
					addTag.insertAdjacentHTML('afterbegin', addTags)
					
					//No값 변경
					
					
					
					//기존 td 삭제
					checkedOrder.parentElement.parentElement.remove();
				}
				console.log('addTags = ' + addTags);
				
				
			}else{
				alert('실패');
			}
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}

//전체 체크박스 컨트롤 함수(주문상태별 테이블)
function statusChkControlAll(statusCode){
	const selectorAll = '.statusChkAll' + statusCode ;
	const selector = '.statusChk' + statusCode ;
	const statusChkAll = document.querySelector(selectorAll);
	const isChecked = statusChkAll.checked;
	const checkboxes = document.querySelectorAll(selector);
	if(isChecked){
		for(const chk of checkboxes){
			chk.checked = true;
		}
	}else{
		for(const chk of checkboxes){
			chk.checked = false;
		}
	}
}
//개별 체크박스 컨트롤 함수(주문상태별 테이블)
function statusChkControl(statusCode){
	const selectorAll = '.statusChkAll' + statusCode;
	const selector = '.statusChk' + statusCode;
	const checkedSelector = `.statusChk${statusCode}:checked`
	
	const checkboxes = document.querySelectorAll(selector);
	const statusChkAll = document.querySelector(selectorAll);
	
	const totalCnt = checkboxes.length;
	const checkedCnt = document.querySelectorAll(checkedSelector).length;
	if(totalCnt == checkedCnt){
		statusChkAll.checked = true;
	} else{
		statusChkAll.checked = false;
	}
	
}
//검색 영역의 전체 체크 박스 클릭 시 실행
function setSearchCheckbox(allCheck){
	const checkboxes = document.querySelectorAll('#searchForm input[type="checkbox"]');
	
	if(allCheck.checked){
		for(const chk of checkboxes){
			chk.checked = true;
		}
	}else{
		for(const chk of checkboxes){
			chk.checked = false;
		}
	}
}
function openModal(buyCode) {
	
	//ajax start
	$.ajax({
		url: '/admin/getBuyDetailListAjax', //요청경로
		type: 'post',
		async: true, //동기/비동기
		//contentType: 'application/json; charset=UTF-8',
		//contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {'buyCode' : buyCode}, //필요한 데이터
		success: function(result) {
			const modal = new bootstrap.Modal('#detailModal');
			const content_div = document.querySelector('#detailModal .modal-body');
			
			content_div.replaceChildren();
			console.log(result);
			let str = ``;
			str += `<table class="table table-striped text-center align-middle">
						<colgroup>
							<col width="10%">
							<col width="*">
							<col width="20%">
							<col width="10%">
							<col width="25%">
						</colgroup>
						<thead class="table-head">
							<tr>
								<td>No</td>
								<td>상품정보</td>
								<td>가격</td>
								<td>수량</td>
								<td>구매가격</td>
							</tr>
						</thead>
						<tbody>`;
			result.forEach(function(detail, index){
				str +=	`<tr>
								<td>${result.length - index}</td>
								<td><img
									src="/upload/${detail.itemVO.imgList[0].attachedFileName}"
									width="30px;"> ${detail.itemVO.itemName}</td>
								<td>${detail.itemVO.itemPrice.toLocaleString('ko-KR', { style: 'currency', currency: 'KRW' })}</td>
								<td>${detail.buy_cnt}</td>
								<td>${detail.detail_buy_price.toLocaleString('ko-KR', { style: 'currency', currency: 'KRW' })}</td>
							</tr>`;
				
			});
							
			str +=		`</tbody>
					</table>`; 
			
			content_div.insertAdjacentHTML('afterbegin', str);
			modal.show();
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}



//주문상태별 체크박스 컨트롤
//----------------이벤트----------------
//(주문상태)체크박스 전체선택/해제 이벤트
const checkAll = document.querySelector('#checkAll');

//(주문상태)내용부 체크박스 선택여부에 따라 전체 체크박스 체크여부 변경
const checkboxes = document.querySelectorAll('.chk');
for(const chk of checkboxes){
	chk.addEventListener('click', function(){
		
		const totalCnt = checkboxes.length;
		const checkedCnt = document.querySelectorAll('.chk:checked').length;
		console.log(`totalCnt = ${totalCnt}`); //6
		console.log(`checkedCnt = ${checkedCnt}`); //5
		if(checkedCnt == totalCnt){
			checkAll.checked = true;
		}else{
			checkAll.checked = false;
			
		}
	});
}




