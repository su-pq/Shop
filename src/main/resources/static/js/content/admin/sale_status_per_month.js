//HTML이 뜨자마자 차트 생성 ajax 실행
getChartDataAjax();


//=================함수 선언=================//

//차트 생성 ajax
function getChartDataAjax() {
	const year = document.querySelector('#yearSelect').value;
	//ajax start
	$.ajax({
		url: '/admin/getChartDataAjax', //요청경로
		type: 'post',
		async: true, //동기/비동기
		//contentType: 'application/json; charset=UTF-8',
		//contentType: "application/x-www-form-urlencoded; charset=UTF-8",
		data: {'year' : year}, //필요한 데이터
		success: function(result) {
			console.log('saleCnt = ' + result['cntList']);
			console.log('salePrice = ' + result['saleList']);
			
			//차트 그리기
			drawChart(result['cntList'], result['saleList']);
			

		},
		error: function() {
			alert('실패');
		}
	});
	//ajax end
}
//차트 그리기
function drawChart(cntList, saleList) {
	const ctx = document.getElementById('myChart');
	//ctx : 실제로 그림이 그려지는 영역
	new Chart(ctx, {
		type: 'bar', //차트의 형태(line, bar, ...)
		data: {
			//lables : x축 제목(1월, 2월, ...)
			labels: [
				'1월', '2월', '3월',
				'4월', '5월', '6월',
				'7월', '8월', '9월',
				'10월', '11월', '12월'

			],
			//datasets 하나 == 그래프(막대) 하나(중괄호 하나까지가 그래프 하나)
			datasets: [
				//그래프1(막대1) : 월별판매금액
				{
					//lable : 화면 상단 범례(막대 제목)
					label: '월별판매금액',
					//data : y축 데이터(data의 데이터는 list 형식으로 들고와야 한다.)
					data: saleList,
					//borderwidth : 막대그래프의 테두리 두께
					borderWidth: 1,
					yAxisID: 'y1'
				},
				//그래프2(막대2) : 월별판매건수
				{
					//꺽은선 그래프로 그래프2의 타입 설정
					type: 'line',
					label: '월별판매건수',
					data: cntList,
					borderWidth: 2,
					yAxisID: 'y2'
				}
			]
		},
		options: {
			scales: {
				y: [
					{
						id: 'y1', // y축 ID 설정
						beginAtZero: true,
						type: 'linear',
						display:true,
						position: 'left', // 왼쪽에 y축 위치
						suggestedMin: 0
						
					},
					{
						id: 'y2', // y축 ID 설정
						beginAtZero: true,
						type: 'linear',
						display:true,
						position: 'right', // 오른쪽에 y축 위치
						suggestedMin: 0
					}
				]
			}
		}
	});
	
}

//년도 클릭 시
function getStatistics(){
	const year = document.querySelector('#yearSelect').value;
	location.href=`/admin/saleStatusPerMonth?year=${year}`;
}

