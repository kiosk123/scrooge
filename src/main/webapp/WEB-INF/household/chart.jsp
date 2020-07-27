<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp" />
<link rel="stylesheet" href="/scrooge/resources/styles/household/chart.css"/>
<script src="/scrooge/resources/js/household/chart.js"></script>
<meta charset="UTF-8">
<meta name="year" content="${year}"/>
<meta name="month" content="${month}"/>
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>차트 확인</title>
</head>
<body>
	<!-- nav bar -->
	<jsp:include page="../common/nav.jsp" />
	
	<!-- 년 월 선택 시작-->
	<div id="YEAR_AND_MONTH_CONTAINER" class="w3-content">
 	<table class="w3-content" >
 		<tr><!-- 첫번째 라인 -->
 			<td><!-- 첫번째칸 시작 -->
  			<select class="w3-select" name="year">
  			</select>
  			</td><!-- 첫번째칸 끝 두번째 칸 시작 -->
  			<td><h3>년&nbsp;&nbsp;&nbsp;&nbsp;</h3></td>
  			<td><!-- 두번째칸 끝 세번째 칸 시작 -->
  			<select class="w3-select" name="month">
  			</select>
  			</td><!-- 세번째칸 끝 네번째 칸 시작 -->
  			<td><h3>월&nbsp;&nbsp;</h3></td>
  			<td>
  			<button id="CHART_SEARCH" class="w3-btn w3-teal">검색</button>
  			</td>
  		</tr><!-- 첫번째 라인 끝 -->
	</table>
	</div>
	<br><br>
	<!-- 년 월 선택 끝 -->
	
	<!-- 지출 수입 버튼 추가부분 -->
	<p id="BUTTON_GROUP">
	 	<button id="SPEND_BUTTON" class="w3-btn w3-red w3-border w3-large w3-round-xlarge">지출 현황</button>
	 	<button id="INCOME_BUTTON" class="w3-btn w3-teal w3-border w3-large w3-round-xlarge">수입 현황</button>
	 	<button id="PROPORTION_BUTTON" class="w3-btn w3-teal w3-border w3-large w3-round-xlarge">수입 지출 비율</button>
	</p>
	<br><br><br>
	
	<!-- 차트를 그리는 부분 -->
	<div id="chart_draw" class="w3-content" style="width: 900px; height: 500px; border: 1px solid black;">
	</div>
	
</body>
</html>