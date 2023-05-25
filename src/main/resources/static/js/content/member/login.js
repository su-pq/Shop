function login() {
	
	const memId = document.querySelector('#memId').value;
	const memPw = document.querySelector('#memPw').value;
	//ajax start
	$.ajax({
		url: '/login', //요청경로
		type: 'post',
		data: {'memId' : memId, 'memPw' : memPw}, //필요한 데이터
		success: function(result) {
			if(result){
				location.href='/';
			}else{
				alert('아이디나 비밀번호가 일치하지 않습니다.');
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