<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp" />
<meta charset="UTF-8">
<link rel="stylesheet" href="/scrooge/resources/styles/myinfo/myinfo.css"/>
<script src="/scrooge/resources/js/myinfo/myinfo.js"></script>
<title>내 정보 수정</title>
</head>
<body>
	<!-- nav bar -->
	<jsp:include page="../common/nav.jsp" />
	<div id="container" class="w3-content">
		<table class="w3-display-middle">
		<tr>
		<td>
		<button id="CHANGE_PASS_BTN" class="w3-btn btn-primary w3-round-large w3-border w3-border-black ">
			<span class="w3-xxlarge">비밀번호 변경</span>
		</button>
		&nbsp;&nbsp;
		</td>
		<security:authorize access="hasRole('ROLE_USER')">
		<td>
		<button id="WITHDRAW_MEMBER_BTN" class="w3-btn btn-success w3-round-large w3-border w3-border-black">
			<span class="w3-xxlarge">회원 탈퇴</span>
		</button>
		</td>
		</security:authorize>
		</tr>
		</table>
	</div>
</body>
</html>