$(function(){
	
	var time=180;
	
	var check=false;
	
	//csrf 토큰 값
	var token = $("meta[name='_csrf']").attr("content"); 
	var header = $("meta[name='_csrf_header']").attr("content");
	
	//타이머 함수 실행
	function timer(){
		if(time==0){
			check=false;
			time=180;
			$("h3").text("인증코드를 다시 발급해주세요");
		}else{
			var minute=parseInt(time/60);
			var seconds=time%60;
			var remain=minute+"분 "+seconds+"초";
			$("h3").text(remain);
			time--;			
			setTimeout(timer,1000);	
		}
	}
	
	//인증코드 발급 버튼 처리
	$("#send_btn").click(function(event){
		time=180;
		timer();
	
		var email=$("#email").val();
		var x={};
		x.email=email;
		
		$.ajax({
			type:"POST",
			url:"/scrooge/join/sendCode",
			data:JSON.stringify(x),
			beforeSend:function(xhr){
				//로딩 이미지 시작
				startLoadingBar();
				xhr.setRequestHeader(header, token);
			},
			success:function(result){						
				check=true;
				alert("인증 코드를 발송하였습니다.");
			},
			error:function(xhr,status,error){
				check=false;
				alert("error : "+xhr.status);
				window.close();
			},
			async: false,
			complete:function(){
				//로딩 이미지 완료
				endLoadingBar();
			},
			contentType:"application/json"
		});		
	});
	
	//확인 버튼 클릭시 처리
	$("#ok").click(function(event){
		event.preventDefault();
 		
		var spaceRegex=  /^\s+|\s+$/g;
		var code=$("#code").val();
		if(!code||(code.replace(spaceRegex,"") == "")){
			alert("인증 코드를 입력하세요");
			return;
		}
		
		if(!check){
			alert("인증 코드를 다시 발급하세요");
			return;
		}
		var x={};
		x.code=code;
		$.ajax({
			type:"POST",
			url:"/scrooge/join/codeCheck",
			data:JSON.stringify(x),
			beforeSend:function(xhr){
				//로딩 이미지 시작
				startLoadingBar();
				xhr.setRequestHeader(header, token);
			},
			success:function(result){
				
				//자식창에서 부모창으로 값넘길때 input tag가 readonly true이면 값이 손실됨--;;;
				$(opener.document).find("#email").attr("readonly",false);
				$(opener.document).find("#email").val($("#email").val());
				$(opener.document).find("#email").attr("readonly",true);
				window.close();
			},
			error:function(xhr,status,error){
				if(xhr.status!=400){
					alert("error : "+xhr.status);
					window.close();
					return;
				}
				
				if(xhr.status==400){					
					alert("인증코드가 일치하지 않습니다.");
				}
				
			},
			async: false,
			complete:function(){
				//로딩 이미지 완료
				endLoadingBar();
			},
			contentType:"application/json"
		});		
		
		
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