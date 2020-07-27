<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp"/>
<link rel="stylesheet" href="/scrooge/resources/styles/myinfo/changePassword.css"/>
<script src="/scrooge/resources/js/myinfo/changePassword.js"></script>
<meta charset="UTF-8">
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>비밀번호 변경</title>
</head>
<body>
	<!-- nav bar -->
	<jsp:include page="../common/nav.jsp" />
	
	<!-- 컨텐츠 랩핑 -->
	<div id="container">
	
	<h1>비밀번호 변경</h1>
	<br><br><br>  
	
	<!-- form 태그 시작 -->
	<form>	
		<div class="form-group">
			<h4><label for="password">비밀번호&nbsp;&nbsp;<span id="pass_format"></span></label></h4>
			<input type="password" id="password" maxlength="12" name="password" class="form-control" placeholder="비밀번호" required><br> 
		</div>
		
		<div class="form-group">
			<h4><label for="passcheck">비밀번호 확인&nbsp;&nbsp;<span id="pass_check"></span></label></h4>
			<input type="password" id="passcheck" maxlength="12" name="passcheck" class="form-control" placeholder="비밀번호 확인" required><br> 
		</div>
		
		<input type="button" class="btn btn-primary btn-lg btn-block" value="확인" />
	</form>
	</div>
	
</body>
</html>