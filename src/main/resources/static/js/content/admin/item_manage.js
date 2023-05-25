//이미지 팝업 모달 선택(bootstrap. ()안에 id값만 넣어주면 된다.)
const imgModal = new bootstrap.Modal('#imgModal');


//이미지명 클릭 시 이미지 모달을 띄우는 함수
function openImgModal (attachedFileName, originFileName){
	//모달로 띄울 이미지 정보 세팅
	const modalTag = document.querySelector('#imgModal');
	modalTag.querySelector('img').src = `/upload/${attachedFileName}`;
	modalTag.querySelector('h1').innerHTML = `${originFileName}`;
	
	
	
	//자바스크립트로 모달 띄우기
	imgModal.show(); //부트스트랩에서 제공하는 기능
	//자바스크립트로 모달 닫기
	//imgModal.hide();
	
}


//상품명 클릭 시 실행되는 함수
function getItemInfo(itemCode){
	console.log(itemCode);
	//ajax start
	$.ajax({
		url: '/admin/getItemInfoAjax', //요청경로
		type: 'post',
		data: {'itemCode' : itemCode}, //필요한 데이터
		success: function(result) {
			console.log(result);
			console.log(result['item']);
			console.log(result['item'].itemName);
			itemInfoTable = document.querySelector('.itemInfoTable');
			itemInfoTable.replaceChildren(); 
			let str = ``;
			str += `
					<input type="hidden" value="${result['item'].itemCode}" name ="itemCode">
					<tr>
						<td class="h4">상품 기본 정보</td>
						<td style="text-align: right;">
							<button type="submit" class="btn btn-primary mb-3">
								수정
							</button>
						</td>
					</tr>
					<tr>
						<td>카테고리</td>
						<td>
							<select class="form-select" id="cateName" name="cateCode">`;
							
			for(const e of result['cateList']){
				const selected = result['item'].cateCode == e.cateCode;
							str += `<option value="${e.cateCode}"`;
							if(selected){
								str += ` selected>${e.cateName}</option>`;	
							}else{
								str += `>${e.cateName}</option>`;
							}			
								
			}
							
			str +=				`			
							</select>
						</td>
					</tr>
					<tr>
						<td>상품명</td>
						<td>
							<input type="text" class="form-control" name="itemName" value="${result['item'].itemName}">
						</td>
					</tr>
					<tr>
						<td>상품가격</td>
						<td>
							<input type="number" class="form-control" name="itemPrice" value="${result['item'].itemPrice}">
						</td>
					</tr>
					<tr>
						<td>상품소개</td>
						<td>
							<textarea rows="3" cols="30" class="form-control" name="itemIntro">${result['item'].itemIntro}</textarea>
						</td>
					</tr>
					<tr>
						<td colspan="2" class="h4">상품 판매 정보</td>
					</tr>
					<tr>
						<td>상품상태</td>
						<td>
							<select class="form-select" id="itemStatus" name="itemStatus">`;
							if(result['item'].itemStatus == 1){
								str += `<option value="1" selected>판매중</option>				
									<option value="2" >준비중</option>				
									<option value="3">매진</option>`;				
								
							}else if(result['item'].itemStatus == 2){
								str += `<option value="1">판매중</option>				
									<option value="2" selected>준비중</option>				
									<option value="3">매진</option>`;	
							}else{
								str += `<option value="1">판매중</option>				
									<option value="2">준비중</option>				
									<option value="3" selected>매진</option>`;	
							}
							
					str +=	`				
							</select>
						</td>
					</tr>
					<tr>
						<td>재 고</td>
						<td>
							<input type="number" class="form-control" name="itemStock" value="${result['item'].itemStock}">
						</td>
					</tr>
					<tr>
						<td colspan="2" class="h4">상품 이미지 정보</td>
					</tr>
					<tr>
						<td>메인이미지</td>
						<td>`;
						
			for(const img of result['item'].imgList){
				if(img.isMain == 'Y'){														//href로 페이지 이동을 막는 방법
					str += `<input class="form-control" type="file" id="mainImg" name="mainImg"><a href="javascript:void(0);" onclick="openImgModal('${img.attachedFileName}', '${img.originFileName}');" >${img.originFileName}</a>`;						
				}
			}	
						
			str +=			`</td>
					</tr>
					<tr>
						<td>서브이미지</td>
						<td>`;
			str += `<input class="form-control" type="file" id="subImg" name="subImg"  multiple>`;
			for(const img of result['item'].imgList){
				if(img.isMain == 'N'){
					str += `<a href="javascript:void(0);" onclick="openImgModal('${img.attachedFileName}', '${img.originFileName}');" >${img.originFileName}</a><br>`;						
				}
			}						
			str += `</td>
					</tr>`;
			itemInfoTable.insertAdjacentHTML('afterbegin', str);
			
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}

/*function checkAll(){
	const checkAll = document.querySelector('#checkAll');
	const chks = document.querySelectorAll('.chk');
	let allChecked = true;
	let allUnChecked = true;
	for(let i = 0; i < chks.length; i++){
		if(chks[i].checked){
			allUnChecked = false;
		}else{
			allChecked = false;
		}
	}
	if(allChecked){
		checkAll.checked = true;
	} else if(allUnChecked){
		checkAll.checked = false;
	}
}*/

//-------------------------이벤트--------------------------//
//전체 체크 박스
const checkAll = document.querySelector('#checkAll');
//전체를 제외한 체크박스들
const chks = document.querySelectorAll('.chk');

//검색 영역의 카테고리의 체크박스 중 '전체' 체크박스 클릭 시
checkAll.addEventListener('click', function(){
	if(checkAll.checked){
		for(const chk of chks){
			chk.checked = true;
		}
	}else{
		for(const chk of chks){
			chk.checked = false;
		}
	}
});
//검색 영역의 카테고리의 체크박스 중 '전체' 체크박스를 제외한 나머지 클릭 시
for(let i=0; i<chks.length; i++){
	chks[i].addEventListener('click', function(){
		//전체를 제외한 체크박스 중 체크된 체크박스
		const checkedCnt = document.querySelectorAll('.chk:checked');
		if(chks.length == checkedCnt.length){
			checkAll.checked = true;
		} else{
			checkAll.checked = false;
		}
	});
}

//검색 영역의 카테고리 체크박스 중 '전체'외의 나머지 체크박스 클릭 시


