
function regCate() {
	//카테고리명이 빈값인지 확인
	const inputCateName = document.querySelector('.inputCateName');
	if(inputCateName.value == ''){
		alert('카테고리명은 필수입니다.');
		//if문 마지막에 return;만 적으면 return을 만나는 순간 해당 함수를 종료한다.(카테고리 등록이 실행되지 않는다.)
		return ;
	}
	
	//카테고리명 중복 확인
	checkCateName(inputCateName.value);
	if(checkCateName(inputCateName.value)){
		alert('카테고리명이 중복입니다.\n다시 입력하세요.');
		inputCateName.value = ''; 
		return ;
	}
	//ajax start
	$.ajax({
		url: '/admin/regCateAjax', //요청경로
		type: 'post',
		data: { 'cateName': inputCateName.value }, //필요한 데이터
		success: function(result) {
			if (result == 1) {
				inputCateName.value = '';
				alert('카테고리가 등록되었습니다.');
				getCateListAjax();
			} else {
				alert('카테고리 등록 실패!!!');
			}
		},
		error: function() {
			alert('실패:regCate');
		}
	});
	//ajax end
}
//카테고리 등록 시 이름 중복 확인
function checkCateName(cateName) {
	let isDuplicate = false;
	
	//ajax start
	$.ajax({
		url: '/admin/checkCateNameAjax', //요청경로
		type: 'post',
		// 함수 내 코드가 끝나고 함수 밖의 코드를 실행하도록 설정(그렇지 않으면 이전 코드와 ajax 동시에 실행)
		async:false, //동기 방식으로 실행, 작성하지 않으면 기본으로 true값을 가짐
		data: {'cateName' : cateName}, //필요한 데이터
		success: function(result) {
			//주의 : return문을 success문에 사용하면 success문의 결과로 return하는 것이다.(checkCateName의 return값을 지정하는 것이 아님.)
			//		 checkCateName의 함수의 return은 ajax 실행이 끝난 후 실행하면 된다.
			if(result == 1){
				isDuplicate = true;
			}
		},
		error: function() {
			alert('실패:checkCateName');
		}
	});
	//ajax end
	
	return isDuplicate
}

function getCateListAjax() {
	$.ajax({
		url: '/admin/getCateListAjax', //요청경로
		type: 'post',
		async: false,
		data: {}, //필요한 데이터
		success: function(result) {
			
			//카테고리 목록 테이블의 tbody태그 선택
			const tbodyTag = document.querySelector('#cateListTable tbody');
			//해당 태그 안의 모든 태그 빈 값으로 교체
			tbodyTag.replaceChildren();
			
			
			let str = '';
			for(let i=0; i<result.length; i++){
				str += '<tr>';
				str += `<td>${i+1}</td>`;
				str += `<td>${result[i].cateName}</td>`;
				str += `<td>
				<div class="row">
					<div class="form-check col-6"> 
						<input type="radio" class="form-check-input" name="isUse_${i+1}" value="Y" onchange="changeIsUse('${result[i].cateCode}');"` 
				
				if(result[i].isUse=='Y'){
					str += ' checked '
				}
				
				str += `>사용중										
					</div>
					<div class="form-check col-6"> 
						<input type="radio" class="form-check-input" name="isUse_${i+1}" value="N" onchange="changeIsUse('${result[i].cateCode}');"`
				if(result[i].isUse=='N'){
					str += ' checked '
				}		
				str +=	`>미사용 
					</div>
				</div>
				</td>`;
				
				str += `<td>${result[i].orderNum}</td>`;
				str += `<td><input type="button" value="삭제" onclick="deleteCate(${result[i].cateCode});"></td>`
				str += '</tr>';
			}
			
			
			tbodyTag.insertAdjacentHTML('beforeend', str);


		},
		error: function() {
			alert('실패:getRegCate');
		}
	});
}

function changeIsUse(cateCode) {
	
	//ajax start
	$.ajax({
		url: '/admin/updateIsUseAjax', //요청경로
		type: 'post',
		data: {'cateCode': cateCode}, //필요한 데이터
		success: function(result) {
			if(result == 1){
				alert('변경 성공');
				//clickTag.checked = true;
				
			}else{
				alert('!!변경 실패!!');
			}
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}

function deleteCate(cateCode){
	if(confirm('정말 삭제하시겠습니까?')){		
		location.href=`/admin/deleteCate?cateCode=${cateCode}`;
	}
}