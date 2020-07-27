<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp" />
<link rel="stylesheet" href="/scrooge/resources/styles/household/excel.css"/>
<script src="/scrooge/resources/js/household/excel.js"></script>
<meta charset="UTF-8">
<meta name="year" content="${year}" />
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}" />
<meta name="_csrf_header" content="${_csrf.headerName}" />
<title>엑셀 파일 다운로드</title>
</head>
<body>
	<jsp:include page="../common/nav.jsp" />
	
	<!-- 년 선택 시작-->	
	<div id="YEAR_CONTAINER" class="w3-content">
 	<table class="w3-content" >
 		<tr><!-- 첫번째 라인 -->
 			<td><!-- 첫번째칸 시작 -->
  			<select class="w3-select" name="year">
  			</select>
  			</td><!-- 첫번째칸 끝 두번째 칸 시작 -->
  			<td><h3>년&nbsp;&nbsp;&nbsp;&nbsp;</h3></td>
  			<td>
  			<button id="EXCEL_SEARCH" class="w3-btn w3-teal">검색</button>
  			</td>
  		</tr><!-- 첫번째 라인 끝 -->
	</table>
	</div>
	<!-- 년 선택 끝 -->
	<br><br><br>
	
	<!-- 다운로드 테이블 시작 -->
	<div id="EXCEL_CONTENT" class="w3-content">
	
	</div>
	
</body>
</html>