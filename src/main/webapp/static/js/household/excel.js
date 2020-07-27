$(function(){
	
	yearSetup();
	renderView(searchAjax());
	
	//검색 버튼을 클릭했을 때 작업
	$("#EXCEL_SEARCH").click(function(){
		 setYear();
		 renderView(searchAjax());
	});
	
	
	function renderView(ajaxVal){
		
		$("#EXCEL_CONTENT").empty();
		
		if(ajaxVal.linkList.length==0){
			var h3=$("<h3/>",{"id":"msg"}).appendTo("#EXCEL_CONTENT");
			$(h3).text("데이터 없음");
			$(h3).addClass("w3-text-shadow w3-text-black w3-opacity");	
		}else{
			//뷰를 렌더링 함...
			var table=$("<table/>",{"class":"w3-table w3-bordered"}).appendTo("#EXCEL_CONTENT");
			var tr=$("<tr/>",{"class":"w3-indigo"}).appendTo(table);
			var th=$("<th/>",{"style":"text-align:center;","class":"w3-large"}).appendTo(tr);
			$(th).text("가계부 다운로드");
			
			$(ajaxVal.linkList).each(function(i){
				tr=$("<tr/>",{"class":"w3-hover-yellow"}).appendTo(table);
				var td=$("<td/>",{"style":"text-align:center;","class":"w3-large"}).appendTo(tr);
				var a=$("<a/>",{"href":this,"class":"w3-text-black"}).appendTo(td);
				$(a).text(ajaxVal.contentList[i]);
			});
			
		}
	}
	
	//년과 월기록과 관련된 데이터를 가져오는 함수
	function searchAjax(){
		var ajaxVal;
		
		//서버에서 받아와서 메타태그에 저장한 년을 읽어온다
		var year=$("select[name='year']").val();
 		//csrf 때문에 ajax통신시 필요한 값
		var token = $("meta[name='_csrf']").attr("content"); 
 		var header = $("meta[name='_csrf_header']").attr("content");
 		var yearAndMonth={};
 		yearAndMonth.year=year;
 		
		$.ajax({
			type:"POST",
			url:"/scrooge/excel/search",
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
	function yearSetup(){
		var year=$("meta[name='year']").attr("content");
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
		
		$("select[name='year'] > option").each(function() {
			 if($(this).val()==year)
				 $(this).prop("selected",true);
		});	
	} //페이지 로드가 끝나고 시작하는 함수 --끝
	
	//셀렉트 태그에서 년값을 읽어와서 메타태그에 세팅한다.
	function setYear(){
		var year=$("select[name='year']").val();
		$("meta[name='year']").attr("content",year); 	
	}
	
	
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