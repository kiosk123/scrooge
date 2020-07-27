$(function(){
	//csrf 때문에 ajax통신시 필요한 값
	var token = $("meta[name='_csrf']").attr("content"); 
	var header = $("meta[name='_csrf_header']").attr("content");
	
	$(document).on('click', "#GO_MAIN_BTN", function(){
		location.href="/scrooge";
	});	
	
	$("input[type='button']").click(function(){
		var pass=$("#password").val();
		if(!pass){
			alert("비밀번호를 입력해주세요");
			return;
		}
		
		// ajax로 회원 탈퇴 처리
		var x = {};
		x.password = pass;
		
		$.ajax({
			type : "DELETE",
			url : "/scrooge/myinfo/withdrawMember",
			data : JSON.stringify(x),
			beforeSend : function(xhr) {
				// 로딩 이미지 시작
				startLoadingBar();
				xhr.setRequestHeader(header, token);
			},
			success : function(data) {
				/*회원 탈퇴가 성공되었다는 것을 알림*/
				$("#container").empty();
				var h3=$("<h3/>",{"id":"SHOW_USER_INFO"}).appendTo("#container");
				$(h3).html("<span class='w3-text-teal'>"+data.id+"</span>님의 탈퇴가 성공적으로 완료되었습니다.");
				$("<br/>").appendTo("#container");
				$("<br/>").appendTo("#container");
				var button=$("<button/>",{"id":"GO_MAIN_BTN","class":"w3-btn btn-success w3-round-large w3-border w3-border-black"}).appendTo("#container");
				$(button).html("<span class='w3-xxlarge'>메인으로 이동</span>");
				$("#NAV_BAR").css("display","none");
				
				//뒤로가기 방지
				history.pushState(null, null, location.href); 
				window.onpopstate = function(event) { 
					history.go(1); 
				}
			},
			error : function(xhr, status, error) {
				if (xhr.status == 400) {
					alert("비밀번호가 일치하지 않습니다")
				}
				
				if(xhr.status!=400)
				{
					alert("error : "+xhr.status);
				}
			},
			complete : function() {
				// 로딩 이미지 완료
				endLoadingBar();
			},
			contentType : "application/json",
			dataType:"json",
			async:false
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
	
});