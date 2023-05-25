//const ctx = document.getElementById('myChart');
getSaleStatusByCategoryAjax();

//차트 생성 ajax
function getSaleStatusByCategoryAjax() {

	//ajax start
	$.ajax({
		url: '/admin/getSaleStatusByCategoryAjax', //요청경로
		type: 'post',
		async: true, //동기/비동기
		//contentType: 'application/json; charset=UTF-8',
		//contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {}, //필요한 데이터
		success: function(result) {
			//vo객체로 조회했을 경우
			//drawChart(result['cateNameList'], result['saleCntList']);
			
			//map으로 조회했을 경우
			console.log(result);
			drawChart2(result);
			drawTable(result);
			
		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}

//표 그리기
function drawTable(data){
	//테이블을 그릴 태그 선택
	const tableDiv = document.querySelector('.tableDiv');
	
	//그려질 테이블 태그를 문자열로 작성
	let str = ``;
	str += `<table class="table text-center">`;
	str += `<thead class="table-head">`;
	str += `<tr>`;
	str += `<td>No</td>`;
	str += `<td>카테고리명</td>`;
	str += `<td>누적 판매 개수</td>`;
	str += `</tr>`;
	str += `</thead>`;
	str += `<tbody>`;
	data.forEach(function(e, index) {
	str += `<tr>`;
	str += `<td>${data.length - index}</td>`;
	str += `<td>${e['CATE_NAME']}</td>`;
	str += `<td>${e['SUM_BUY_CNT']}</td>`;
	str += `</tr>`;
	});
	str += `</tbody>`;
	str += ``;
	str += `</table>`;
	
	tableDiv.insertAdjacentHTML('afterbegin', str);
		
}

//차트 그리기
function drawChart(cateNameList, saleCntList) {
	const ctx = document.getElementById('category-pie-chart');
	new Chart(ctx, {
		type: 'pie',
		data: {
			labels: cateNameList,
			datasets: [{
				label: "판매수량",
				//backgroundColor: Object.values(Utils.CHART_COLORS),
				data: saleCntList
			}]
		},
		  options: {
			responsive: true,
			plugins: {
				//범례 위치를 상단에 배치
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: '카테고리별 판매 추이'
				}
			}
		}

	});
}
//차트 그리기2
function drawChart2(data) {
	const ctx = document.getElementById('category-pie-chart');
	const cateNameArr = [];
	const sumBuyCntArr = [];
	data.forEach(function(item, index) {
		cateNameArr.push(item['CATE_NAME']);
		sumBuyCntArr.push(item['SUM_BUY_CNT']);
	});
	
	new Chart(ctx, {
		type: 'pie',
		data: {
			labels: cateNameArr,
			datasets: [{
				label: "판매수량",
				//backgroundColor: Object.values(Utils.CHART_COLORS),
				data: sumBuyCntArr
			}]
		},
		  options: {
			responsive: true,
			plugins: {
				//범례 위치를 상단에 배치
				legend: {
					position: 'top',
				},
				title: {
					display: true,
					text: '카테고리별 판매 추이'
				}
			}
		}

	});
}
