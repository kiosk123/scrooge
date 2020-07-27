$(function(){
	
	//form 유효성 검증용 변수
	var id_vaild=false;
	var password_vaild=false
	
	//csrf 토큰 값
	var token = $("meta[name='_csrf']").attr("content"); 
	var header = $("meta[name='_csrf_header']").attr("content");
	
	
	//console.log("jquery load");
		
	//form 입력부분 유효성 검증 함수
	function formValidation(id,password){
		
		console.log(token);
		console.log(header);
		
		if(!id){
			id_vaild=false;
			alert("아이디를 입력해주세요");
			return;
		}
		
		if(!password){
			password_vaild=false;
			alert("비밀번호를 입력해주세요");
			return;
		}
		
		id_vaild=true;
		password_vaild=true;
	}
	
	
	
	//form submit 처리
	$("form").submit(function(event){
		event.preventDefault();
		var form=this;
		
		var id=$("input[name='id']").val();
		var password=$("input[name='password']").val();
		
		
		var user={};
		user.id=id;
		user.password=password;
		
		formValidation(user.id,user.password);
		
		if(id_vaild&&password_vaild){
		
			console.log("검증완료됨");
			$.ajax({
				type:"POST",
				url:"/scrooge/auth",
				data:JSON.stringify(user),
				beforeSend:function(xhr){
		            xhr.setRequestHeader(header, token);
		        },
				success:function(result,status,xhr){
					if(xhr.status==204)
					{
						form.submit();
					}					
				},
				error:function(xhr,status,error){
					if(xhr.status==404)
						alert("아이디를 찾을수 없습니다.");					
					
					if(xhr.status==400)
						alert("비밀번호를 확인해주세요");					
				},
				contentType:"application/json"
			});
			
		}
	});
	
	//회원 가입 페이지 이동
	$("#join_btn").click(function(){
		location.href="/scrooge/join";
	});
	 
}); 