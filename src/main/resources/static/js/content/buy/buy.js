//시작하자마자 실행
setFinalPrice();
//최종결제금액 세팅
function setFinalPrice(){
	//상품별 totalPrice 태그 전체 선택
	const totalPriceSpans = document.querySelectorAll('.totalPriceSpan');
	//최종 결제금액 태그 선택
	const finalPriceSpan = document.querySelector('#finalPriceSpan');
	
	//finalPrice 세팅
	let finalPrice = 0;
	for(const totalPriceSpan of totalPriceSpans){
		finalPrice += parseInt(totalPriceSpan.dataset.totalPrice);		
	}
	//화폐단위로 변환
	const options = { style: 'currency', currency: 'KRW' };
	const finalPriceView = finalPrice.toLocaleString('ko-KR', options);
	
	//html 삽입
	finalPriceSpan.replaceChildren();
	finalPriceSpan.insertAdjacentHTML('afterbegin', finalPriceView);	
}

//주소 찾기버튼 클릭시
function searchAddr(){
	new daum.Postcode({
    	oncomplete: function(data) {
			//도로명주소를 가져오는 변수 roadAddr
			var roadAddr = data.roadAddress;
			//html 태그에 값 변경
        	document.querySelector('#memAddr').value = roadAddr;
	    }
	}).open();
}
//주문하기 버튼 클릭시
function goBuy() {
	//전달 데이터 세팅
	
	//ajax start
	$.ajax({
		url: '/buy/regBuyAjax', //요청경로
		type: 'post',
		data: {}, //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
			//주문완료메시지 띄운 후 주문내역 페이지 이동
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}