<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp"/>
<link rel="stylesheet" href="/scrooge/resources/styles/myinfo/withdrawMember.css"/>
<script src="/scrooge/resources/js/myinfo/withdrawMember.js"></script>
<meta charset="UTF-8">
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>회원탈퇴</title>
</head>
<body>
	<!-- nav bar -->
	<jsp:include page="../common/nav.jsp" />
	
	<div id="container">
	<h1>회원 탈퇴</h1>
	<br><br><br>  
	
	<!-- form 태그 시작 -->
	<form>	
		<div class="form-group">
			<h4><label for="password">탈퇴를 위해 비밀번호를 입력해주세요</label></h4>
			<input type="password" id="password" maxlength="12" name="password" class="form-control" placeholder="비밀번호" required><br> 
		</div>		
		<input type="button" class="btn btn-primary btn-lg btn-block" value="확인" />
	</form>
	</div>
</body>
</html>