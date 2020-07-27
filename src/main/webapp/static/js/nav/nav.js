//로그 아웃처리 버튼
$(function() {

	$("#LOGOUT_LINK").click(function() {
		$("#LOGOUT_SUBMIT").submit();
	});

	// 가계부 드랍다운 메뉴 보여주는 함수
	$("#SHOW_DROP_DOWN_MENU").hover(function() {
		$("#DROP_DOWN_MENU").addClass("w3-show");
	},function(){
		setTimeout(function(){
			if($("#DROP_DOWN_MENU :hover").length==0){
				$("#DROP_DOWN_MENU").removeClass("w3-show"); 
			}
		},1000);
	});

	// 가계부 드랍다운 메뉴 처리
	$("#DROP_DOWN_MENU > a").mouseout(function() {
		setTimeout(function(){
			if($("#DROP_DOWN_MENU :hover").length==0){
				$("#DROP_DOWN_MENU").removeClass("w3-show"); 
			}
		},1000);
	});

	// 관리자 드랍다운 메뉴 처리
	$("#SHOW_ADMIN_DROP_DOWN_MENU").hover(function() {
		$("#ADMIN_DROP_DOWN_MENU").addClass("w3-show");
	},function(){
		setTimeout(function(){
			if($("#ADMIN_DROP_DOWN_MENU :hover").length==0){
				$("#ADMIN_DROP_DOWN_MENU").removeClass("w3-show"); 
			}
		},1000);
	});

	$("#ADMIN_DROP_DOWN_MENU > a").mouseout(function() {
		setTimeout(function(){
			if($("#ADMIN_DROP_DOWN_MENU :hover").length==0){
				$("#ADMIN_DROP_DOWN_MENU").removeClass("w3-show"); 
			}
		},1000);
	});

});