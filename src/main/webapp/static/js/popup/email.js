$(function(){
	
	var check=false;
	
	//csrf 토큰 값
	var token = $("meta[name='_csrf']").attr("content"); 
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$("input[name='email']").keyup(function(){
		check=false;
	});
	
	//이메일 체크 버튼 처리 함수 시작
	$("#email_check").click(function(event){
		
		event.preventDefault();
		
		//이메일 체크정규식
		var emailRegex= /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
		
		//공백 체크 정규식
		var spaceRegex=  /^\s+|\s+$/g;
		var email=$("input[name='email']").val();
		console.log(emailRegex.test(email));
	
		if(!email||(email.replace(spaceRegex,"") == "")){
			alert("이메일을 입력해주세요");
			$("input[name='email']").val('');
			check=false;
			return;
		}
		
		if(!emailRegex.test(email)){
			alert("이메일 형식을 맞춰 주세요\n예 : example@email.com ");
			$("input[name='email']").val('');
			check=false;
			return;
		}
		
		var x={};
		x.email=email;
		
		$.ajax({
			type:"POST",
			url:"/scrooge/join/emailCheck",
			data:JSON.stringify(x),
			beforeSend:function(xhr){
				//로딩 이미지 시작
				startLoadingBar();
				xhr.setRequestHeader(header, token);
			},
			success:function(result){						
				check=true;
				alert("사용 가능한 이메일 입니다.");
	
			},
			error:function(xhr,status,error){
				check=false;
				if(xhr.status!=400){
					alert("error : "+xhr.status);
					window.close();
					return;
				}
				
				if(xhr.status==400){					
					alert("이미 존재하는 이메일입니다.\n 다른 이메일을 입력해 주세요");
				}
			},
			async: false,
			complete:function(){
				//로딩 이미지 완료
				endLoadingBar();
			},
			contentType:"application/json"
		});		
		
	});////이메일 체크 버튼 처리 함수 끝
	
	//확인 버튼 클릭시 처리
	$("#ok").click(function(event){
		event.preventDefault();
		
		if(!check){
			alert("이메일 중복확인을 해주세요");
			return;
		}
		
		$("form").submit();
	});
	
	//로딩이미지 시작
	function startLoadingBar(){
		$('body').oLoader({
			  wholeWindow: true, 
			  lockOverflow: false, 		   
			  backgroundColor: '#000',
			  fadeInTime: 1000,
			  fadeLevel: 0.4,
			  image: '/scrooge/resources/img/loader1.gif',  
		});
	}
	
	//로딩이미지 완료
	function endLoadingBar(){
		$('body').oLoader('hide');
	}
});