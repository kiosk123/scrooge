<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/scrooge/resources/styles/join/join.css"/>
<jsp:include page="../common/lib.jsp" />
<script src="/scrooge/resources/js/join/join.js"></script>
<meta charset="UTF-8">
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>회원가입</title>
</head>

<!-- 바디 시작 -->
<body>

	<!-- 컨텐츠 랩핑 -->
	<div id="container">

	<h1 style="font-weight: bold;">회원가입</h1>

	<!-- form 태그 시작 -->
	<form>	
		<!-- form 필드 -->
		<div class="form-group">
			<button id="dup_check" class="btn btn-info btn-lg">아이디 중복 확인</button>
			<h4><label for="id">아이디&nbsp;&nbsp;<span id="check_id"></span></label></h4>
			<input type="text" id="id" name="id" class="form-control" placeholder="아이디" maxlength="15" required><br>			
		</div>
	
		<div class="form-group">
			<h4><label for="password">비밀번호&nbsp;&nbsp;<span id="pass_format"></span></label></h4>
			<input type="password" id="password" maxlength="12" name="password" class="form-control" placeholder="비밀번호" required><br> 
		</div>
		
		<div class="form-group">
			<h4><label for="passcheck">비밀번호 확인&nbsp;&nbsp;<span id="pass_check"></span></label></h4>
			<input type="password" id="passcheck" maxlength="12" name="passcheck" class="form-control" placeholder="비밀번호 확인" required><br> 
		</div>
		
		<div class="form-group">
			<button id="email_btn" class="btn w3-teal btn-lg">이메일 인증</button>
			<h4><label for="email">이메일&nbsp;&nbsp;</label></h4>
			<input type="text" id="email" maxlength="12" name="email" class="form-control" placeholder="example@email.com" readonly><br> 
		</div>
		
		<input type="button" class="btn btn-primary btn-lg btn-block" value="회원가입" />
	</form>
	<!-- form 태그 종료 -->			
	
		<button id="main_btn" class="btn btn-success btn-lg btn-block">메인으로</button>
	
	</div>
</body>
</html>