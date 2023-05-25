//초기작업 실행
init();
//--함수 선언--//
function isDuplicateMemId(){
	//회원 아이디를 입력하는 태그 가져오기
	const memIdTag = document.querySelector('#joinModal #memId'); //id가 joinModal인 태그 안에 있는 id가 memId인 태그선택(login과 아이디값이 겹칠 수 있으므로 왼쪽과 같이 작성)
	const memId = memIdTag.value;
	
	if(memId == ''){
		alert('ID를 입력하세요.');
		return;
	}
	
	//ajax start
	$.ajax({
	   url: '/member/isDuplicateMemId', //요청경로
	   type: 'post',
	   async:false,
	   data: {'memId' : memId}, //필요한 데이터
	   success: function(result) {
		
	      if(result){
				alert('이미 사용중인 아이디입니다.');
				
			}else{
				alert('사용 가능한 아이디입니다.');
				document.querySelector('#joinBtn').disabled = false;
			}
	   },
	   error: function() {
	      alert('실패');
	   }
	});
	//ajax end
}

function setDisabled(){
	//회원가입버튼 비활성화 (disabled 속성 활성화)
	document.querySelector('#joinBtn').disabled = true;
}

//회원가입 함수
function join(){
	//유효성 검사 진행 (isValid : 유효한거니?)
	const isValid = joinValidate();
	if(!isValid){
		return ;
	}
	//회원가입 진행(쿼리 실행하러 가기)
	document.querySelector('#joinForm').submit();
}

//회원가입 진행 시 데이터 유효성 검사
function joinValidate(){
	//오류메시지 div 전체 제거(초기화)
	deleteErrorDiv();
	
	//함수의 리턴 결과를 저장하는 변수
	let result_memId = true;
	let result_memPw = true;
	
	//오류메시지를 저장할 변수 str
	let str_memId = ''; 
	let str_memPw = ''; 
	
	
	//회원가입 form 태그의 자식 div '전체' 선택
	const divs = document.querySelectorAll('#joinForm > div');
	//validation 처리
	// id-------------------------------
	// 조건문이 하나라도 만족하면 join 함
	const memId = document.querySelector('#joinModal #memId').value;
	if(memId == ''){
		str_memId = '아이디는 필수 입력입니다.';
		result_memId = false;
	}else if(memId.length < 4){
		str_memId = '아이디는 4글자 이상이어야 합니다.';
		result_memId = false;
	}
	
	
	//pw---------------------------------
	const memPw = document.querySelector('#joinModal #memPw').value;
	
	//문자 + 숫자의 6~12자리의 글자인지 체크하는 정규식
	// 정규식에 부합하지 않으면 null을 리턴
	const regExp = /^(?=.*[a-zA-Z])(?=.*[0-9]).{6,12}$/;
	memPw.match(regExp); //정규식에 맞지 않을 경우 : null / 정규식에 맞을 경우 : 실제 입력한 값
	
	if(memPw == ''){
		str_memPw = '비밀번호는 필수 입력입니다.';
		result_memPw = false;
	}else if(memPw.match(regExp)==null){
		str_memPw = '문자와 숫자가 조합된 6~12글자를 입력하세요.';
		result_memPw = false;
	}
	
	//id 유효성 검사 실패 시 오류 메세지 출력
	if(!result_memId){
		const errorHTML = `<div class="my-invalid">${str_memId}</div>`;
		divs[2].insertAdjacentHTML('afterend', errorHTML);		
	}
	
	//pw 유효성 검사 실패 시 오류 메세지 출력
	if(!result_memPw){
		const errorHTML = `<div class="my-invalid">${str_memPw}</div>`;
		divs[3].insertAdjacentHTML('afterend', errorHTML);		
	}
	
	
	return result_memId && result_memPw;
	
}

//오류메시지 div 전체 제거(초기화)
function deleteErrorDiv(){
	const errorDivs = document.querySelectorAll('div[class="my-invalid"]');
	for(const errorDiv of errorDivs){
		errorDiv.remove();
	}
}

function checkPw(){
	const memPwTag = document.querySelector('#joinModal #memPw');
	const checkPw = document.querySelector('#joinModal #checkPw').value;
	
	if(memPwTag.value != checkPw){
		str = `<div class="my-invalid">비밀번호가 일치하지 않습니다.</div>`;
		memPwTag.insertAdjacentHTML('afterend', str);
		setDisabled();
		//return false;
	}else{
		document.querySelector('#joinBtn').disabled = false;
		//return true;
	}
}


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

function login() {
	
	const memId = document.querySelector('#loginModal #memId').value;
	const memPw = document.querySelector('#loginModal #memPw').value;
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
//관리자 메뉴바 선택시
function clickMenu(menuCode){
	if(menuCode == 'MENU_001'){
		location.href='/admin/cateManage';
	}else if(menuCode == 'MENU_002'){
		location.href='/admin/memberManage';
	}else if(menuCode == 'MENU_003'){
		location.href='/admin/orderManage';
	}
}

//로그인 > 비밀번호찾기 클릭시
function findPw(btn){
	btn.disabled = true;
	btn.textContent = 'Loading...';
	const spinner = '<span class="spinner-border spinner-border-sm" role="status" aria-hidden="true"></span>'
	btn.insertAdjacentHTML('afterbegin', spinner);
	//ajax start
	$.ajax({
		url: '/member/findPwAjax', //요청경로
		type: 'post',
		async: true, //동기/비동기
		contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		//form태그.serialize() : form 태그 안의 데이터를 모두 가져감
		//serialize()는 ajax 문법이므로 태그 선택도 ajax 문법으로 해야 한다.
		data: $('#findPwForm').serialize(), //필요한 데이터
		success: function(result) {
			if(result){
				alert('이메일로 임시 비밀번호를 발급했습니다.\n반드시 비밀번호 변경 후 사용하세요.');
			}else{
				let str = ``;
				str += `<div class="col-12 error-findPw" style="font-size: 0.9rem; color: red;">`;
				str += `<span>`;
				str += `아이디와 이름을 확인하세요.`;
				str += `</span>`;
				str += `</div>`;
				document.querySelector('#findPwErrorDiv').insertAdjacentHTML('afterend', str);
			}
			btn.disabled = false;
			btn.replaceChildren('임시 비밀번호 발급');
			
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
	
}


function init(){
	
	//로그인 모달 태그 선택
	const loginModal = document.getElementById('loginModal');
	//회원가입 모달 태그 선택
	const joinModal = document.getElementById('joinModal');
	//비밀번호 찾기 모달 태그 선택
	const findPwModal = document.getElementById('findPwModal');
	
	//로그인 모달창이 닫힐 때 마다 실행되는 이벤트
	loginModal.addEventListener('hidden.bs.modal', function(e){ //e : 이벤트의 정보를 들고 온다.
	  //Form 태그 안의 모든 input 태그값 초기화
	  document.querySelector('#loginForm').reset();
	  deleteErrorDiv();
	  
	  //로그인 실패 시 추가되는 div 태그 삭제
	  const error_div = document.querySelector('#errorDiv');
	  error_div.textContent = '';
	  console.log(error_div);
	})
	//회원가입 모달 창이 닫힐 때마다 실행되는 이벤트
	joinModal.addEventListener('hidden.bs.modal', function(e){ //e : 이벤트의 정보를 들고 온다.
	  //Form 태그 안의 모든 input 태그값 초기화
	  document.querySelector('#joinForm').reset();
	  deleteErrorDiv();
	  
	})
	//비밀번호 찾기 모달 창이 닫힐 때마다 실행되는 이벤트
	//모든 이벤트는 이벤트의 정보를 가져오는 매개변수를 사용할 수 있다.
	findPwModal.addEventListener('hidden.bs.modal', function(event){
		document.querySelector('#findPwForm').reset();
		const error_tag = document.querySelector('.error-findPw');
		if(error_tag != null){
			error_tag.remove();
		}
		
		//이벤트가 발생하고 있는 태그(this와 같은 역할)
		const find_pw_modal_div = event.target; //비밀번호 찾기 모달태그 전체
		const find_pw_btn = find_pw_modal_div.querySelector('button');
		find_pw_btn.disabled = false;
		find_pw_btn.replaceChildren('임시 비밀번호 발급');
	})
	
};






