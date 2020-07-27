$(function(){
	
	//csrf 때문에 ajax통신시 필요한 값
	var token = $("meta[name='_csrf']").attr("content"); 
	var header = $("meta[name='_csrf_header']").attr("content");
	
	// 패스워드 형식 확인
	var pass_ok = false;
	
	// 비밀번호 입력시 작업내용
	$("#password").keyup(function() {

		var val = $("#password").val();
		$('#pass_check').text("");
		$('#passcheck').val("");

		if (val.length < 8) {

			$("#pass_format").text("비밀번호는 8자리 이상이어야 합니다.").css({"color" : "red"});
			pass_ok = false;

		} else {

			$("#pass_format").text("비밀번호 형식이 맞습니다.").css({"color" : "green"});
			pass_ok = false;
		}
	});

	// 패스워드창에 패스워드 입력없이 패스워드 확인하려고 할때 작업 지정
	$("#passcheck").focus(function() {

		var val = $("#password").val();
		if (!val) {
			$('#pass_check').text("확인 할 비밀번호를 먼저입력하세요").css({"color" : "red"});
			pass_ok = false;
			$('#password').focus();
		}
	});

	// 비밀번호 확인시 작업내용
	$("#passcheck").keyup(function() {
		var val = $("#password").val();
		var check = $('#passcheck').val();

		if (val != check || val.length < 8) {
			$('#pass_check').text("비밀번호가 다릅니다").css({"color" : "red"});
			pass_ok = false;
		} else if (val == check) {
			$('#pass_check').text("비밀번호 확인이 되었습니다.").css({"color" : "green"});
			pass_ok = true;
		}
	});
	
	//비밀번호 변경 버튼을 눌렀을때
	$("input[type='button']").click(function(event) {
		event.preventDefault();
		if (!pass_ok){
			alert("비밀번호 확인까지 완료해주세요");
			return;
		}
			// ajax로 패스워드 변경 처리
			var x = {};
			x.password = $("#password").val();

			$.ajax({
				type : "PUT",
				url : "/scrooge/myinfo/changePassword",
				data : JSON.stringify(x),
				beforeSend : function(xhr) {
					// 로딩 이미지 시작
					startLoadingBar();
					xhr.setRequestHeader(header, token);
				},
				success : function(result) {
					/* 비밀번호 변경 성공시 변경이 성공되었다는 것을 알리고 페이지 리로드*/
					alert("비밀번호가 변경되었습니다.");
					location.reload(true);
				},
				error : function(xhr, status, error) {
					if (xhr.status == 409) {
						alert("기존 비밀번호와 동일합니다. \n다른 비밀번호를 입력해주세요.");
					}
					
					if(xhr.status!=409)
					{
						alert("error : "+xhr.status);
					}
				},
				complete : function() {
					// 로딩 이미지 완료
					endLoadingBar();
				},
				contentType : "application/json"
			});

	});
	
	//로딩이미지 시작
	function startLoadingBar() {
		$('body').oLoader({
			wholeWindow : true,
			lockOverflow : false,
			backgroundColor : '#000',
			fadeInTime : 1000,
			fadeLevel : 0.4,
			image : '/scrooge/resources/img/loader1.gif',
		});
	}

	// 로딩이미지 완료
	function endLoadingBar() {
		$('body').oLoader('hide');
	}
});
