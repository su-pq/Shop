//총 가격 계산
function totalPrice(itemPrice, stockTag){
	itemStock = stockTag.value;
	totalPriceTag = document.querySelector('#totalPrice');
	totalPriceTag.replaceChildren();
	const totalPrice = itemPrice * itemStock;
	const options = { style: 'currency', currency: 'KRW' };
	const totalPriceView = totalPrice.toLocaleString('ko-KR', options);
	const str = `<label for="" class="col-3 col-form-label">총 가격</label>
		   		<label for="" class="col-9 col-form-label finalPrice">${totalPriceView}</label>`
	totalPriceTag.insertAdjacentHTML('afterbegin', str);
}
//'장바구니' 버튼 클릭 시 실행
function regCart(itemCode, memId) {
	
	console.log('상품코드 : ' + itemCode);
	console.log('로그인 아이디 : ' + memId);

	if (memId == 'anonymousUser') {
		const result = confirm('로그인 후 이용 가능합니다. \n로그인하시겠습니까?');
		
		if(result){
			//로그인 모달 띄우기
			const loginModal = new bootstrap.Modal('#loginModal');
			loginModal.show();
		}
		
		return ;
		
	} 
	regCartAjax(itemCode);

}
//장바구니 등록 ajax
function regCartAjax(itemCode){
	const cartCnt = document.querySelector('#cntInput').value;
		//ajax start
		$.ajax({
			url: '/cart/regCartAjax', //요청경로
			type: 'post',
			data: { 'itemCode': itemCode, 'cartCnt': cartCnt }, //필요한 데이터
			success: function(result) {
				if (result == 1) {
					/*const result1 = confirm('상품을 장바구니에 추가했습니다.\n 장바구니로 이동할까요?');
					if(result1){
						href=`/cart/cartList`;
					}
					*/
					//자바스크립트로 모달 띄우기
					const cartModal = new bootstrap.Modal('#cartModal');
					cartModal.show(); //부트스트랩에서 제공하는 기능
					//자바스크립트로 모달 닫기
					//imgModal.hide();
				} else {
					alert('상품을 장바구니에 담지 못했습니다.');
				}
			},
			error: function() {
				alert('실패 : 장바구니 Ajax');
			}
		});
		//ajax end
	
}
//'바로구매' 버튼 클릭 시 실행
function buy(itemCode){
	const loginInfo = sessionStorage.getItem('loginInfo');
	console.log(loginInfo);
		if (confirm('상품을 구매하시겠습니까?')) {
			//총 구매금액
			let final_price = document.querySelector('.finalPrice').textContent;
			//숫자만 추출하는 정규식
			const regex = /[^0-9]/g;
			final_price = final_price.replace(regex, '');//0-9가 아닌 문자들은 빈 문자열로 교체
			console.log('final_price = ' + final_price);
			//배열 세팅
			const detail_info_arr = [];
			buy_detail_info = {
				'item_code' : itemCode,
				'buy_cnt' : document.querySelector('#cntInput').value,
				'detail_buy_price' : final_price
			};
			detail_info_arr[0] = buy_detail_info;
			
			console.log(detail_info_arr);
			
			
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