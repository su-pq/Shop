

function searchAddr(){
	new daum.Postcode({
    	oncomplete: function(data) {
			//도로명주소를 가져오는 변수 roadAddr
			var roadAddr = data.roadAddress;
			//html 태그에 값 변경
        	document.querySelector('#memAddr').value = roadAddr;
        	document.query
	    }
	}).open();
}

function checkPw(btn) {
	const inputPwTag = document.querySelector('#inputPw');
	
	//ajax start
	$.ajax({
		url: '/member/getIsMachedPwAjax', //요청경로
		type: 'post',
		async: true, //동기/비동기
		//contentType: 'application/json; charset=UTF-8',
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {'memPw' : inputPwTag.value}, //필요한 데이터
		success: function(result) {
			
			if(result){
				//비밀번호 확인란 disabled 추가
				inputPwTag.disabled = true;
				btn.disabled = true;
				//비밀번호가 비밀번호 수정란 disabled 제거
				const memPwTagList = document.querySelectorAll('.memPw')
				memPwTagList.forEach(function(memPw){
					memPw.disabled = false;
				});
			}else{
				alert('비밀번호가 일치하지 않습니다.')
			}
			
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}
//수정버튼 비활성화(미사용)
function setDisabledUpdate(){
	document.querySelector('#updateBtn').disabled = true;
}

// 새 비밀번호 일치하는지 확인
function isSamePw(){
	document.querySelector('#updateBtn').disabled = true;
	
	const confirmMemPw = document.querySelector('#confirmMemPw').value;
	const memPw = document.querySelector('#memPwOrigin');
	
	if(memPw.nextElementSibling != null){
		memPw.nextElementSibling.remove();
	}
	let str = '';
	if(memPw.value != '' && confirmMemPw != ''){
		if(memPw.value == confirmMemPw){
			str += '<span style="color: lime; font-size:0.9rem;">비밀번호 일치</span>';
			document.querySelector('#updateBtn').disabled = false;
		}else{
			str += '<span style="color: red; font-size:0.9rem;">비밀번호 불일치</span>';
			
		}
	}
	memPw.insertAdjacentHTML('afterend', str);
	
}




