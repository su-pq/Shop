// 현재 페이지의 URL을 가져오기
let currentUrl = location.href;
//li 태그 가져오기
const cartListTag = document.querySelector('#cartList');
const buyListTag = document.querySelector('#buyList');
const updateMemberInfoTag = document.querySelector('#updateMemberInfo');
console.log(currentUrl); // 예시: "http://example.com/page.html"
if(currentUrl.includes('cartList')){
	cartListTag.classList.add('active');
	buyListTag.classList.remove('active');
	updateMemberInfoTag.classList.remove('active');	
}else if(currentUrl.includes('buyList')){
	cartListTag.classList.remove('active');
	buyListTag.classList.add('active');
	updateMemberInfoTag.classList.remove('active');	
}else if(currentUrl.includes('updateMemberForm')){
	cartListTag.classList.remove('active');
	buyListTag.classList.remove('active');
	updateMemberInfoTag.classList.add('active');	
}