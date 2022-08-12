// (1) 회원정보 수정
function update(userId,event) {
	
	event.preventDefault(); // form태그 액션을 막기
	/* form 태크를 집어와서 .serialize(); 해주면 모든 input값들이 data에 담기게된다*/
	let data = $("#profileUpdate").serialize(); 
	
	
	$.ajax({
		type:"put",
		url:`/api/user/${userId}`,
		data:data,
		contentType:"application/x-www-form-urlencoded; charset=utf-8",
		dataType:"json" //응답은 json으로 받음
	}).done(res=>{ // res에 응답된데이터(json)가 파싱되서 들어옴, 그래서 res는 자바스크립트 오브젝트가 될 것임,
				   // HttpStatus 상태코드 200번대
		console.log("성공",res);
		location.href = `/user/${userId}`;
	}).fail(error=>{  // HttpStatus 상태코드 200번대 아닐시
		//alert(error.responseJSON.data.name);
		if(error.data==null){
			alert(JSON.stringify(error.responseJSON.message));
		}else{
			alert(JSON.stringify(error.responseJSON.data));	
		}
		
		console.log("실패",error.responseJSON.data.name);
	});
}