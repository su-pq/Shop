const div = document.querySelector(".container-content"); // 해당 div 요소를 가져옵니다.
const height = div.offsetHeight; // 해당 div 요소의 너비를 가져옵니다.

if (height < 700) { // 해당 div 요소의 너비가 1000보다 작을 경우에만
  div.style.height = "700px"; // div 요소의 너비를 1000px로 설정합니다.
}