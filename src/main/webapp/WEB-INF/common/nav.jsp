<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" trimDirectiveWhitespaces="true"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
        
<!-- 로그 아웃을 위한 폼 csrf 활성화시 로그아웃은 POST 방식이어야 하기 때문 -->
<form id="LOGOUT_SUBMIT" action="/scrooge/logout" method="post">      
<ul id="NAV_BAR" class="w3-navbar w3-black w3-large">
  <li><a class="w3-hover-blue w3-text-white" href="/scrooge/main">구두쇠 2.0</a></li>
  
  <!-- 드랍다운 메뉴 -->
  <li class="w3-dropdown-click ">
  	<a id="SHOW_DROP_DOWN_MENU"  class="w3-hover-blue w3-text-white" href="#">메뉴 <i class="fa fa-caret-down"></i></a>
  	<div id="DROP_DOWN_MENU" class="w3-dropdown-content w3-white w3-card-4">
    	<a class="w3-gray w3-hover-blue w3-text-white" href="/scrooge/household/list">가계부 작성</a>
    	<a class="w3-gray w3-hover-blue w3-text-white" href="/scrooge/chart">지출 차트 확인</a>
    	<a class="w3-gray w3-hover-blue w3-text-white" href="/scrooge/excel">가계부 엑셀 파일 다운</a>
  	</div>
  </li>
  
  <li><a class="w3-hover-blue w3-text-white" href="/scrooge/myinfo">내정보 수정</a></li>
  
  <!-- 드랍다운 메뉴 -->
  <security:authorize url="/admin/**"> 
  <li class="w3-dropdown-click ">
  	<a id="SHOW_ADMIN_DROP_DOWN_MENU"  class="w3-hover-blue w3-text-white" href="#">관리자 메뉴 <i class="fa fa-caret-down"></i></a>
  	<div id="ADMIN_DROP_DOWN_MENU" class="w3-dropdown-content w3-white w3-card-4">
    	<a class="w3-gray w3-hover-blue w3-text-white" href="/scrooge/admin/item">지출 수입 목록 편집</a>
  	</div>
  </li>
  </security:authorize>
  <li><a id="LOGOUT_LINK" class="w3-hover-blue w3-text-white" href="#">로그아웃</a></li>
</ul>
<!-- hidden 필드에 csrf 토큰 값을 숨겨야 한다. -->
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
</form>