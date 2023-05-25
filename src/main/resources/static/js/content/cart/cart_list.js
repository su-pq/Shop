//수정버튼 클릭시
function updateTotalPrice(clickTag, cartCode) {
	setFinalPrice();
	cartCnt = clickTag.parentElement.previousElementSibling.children[0].value
	console.log(`CartCnt = ${cartCnt} / cartCode = ${cartCode}`);
	location.href=`/cart/update?cartCnt=${cartCnt}&cartCode=${cartCode}`;
}
//삭제버튼
function goDelete(cartCode){
	const result = confirm('해당 상품을 장바구니에서 비울까요?');
	if(result){
		location.href=`/cart/delete?cartCode=${cartCode}`;		
	}
}

//선택삭제
function goDeleteCarts(){
	const checkedChks = document.querySelectorAll('.chk:checked');
	if(checkedChks.length == 0){
		alert('선택한 상품이 없습니다.');
		retrun ;
	}
	
	//cartCode를 여러개 담을 수 있는 배열 생성
	const cartCodeArr = [];
	checkedChks.forEach(function(chk, index){
		//cartCode 넣기
		cartCodeArr[index] = chk.value;
	})
	location.href=`/cart/deleteCarts?cartCodes=${cartCodeArr}`
	/*const result = confirm('선택한 상품을 장바구니에서 비울까요?');
	if(result){
		const cartCodes = getCartCodes();
		location.href=`/cart/deleteCarts?cartCodes=${cartCodes}`;
		
	}*/
}
//선택구매(쌤)
function buys(){
	//체크된 체크박스의 개수
	const checked_checkboxes = document.querySelectorAll('.chk:checked');
	if(checked_checkboxes.length == 0){
		alert('구매할 상품을 선택하세요');
		return ;
	}
	
	//체크된 체크박스의 dataset 확인
	for(const e of checked_checkboxes){
		console.log(e.dataset);
	}
	
	const result = confirm('상품을 구매하시겠습니까?');
	if (result) {
		//넘길 데이터
		const detail_info_arr = [];
		for(let i = 0; i < checked_checkboxes.length; i++){			
			buy_detail_info = {
				'item_code' : checked_checkboxes[i].dataset.itemCode,
				'buy_cnt' : checked_checkboxes[i].dataset.buyCnt,
				'detail_buy_price' : checked_checkboxes[i].dataset.datailBuyPrice
			};
			detail_info_arr[i] = buy_detail_info;
		}
		
		//총 구매금액
		let final_price = document.querySelector('#finalPriceSpan').textContent;
		//숫자만 추출하는 정규식
		const regex = /[^0-9]/g;
		final_price = final_price.replace(regex, '');//0-9가 아닌 문자들은 빈 문자열로 교체
		
		data = {
			'final_price' : final_price,
			'detail_info_arr' : detail_info_arr
		}
		
		//ajax start
		$.ajax({
			url: '/buy/buysAjax', //요청경로
			type: 'post',
			async: false, //동기/비동기
			contentType: 'application/json; charset=UTF-8',
			//contentType: "application/x-www-form-urlencoded; charset=UTF-8",
			data: JSON.stringify(data), //필요한 데이터
			success: function(result) {
				alert('구매완료');
				goDeleteCarts();
				//구매내역으로 이동
				//location.href='/buy/buyList';
			},
			error: function() {
				alert('실패');
			}
		});
		//ajax end
	}
	
}

//선택구매
function goBuyCarts(){
	const result = confirm('상품을 구매하시겠습니까?');
	if(result){
		const cartCodes = getCartCodes();
		location.href=`/buy/goBuyForm?cartCodes=${cartCodes}`;
	}
}

//체크된 상품의 cartCode 가져오기
function getCartCodes(){
	const checkedChks = document.querySelectorAll('.chk:checked');
	let cartCode = '';
	for(const chk of checkedChks){
		console.log(chk.value);
		cartCode += chk.value + ',';
	}
	cartCode = cartCode.substr(0, cartCode.length-1);
	console.log('cartCode : ' + cartCode);
	return cartCode;
}


// 시작하자마자 총 가격 세팅
setFinalPrice();

function setFinalPrice(){
	
	const checkedChks = document.querySelectorAll('.chk:checked');
	//최종 금액 저장 변수
	let finalPrice = 0;
	for(const chk of checkedChks){
		//선택한 태그를 감싼 가장 가까운 부모 태그 찾아감.
		const spanTag = chk.closest('tr').querySelector('.totalPriceSpan');
		console.log(spanTag);
		console.log(spanTag.dataset.cartCode);
		
		const totalPrice = parseInt(spanTag.dataset.totalPrice);
		finalPrice += totalPrice;
	}
	
	
	const finalPriceDiv = document.querySelector('#finalPriceDiv');
	//전체합계 통화단위로 변경
	const options = { style: 'currency', currency: 'KRW' };
	const finalPriceView = finalPrice.toLocaleString('ko-KR', options);
	//전체합계 변경
	str = `<span style="text-align: right;" id="finalPriceSpan">${finalPriceView}</span>`;
	finalPriceDiv.replaceChildren();
	finalPriceDiv.insertAdjacentHTML('afterbegin', str);
	
	/*
	//--------------총 totalPrice 세팅---------------
	//체크박스 태그 선택
	const checkboxes = document.querySelectorAll('.chk');
	//개별 totalPrice 태그 선택
	const totalPriceSpans = document.querySelectorAll('.totalPriceSpan');
	//totalPrice 태그 선택
	const totalPriceDiv = document.querySelector('#finalPriceDiv');
	let totalPrice = 0;
	for(let i=0; i<eachTotalPriceTds.length; i++){
		if(checkboxes[i].checked){
			//개별합계 계산/변경
			let eachTotalPrice = parseInt(eachTotalPriceTds[i].textContent.replace(/[\₩,]/g, ''));		
			//전체합계 계산
			totalPrice += eachTotalPrice;			
		}
	}
	//전체합계 통화단위로 변경
	const options = { style: 'currency', currency: 'KRW' };
	const totalPriceView = totalPrice.toLocaleString('ko-KR', options);
	//전체합계 변경
	str = `<span style="text-align: right;">${totalPriceView}</span>`;
	totalPriceDiv.replaceChildren();
	totalPriceDiv.insertAdjacentHTML('afterbegin', str);*/

}

//----------------이벤트----------------
//체크박스 전체선택/해제 이벤트
const checkAll = document.querySelector('#checkAll');
checkAll.addEventListener('click', function(){
	
	const isChecked = checkAll.checked;
	const checkboxes = document.querySelectorAll('.chk');
	if(isChecked){
		for(const chk of checkboxes){
			chk.checked = true;
		}
	}else{
		for(const chk of checkboxes){
			chk.checked = false;
		}
	}
	setFinalPrice();
});
//내용부 체크박스 선택여부에 따라 전체 체크박스 체크여부 변경
const checkboxes = document.querySelectorAll('.chk');
for(const chk of checkboxes){
	chk.addEventListener('click', function(){
		
		const totalCnt = checkboxes.length;
		const checkedCnt = document.querySelectorAll('.chk:checked').length;
		if(checkedCnt == totalCnt){
			checkAll.checked = true;
		}else{
			checkAll.checked = false;
			
		}
		setFinalPrice();
	});
}



















