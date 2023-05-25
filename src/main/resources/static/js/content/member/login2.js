alert('JS연결 성공');


function login() {
	alert('LOGIN함수 실행');
	const memId = document.querySelector('#memId').value;
	const memPw = document.querySelector('#memPw').value;
	
	console.log(`memId : ${memId} // memPw : ${memPw}`);
	//ajax start
	$.ajax({
		url: '/member/login', //요청경로
		type: 'post',
		data: {'memId' : memId, 'memPw' : memPw}, //필요한 데이터
		success: function(result) {
			alert(result);
			if(result == 'success'){
				location.href='/';
			}else{
				
				let str = '';
				str += '로그인 정보를 확인하세요.';				
				const error_div = document.querySelector('#errorDiv');
				error_div.textContent = '';
				error_div.insertAdjacentHTML('beforeend', str);
				//error_div.textContent('beforeend', str);
				
				
				//아이디, 비밀번호 데이터 초기화
				//바로 반복문 실행. foreach의 functiion에는 매개변수로 value와 index가 들어온다.
				const tags = document.querySelectorAll('#loginModal input:not([type="button"])').forEach(function(t, index){
					t.value = '';
				});
			}
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}