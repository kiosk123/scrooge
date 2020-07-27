<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/scrooge/resources/styles/popup/emailcode.css" />
<jsp:include page="../common/lib.jsp" />
<script src="/scrooge/resources/js/popup/emailcode.js"></script>
<meta charset="UTF-8">
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>이메일 인증 처리</title>
</head>
<body>
	<div class="w3-content">
	<!-- /join/emailProcess -->
	<table>
		<tr>
			<td colspan="2"><h3>인증 코드 발급 버튼을 눌러주세요</h3></td>
		</tr>
		<tr>
		<td><input class="w3-input w3-border" id="code" name="code" type="text" required="required" maxlength="10"/></td>
		<td>&nbsp;&nbsp;<button id="send_btn" class="w3-btn w3-blue w3-round w3-large" >인증코드 발급</button></td>
		</tr>
		<tr>
			<td colspan="2"><br></td>
		</tr>
		<tr>
		<td colspan="2"><button id="ok" class="w3-btn-block w3-teal w3-round w3-large" type="submit">확인</button></td>
		</tr>
	</table>
	<input type="hidden" id="email" name="email" value="${email}"/>
	</div>
</body>
</html>