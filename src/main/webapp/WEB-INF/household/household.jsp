<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp" />
<link rel="stylesheet" href="/scrooge/resources/styles/household/household.css" />
<script src="/scrooge/resources/js/lib/datepicker-ko.js"></script>
<script src="/scrooge/resources/js/lib/jquery.number.js"></script>
<script src="/scrooge/resources/js/household/household.js"></script>
<meta charset="UTF-8">
<meta name="year" content="${year}"/>
<meta name="month" content="${month}"/>
<!-- csrf 활성화되었고 ajax 통신시 필요할 데이터 -->
<meta name="_csrf" content="${_csrf.token}"/>
<meta name="_csrf_header" content="${_csrf.headerName}"/>
<title>가계부 작성</title>
</head>
<body>
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
  			<button id="HOUSEHOLD_SEARCH" class="w3-btn w3-teal">검색</button>
  			</td>
  		</tr><!-- 첫번째 라인 끝 -->
	</table>
	</div>
	<!-- 년 월 선택 끝 -->
	
	
	<!-- HOUSEHOLD_CONTENT 시작 -->
	<div id="HOUSEHOLD_CONTENT" class="w3-container">
	
	<!-- 등록 및 수정, 삭제 버튼 그룹 -->
	<p id="HOUSEHOLD_BUTTON_GROUP"></p>
	
	<!-- HOUSEHOLD_TABLE_TITLE 시작 -->
	<div id="HOUSEHOLD_TABLE_TITLE" class="w3-content">
	
	</div><!-- HOUSEHOLD_TABLE_TITLE 시작 -->
	
	<!-- HOUSEHOLD_TABLE_CONTENT 시작 -->
	<div id="HOUSEHOLD_TABLE_CONTENT" class="w3-content">

	</div><!-- HOUSEHOLD_TABLE_CONTENT 끝-->
	<br><br>
	<hr>
	<br><br>
	<!-- 수입 및 지출 및 총합계 표시 시작-->
	<div id="SHOW_RESULT_TABLE" class="w3-content">
	<table class="w3-table w3-bordered w3-border w3-large">
    <tr>
    	<th class="w3-red" style="text-align:center; width: 50%;">지출</th>
      	<td style="text-align:right;">￦<input type="text" id="spend_result" class="w3-border-0 w3-white" disabled="disabled" value="0" style="text-align: right"/></td>
    </tr>
    <tr>
      	<td class="w3-green" style="text-align:center;">수입</td>
      	<td style="text-align:right;">￦<input type="text" id="income_result" class="w3-border-0 w3-white" disabled="disabled" value="0" style="text-align: right"/></td>
    </tr>
    <tr>
      	<td class="w3-blue" style="text-align:center;">총합</td>
      	<td style="text-align:right;">￦<input type="text" id="total_result" class="w3-border-0 w3-white" disabled="disabled" value="0" style="text-align: right"/></td>
    </tr>
  	</table>
	
	</div><!-- 수입 및 지출 및 총합계 표시 끝-->	
	
	</div><!-- HOUSEHOLD_CONTENT 끝-->
	<br><br><br>
	

</body>
</html>