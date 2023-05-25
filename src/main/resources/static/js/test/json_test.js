
//contentType : 서버단으로 넘기는 데이터의 타입 지정
//contentType을 적지 않으면 default값으로 넘어간다.
//default값 : 'application/x-www-form-urlencoded; charset=UTF-8' 
//많이 사용하는 타입 : 'application/json; charset=UTF-8'

//1. default 방식
function test1() {
	
	//ajax start
	$.ajax({
		url: '/test/test1', //요청경로
		type: 'post',
		contentType: 'application/x-www-form-urlencoded; charset=UTF-8',
		data: {
				'name' : 'java', 
				'age' : 20, 
				'addr' : '울산시'
		}, //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end

}

//2. json 방식
function test2() {
	data = {
		'name': 'java',
		'age': 20,
		'addr': '울산시'
	};
	
	//ajax start
	$.ajax({
		url: '/test/test2', //요청경로
		type: 'post',
		contentType: 'application/json; charset=UTF-8',
		//json 형식으로 데이터를 넘기는 법
		//json타입으로 데이터를 넘기기 위해서는 json.stringify로 문자화시켜서 넘겨줘야 한다.
		data: JSON.stringify(data), //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
	
}

//
//3. json방식 map으로 받기
function test3() {
	data = {
		'name': 'java',
		'age': 20,
		'addr': '울산시'
	};
	
	//ajax start
	$.ajax({
		url: '/test/test3', //요청경로
		type: 'post',
		contentType: 'application/json; charset=UTF-8',
		//json 형식으로 데이터를 넘기는 법
		//json타입으로 데이터를 넘기기 위해서는 json.stringify로 문자화시켜서 넘겨줘야 한다.
		data: JSON.stringify(data), //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
	
}
//4. 자바스크립트의 객체 배열을 넘기고 받기(데이터를 담을 VO가 존재하는 경우 사용)
function test4() {
	dataArr = [];
	for (let i = 0; i < 3; i++) {
		data = {
			'name': 'java' + i,
			'age': 20 + i,
			'addr': '울산시' + i
		};
		dataArr[i] = data;
	};
	
	
	
	//ajax start
	$.ajax({
		url: '/test/test4', //요청경로
		type: 'post',
		contentType: 'application/json; charset=UTF-8',
		//json 형식으로 데이터를 넘기는 법
		//js의 객체는 java가 인식하지 못하므로 json.stringify로 json타입의 문자열로 변환시켜서 넘겨줘야 한다.
		data: JSON.stringify(dataArr), //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
	
}
//5. 기존 방식으로 하면 오류 발생
function test5() {
	dataArr = [];
	for (let i = 0; i < 3; i++) {
		data = {
			'name': 'java' + i,
			'age': 20 + i,
			'addr': '울산시' + i
		};
		dataArr[i] = data;
	};
	
	//ajax start
	$.ajax({
		url: '/test/test5', //요청경로
		type: 'post',
		//기존 방식으로 전달
		//contentType: 'application/json; charset=UTF-8', default 값이 들어가도록 주석처리
		data: {'dataArr' : dataArr}, //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
	
}
//6.(4번 형식에서)Map 데이터로 받기  (데이터를 담을 VO가 없는 경우 사용)
function test6() {
	dataArr = [];
	for (let i = 0; i < 3; i++) {
		data = {
			'name': 'java' + i,
			'age': 20 + i,
			'addr': '울산시' + i
		};
		dataArr[i] = data;
	};
	
	
	
	//ajax start
	$.ajax({
		url: '/test/test6', //요청경로
		type: 'post',
		contentType: 'application/json; charset=UTF-8',
		data: JSON.stringify(dataArr), //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
	
}
//7.  VO가 없는 경우 사용
function test7() {
	studentArr = [];
	for (let i = 0; i < 3; i++) {
		student = {
			'name': 'java' + i,
			'age': 20 + i,
			'score': 50 + i
		};
		studentArr[i] = student;
	};
	
	classInfo = {
		'teacher_name' : '홍길동'
		, 'class_name' : '자바반'
		, 'stu_info' : studentArr
	};
	
	//ajax start
	$.ajax({
		url: '/test/test7', //요청경로
		type: 'post',
		contentType: 'application/json; charset=UTF-8',
		//json 형식으로 데이터를 넘기는 법
		//js의 객체는 java가 인식하지 못하므로 json.stringify로 json타입의 문자열로 변환시켜서 넘겨줘야 한다.
		data: JSON.stringify(classInfo), //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
	
}
//8.  VO가 없는 경우 사용
function test8() {
	studentArr = [];
	for (let i = 0; i < 3; i++) {
		student = {
			'name': 'java' + i,
			'age': 20 + i,
			'score': 50 + i
		};
		studentArr[i] = student;
	};
	
	classInfo = {
		'tName' : '홍길동'
		, 'cName' : '자바반'
		, 'stuInfo' : studentArr
	};
	
	//ajax start
	$.ajax({
		url: '/test/test8', //요청경로
		type: 'post',
		contentType: 'application/json; charset=UTF-8',
		//json 형식으로 데이터를 넘기는 법
		//js의 객체는 java가 인식하지 못하므로 json.stringify로 json타입의 문자열로 변환시켜서 넘겨줘야 한다.
		data: JSON.stringify(classInfo), //필요한 데이터
		success: function(result) {
			alert('ajax 통신 성공');
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
	
}