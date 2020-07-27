<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp" />
<link rel="stylesheet" href="/scrooge/resources/styles/home/home.css"/>
<script src="/scrooge/resources/js/home/home.js"></script>
<meta charset="UTF-8">
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>구두쇠</title>
</head>
<body>
	<div id="container" class="container-fluid">
		<p><h1>구두쇠 version 2.0</h1></p>									
			<!-- form 시작 -->
			<form action="/scrooge/login/process" method="post" >
				
				<!-- form 필드 -->
				<div class="form-group">
					<h4><label for="id">아이디</label></h4>
					<input type="text" name="id" maxlength="15" class="form-control" required/><br> 
				</div> 
				<div class="form-group">
					<h4><label for="password">비밀번호</label></h4>
					<input type="password" name="password" maxlength="12" class="form-control" required/><br> 
				</div>				
					<button type="submit"  class="btn btn-primary btn-lg btn-block">로그인</button>
					<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
			</form>							
					<button id="join_btn" class="btn btn-danger btn-lg btn-block">회원 가입</button>
	</div>
</body>
</html>