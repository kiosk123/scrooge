<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp" />
<link rel="stylesheet" href="/scrooge/resources/styles/admin/item.css"/>
<meta charset="UTF-8">
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>지출 수입 목록 편집</title>
<script>
$(function(){
	
	var SPEND_ORIGIN;
	var INCOME_ORIGIN;
	
	//csrf 때문에 ajax통신시 필요한 값
	var token = $("meta[name='_csrf']").attr("content"); 
	var header = $("meta[name='_csrf_header']").attr("content");
	
	renderSpend(getSpendList());
	renderIncome(getIncomeList());
	
	function renderSpend(data){
		if(data.length==0){
			$("#SPEND_TABLE").empty();
			var tr=$("<tr/>").appendTo("#SPEND_TABLE");
			var td=$("<td/>",{"class":"w3-center"}).appendTo(tr);
			$(td).text("데이터 없음");
		}else{
			$("#SPEND_TABLE").empty();
			$(data).each(function(){
				var tr=$("<tr/>").appendTo("#SPEND_TABLE");
				var td=$("<td/>").appendTo(tr);
				var input=$("<input/>",{"type":"checkbox","class":"w3-check","name":"spendNo","value":this.no}).appendTo(td);
				td=$("<td/>").appendTo(tr);
				input=$("<input/>",{"type":"text","class":"w3-input","name":"spendName","value":this.name}).appendTo(td);
			});
		}
	}
	
	function renderIncome(data){
		if(data.length==0){
			$("#INCOME_TABLE").empty();
			var tr=$("<tr/>").appendTo("#SPEND_TABLE");
			var td=$("<td/>",{"class":"w3-center"}).appendTo(tr);
			$(td).text("데이터 없음");
		}else{
			$("#INCOME_TABLE").empty();
			$(data).each(function(){
				var tr=$("<tr/>").appendTo("#INCOME_TABLE");
				var td=$("<td/>").appendTo(tr);
				var input=$("<input/>",{"type":"checkbox","class":"w3-check","name":"incomeNo","value":this.no}).appendTo(td);
				td=$("<td/>").appendTo(tr);
				input=$("<input/>",{"type":"text","class":"w3-input","name":"incomeName","value":this.name}).appendTo(td);
			});
			
		}
	}
	
	function getSpendList(){
		var ajaxVal;
		$.ajax({
			type:"GET",
			url:"/scrooge/admin/spend",
			beforeSend:function(xhr){
				startLoadingBar();
	            xhr.setRequestHeader(header, token);
	        },
			success:function(data,status,xhr){
				ajaxVal=data;
		
			},
			error:function(xhr,status,error){
				alert("error"+xhr.status);				
			},
			complete:function(){
				endLoadingBar();
			},
			async: false,
			contentType:"application/json",
			dataType:"json"
		});
		return ajaxVal;
	}
	
	function getIncomeList(){
		var ajaxVal;
		$.ajax({
			type:"GET",
			url:"/scrooge/admin/income",
			beforeSend:function(xhr){
				startLoadingBar();
	            xhr.setRequestHeader(header, token);
	        },
			success:function(data,status,xhr){
				ajaxVal=data;
		
			},
			error:function(xhr,status,error){
				alert("error"+xhr.status);				
			},
			complete:function(){
				endLoadingBar();
			},
			async: false,
			contentType:"application/json",
			dataType:"json"
		});
		return ajaxVal;
	}
	
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

</script>
</head>
<body>
	<!-- nav bar -->
	<jsp:include page="../common/nav.jsp" />
	
	<div id="SPEND_ITEM_CONTAINER" >
	<table class="HEADER_TABLE">
	<tr>
		<td><h3>지출 목록 편집&nbsp;&nbsp;</h3></td>
		<td><button id="SPEND_UPDATE_BTN" class="w3-btn w3-teal">저장</button>&nbsp;</td>
		<td><button id="SPEND_DELETE_BTN" class="w3-btn w3-yellow">삭제</button></td>
	</tr>
	</table>
	<div id="SPEND_HEADER">
	<table class="w3-table w3-bordered">
		<tr class="w3-red w3-large">
			<td>삭제</td>
			<td>목록</td>
		</tr>
	</table>
	</div>
	<div id="SPEND_CONTENT">
	<table class="w3-table w3-bordered" id="SPEND_TABLE">		
	</table>
	</div>
	<table class="w3-table w3-bordered insertGroup">
		<tr>
			<td><input type="text" class="w3-input" id="SPEND_INSERT" /></td>
			<td><button id="SPEND_INSERT_BTN" class="w3-btn w3-blue">등록</button></td>
		</tr>
	</table>	
	</div>
	
	<div id="INCOME_ITEM_CONTAINER">
	
	<div id="INCOME_HEADER">
	<table class="HEADER_TABLE">
	<tr>
		<td><h3>수입 목록 편집 &nbsp;&nbsp;</h3></td>
		<td><button id="INCOME_UPDATE_BTN" class="w3-btn w3-teal">저장</button>&nbsp;</td>
		<td><button id="INCOME_DELETE_BTN" class="w3-btn w3-yellow">삭제</button></td>
	</tr>
	</table>
	<table class="w3-table w3-bordered">
		<tr class="w3-blue w3-large">
			<td>삭제</td>
			<td>목록</td>
		</tr>
	</table>
	</div>	
	<div id="INCOME_CONTENT">
	<table id="INCOME_TABLE" class="w3-table w3-bordered">
	</table>
	</div>	
	<table class="w3-table w3-bordered insertGroup">
		<tr>
			<td><input type="text" class="w3-input" id="INCOME_INSERT" /></td>
			<td><button id="INCOME_INSERT_BTN" class="w3-btn w3-blue">등록</button></td>
		</tr>
	</table>	
	</div>
	
</body>
</html>