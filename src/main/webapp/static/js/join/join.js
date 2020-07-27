$(function() {	
	// csrf 토큰 값
	var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");

	// 아이디 유효성 확인
	var id_ok = false;

	// 아이디 중복 확인
	var dup_id_check = false;

	// 패스워드 형식 확인
	var pass_ok = false;	
	
	// 메인으로 버튼 클릭시 작업
	$("#main_btn").click(function() {
		location.href = "/scrooge";
	});

	// 아이디 입력시 작업내용
	$("#id").keyup(function() {

		// 중복 아이디 체크는 false-아이디 값 변경시 중복아이디 다시 확인해야함
		dup_id_check = false;

		var val = $("#id").val();
		// 영어소문자랑 숫자로만 아이디 입력 걸러내는 정규식
		var pattern = /^[a-z0-9]*$/;

		if (val.length < 7) {
			$("#check_id").text("아이디는 7자리 이상입니다").css({"color" : "red"});
			id_ok = false;

		} else if (!pattern.test(val)) {
			$("#check_id").text("아이디는 영어 숫자로만 구성해주세요").css({"color" : "red"});
			id_ok = false;
		} else {
			$("#check_id").text("아이디 중복 체크를 해주세요").css({"color" : "blue"});
			id_ok = true; // 아이디 유효성 검사완료
		}
	});

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
			// console.log("확인할 비밀번호를 먼저 입력하세요 함수");
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

	// 이메일 인증 버튼 클릭시 처리
	$("#email_btn").click(function(event) {
		event.preventDefault();
		var coordinateX = screen.width / 3;
		var coordianteY = screen.height / 4;
		var option = "width=450,height=200,top="+coordianteY+",left="+coordinateX+",scrollbars=0,resizable=0,location=no, directories=no, status=no, menubar=no";
		var popup = window.open("/scrooge/join/email", "이메일 인증", option);
		if (!popup) {
			alert("팝업 차단기능 혹은 팝업차단 프로그램이 동작중입니다. 팝업 차단 기능을 해제한 후 다시 시도하세요.");
		}
	});

	// 아이디 중복버튼 클릭시 확인작업
	$("#dup_check").click(function(event) {

		event.preventDefault();

		if (!id_ok) {
			// console.log("아이디가 유효하지 못함");
			alert("사용할 아이디를 입력하세요");
		} else {
			// ajax로 컨트롤러에 중복확인할 아이디값 전송해서 결과값을 받음
			var x = {};
			x.id = $("#id").val();
			console.log(x.id + " : " + JSON.stringify(x));
			$.ajax({
				type : "POST",
				url : "/scrooge/join/idCheck",
				data : JSON.stringify(x),
				beforeSend : function(xhr) {
					// 로딩 이미지 시작
					startLoadingBar();
					xhr.setRequestHeader(header, token);

				},
				success : function(result) {
					dup_id_check = true;
					$("#check_id").text("사용가능한 아이디 입니다.").css({"color" : "green"});

				},
				error : function(xhr, status, error) {
					if (xhr.status != 400)
						alert("error : " + xhr.status);

					if (xhr.status == 400) {
						dup_id_check = false;
						$("#check_id").text("이미 존재하는 아이디 입니다.").css({"color" : "red"});
					}
				},
				complete : function() {
					// 로딩 이미지 완료
					endLoadingBar();
				},
				contentType : "application/json"
			});
		}
	});

	// 회원가입 버튼 눌렀을때 처리
	$("input[type='button']").click(function(event) {
		event.preventDefault();

		if (!id_ok)

			alert("사용할 아이디를 입력해주세요");
		else if (!pass_ok)
			alert("비밀번호 확인까지 완료해주세요");
		else if (!dup_id_check)
			alert("아이디 중복을 확인해 주세요");
		else if (!$("#email").val())
			alert("이메일 인증을 해주세요");
		else {
			// ajax로 회원가입 처리
			var x = {};
			x.id = $("#id").val();
			x.password = $("#password").val();
			x.email = $("#email").val();

			$.ajax({
				type : "POST",
				url : "/scrooge/join/member",
				data : JSON.stringify(x),
				beforeSend : function(xhr) {
					// 로딩 이미지 시작
					startLoadingBar();
					xhr.setRequestHeader(header, token);
				},
				success : function(result) {
					/* 회원가입 성공시 결과 메시지 출력하고 전송 */
					alert("회원가입을 축하합니다.");
					location.href = "/scrooge";
				},
				error : function(xhr, status, error) {
					if (xhr.status == 409) {
						dup_id_check = false;
						id_ok = false;
						$('#id').val("");
						alert("사용자 데이터가 중복됩니다.\n 다시 작성해주세요");
						$("form").reset();
					}
				},
				complete : function() {
					// 로딩 이미지 완료
					endLoadingBar();
				},
				contentType : "application/json"
			});

		}
	});

	// 1초마다 함수를 실행하여 아이디와 패스워드를 입력받았는지 실행
	setInterval(function() {

		// 아이디가 공백일시 처리
		if (!$('#id').val()) {
			$('#check_id').text("아이디를 입력해주세요").css({"color" : "blue"});
			id_ok = false;
			dup_id_check = false;
		}

		// 비밀번호 공백일시처리
		if (!$("#password").val()) {
			$('#pass_format').text("비밀번호를 입력해주세요").css({"color" : "blue"});
			pass_ok = false;
		}

	}, 100);
	
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