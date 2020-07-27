<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="../common/lib.jsp" />
<link rel="stylesheet" href="/scrooge/resources/styles/household/main.css"/>
<link rel="stylesheet" href="/scrooge/resources/styles/lib/coin-slider-styles.css"/>
<script src="/scrooge/resources/js/lib/coin-slider.min.js"></script>
<script src="/scrooge/resources/js/household/main.js"></script>
<meta charset="UTF-8">
<title>구두쇠 version 2.0</title>
</head>
<body>
<!-- nav bar -->
<jsp:include page="../common/nav.jsp" />

<div id="container">

	<p><span id="title">구두쇠 ver 2.0</span></p>
	
	<div id="content">
	 <div id="coin-slider">
                <img alt="메인화면" src="/scrooge/resources/img/main1.jpg" />
                <span>구두쇠로 가계부를 편리하게 정리해보세요</span>
                <img alt="메인화면" src="/scrooge/resources/img/main2.jpg" />
				<span>당신은 간편하게 소비내역을 관리할 수 있습니다.</span>
                <img alt="메인화면" src="/scrooge/resources/img/main3.jpg" />
				<span>구두쇠를 통해 자산 관리 계획을 세워보세요</span>
				<img alt="메인화면" src="/scrooge/resources/img/main4.jpg" />
				<span>그래서 열심히 돈을 모아 보세요</span>
				<img alt="메인화면" src="/scrooge/resources/img/main5.jpg" />
				<span>돈을 모아 건물주가 되어 노후를 대비하세요</span>
	    </div>
	</div>
</div>
</body>
</html>