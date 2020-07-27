$(function(){
	
	//차트를 그리는데 필요한 데이터를 저장
	var CHART_DATA;
	
	yearAndMonthSetup();
	CHART_DATA=searchDataByAjax("chartData");
	renderView(CHART_DATA[0]);
	
	// 검색 버튼 눌렀을 때 작업 -라인참조할때 data-line 애트리뷰트를 이용할 것
 	$('#CHART_SEARCH').click(function(){
 		setYearAndMonth();
 		$("#SPEND_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-red");;
 		$("#INCOME_BUTTON").removeClass("w3-red").addClass("w3-teal");
 		$("#PROPORTION_BUTTON").removeClass("w3-red").addClass("w3-teal");
 		CHART_DATA=searchDataByAjax("chartData");
 		renderView(CHART_DATA[0]);
	}); 
	
 	//지출 현황 버튼 클릭시 작업
 	$("#SPEND_BUTTON").click(function(){
 		$("#SPEND_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-red");
 		$("#INCOME_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-teal");
 		$("#PROPORTION_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-teal");		
 		renderView(CHART_DATA[0]);
 	});
 	
 	//수입 현황 버튼 클릭시 작업
 	$("#INCOME_BUTTON").click(function(){
 		$("#INCOME_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-red");
 		$("#SPEND_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-teal");
 		$("#PROPORTION_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-teal");		
 		renderView(CHART_DATA[1]);
 	});
 	
 	//수입 지출 비율 확인 버튼 클릭시 작업
 	$("#PROPORTION_BUTTON").click(function(){
 		$("#PROPORTION_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-red");		
 		$("#INCOME_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-teal");
 		$("#SPEND_BUTTON").removeClass("w3-red").removeClass("w3-teal").addClass("w3-teal");		
 		renderPieChartView(CHART_DATA[2]);
 	});
	
	//검색버튼 눌렀을 때 년과 월 값 세팅 
	function setYearAndMonth(){		
		var year=$("select[name='year']").val();
		var month=$("select[name='month']").val();
		$("meta[name='month']").attr("content",month); 
		$("meta[name='year']").attr("content",year); 		
	}


	//검색 버튼 눌렀을 때는 ajax로 spendData를 서치해서 데이터를 가져와야한다.
	function searchDataByAjax(location){
		var ajaxVal;
		
		//서버에서 받아와서 메타태그에 저장한 년과 월을 읽어온다
		var year=$("select[name='year']").val();
		var month=$("select[name='month']").val();
			//csrf 때문에 ajax통신시 필요한 값
			
		var token = $("meta[name='_csrf']").attr("content"); 
		var header = $("meta[name='_csrf_header']").attr("content");
		var yearAndMonth={};
		yearAndMonth.year=year;
		yearAndMonth.month=month;
			
		$.ajax({
			type:"POST",
			url:"/scrooge/chart/"+location,
			data:JSON.stringify(yearAndMonth),
			//csrf방어를 위한 헤더 세팅
			beforeSend:function(xhr){
				startLoadingBar();
	            xhr.setRequestHeader(header, token);
	        },
			success:function(data,status,xhr){
				ajaxVal=data;
		
			},
			error:function(xhr,status,error){
				alert("error"+xhr.status);				
			},
			complete:function(){
				endLoadingBar();
			},
			async: false,
			contentType:"application/json",
			dataType:"json"
		});
		return ajaxVal;
	}
	
	
	//뷰 렌더링을 위해서 사용
	function renderView(ajaxVal){
		
		//라벨값과 금액을 표시하기 위한 배열
		var label=[''];
		var money=[''];
		
		//일단 차트 그리는 부분 일단 비우고 시작
		$("#chart_draw").empty();		
		
		if(ajaxVal.nameList.length==0){
			var h3=$("<h3/>",{"id":"msg"}).appendTo("#chart_draw");
			$(h3).text("데이터 없음");
			$(h3).addClass("w3-text-shadow w3-text-black w3-opacity");			
		}else{
			label=label.concat(ajaxVal.nameList);
			money=money.concat(ajaxVal.moneyList);
			renderChart(label,money);
		}		
	}
	
	//막대기 차트 렌더링을 위해서 사용
	function renderChart(label,money){
	
		var year=$("select[name='year']").val();
		var month=$("select[name='month']").val();
		var chartTitle=year+" 년 "+month+" 월";
		google.charts.load('current', {
			'packages' : [ 'corechart' ]
		});
		google.charts.setOnLoadCallback(drawVisualization);
		
		function drawVisualization() {
			// Some raw data (not necessarily accurate)
			var data = google.visualization.arrayToDataTable([label,money]);

			var options = {
				title : chartTitle,
				vAxis : {
					title : '원'
				},
				seriesType : 'bars'
			};

			var chart = new google.visualization.ComboChart(document.getElementById('chart_draw'));
			chart.draw(data, options);
		}
	}
	
	//파이 차트를 위한 뷰 렌더링을 위해 사용
	function renderPieChartView(ajaxVal){
		//라벨값과 금액을 표시하기 위한 배열
		var firstArr=[];
		var secondArr=[];
		
		//일단 차트 그리는 부분 일단 비우고 시작
		$("#chart_draw").empty();		
		
		if(ajaxVal.moneyList.length==0){
			var h3=$("<h3/>",{"id":"msg"}).appendTo("#chart_draw");
			$(h3).text("데이터 없음");
			$(h3).addClass("w3-text-shadow w3-text-black w3-opacity");			
		}else{
			firstArr.push(ajaxVal.nameList[0]);
			firstArr.push(ajaxVal.moneyList[0]);
			secondArr.push(ajaxVal.nameList[1]);
			secondArr.push(ajaxVal.moneyList[1]);
			renderPieChart(firstArr,secondArr);
		}		
	}
	
	//파이차트 렌더링을 위해 사용
	function renderPieChart(firstArr,secondArr){
		  var year=$("select[name='year']").val();
		  var month=$("select[name='month']").val();
		  var chartTitle=year+" 년 "+month+" 월 수입과 지출 비율";
		  
		  google.charts.load('current', {'packages':['corechart']});
	      google.charts.setOnLoadCallback(drawChart);
	      function drawChart() {

	        var data = google.visualization.arrayToDataTable([['항목', '비율'],firstArr,secondArr]);
	        var options = {
	          title: chartTitle
	        };
	        var chart = new google.visualization.PieChart(document.getElementById('chart_draw'));
	        chart.draw(data, options);
	      }
	}
	
	//상단의 년 클릭시 선택할 수있는 년도 값의 범위가 달라지게 하는 함수
	$("select[name='year']").click(function(){
		var year=$(this).val();
		var startYear=Number(year)-10;
		var endYear=Number(year)+10;
		
		if(startYear<=2010){
			startYear=2010
			endYear=Number(startYear)+20;
		}
		
		$("select[name='year']").empty();
		
		for(var i=startYear;i<endYear;i++){
			var option="<option class='w3-large' value="+i+">"+i+"</option>";
			$("select[class='w3-select'][name='year']").append(option);
		}
		
		$("select[name='year'] > option").each(function() {
			 if($(this).val()==year)
				 $(this).prop("selected",true);
		});
		
	});
	//페이지 로드 끝나고 시작하는 함수 --시작(년도 월선택 셀렉트 창을 만들어준다)
	function yearAndMonthSetup(){
		var year=$("meta[name='year']").attr("content");
		var month=$("meta[name='month']").attr("content");
	 	var startYear=Number(year)-10;
		var endYear=Number(year)+10;
		
		if(startYear<=2010){
			startYear=2010
			endYear=Number(startYear)+20;
		}		
		
		for(var i=startYear;i<=endYear;i++)
		{
			var option="<option class='w3-large' value="+i+">"+i+"</option>";
			$("select[class='w3-select'][name='year']").append(option);
		}
		
		for(var i=1;i<=12;i++){
			var option="<option class='w3-large' value="+i+">"+i+"</option>";
			$("select[class='w3-select'][name='month']").append(option);
		}
		
		
		$("select[name='year'] > option").each(function() {
			 if($(this).val()==year)
				 $(this).prop("selected",true);
		});
		
		$("select[name='month'] > option").each(function() {
			 if($(this).val()==month)
				 $(this).prop("selected",true);
		});		
	} //페이지 로드가 끝나고 시작하는 함수 --끝	
	
	//로딩이미지 시작
	function startLoadingBar(){
		$('body').oLoader({
			  wholeWindow: true, 
			  lockOverflow: false, 			   
			  backgroundColor: '#000',
			  fadeInTime: 1000,
			  fadeLevel: 0.4,
			  image: '/scrooge/resources/img/loader1.gif',  
		});
	}
	
	//로딩이미지 완료
	function endLoadingBar(){
		$('body').oLoader('hide');
	}
});