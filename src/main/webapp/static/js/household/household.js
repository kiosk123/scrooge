$(function(){
	//////////////////전역변수는 어떤 코드 보다도 가정 먼저 나와야 한다!!!!!!///////////////////////////////////////////////////////////
	//라인 참조시 사용
	var nextLine='LINE_1';
	var categoryList;
	var spendList;
	var incomeList;
	//전체 체크 박스 해제 및 선택시 사용할 플래그
	var wholeCheck=false;
	
	var SPEND// 지출값
	var INCOME//수입값
	var INCOME_ETC; //수입의 기타값을 저장
	var SPEND_ETC; //지출의 기타값을 저장
	var ORIGIN_DATA; //업데이트 할 시 비교할 기존데이터를 저장할 변수
	
	//csrf 때문에 ajax통신시 필요한 값
	var token = $("meta[name='_csrf']").attr("content"); 
	var header = $("meta[name='_csrf_header']").attr("content");

	//날짜를 선택하기 위한 상단의 셀렉트 태그의 옵션으로 사용할 year와 month를 셋업한다.
	yearAndMonthSetup();
	renderView(searchAjax());//컨트롤러에서 뷰호출완료 되자 마자 ajax로 뷰 렌더링... +총 수입, 총 지출, 총합계를 테이블에 출력하는 기능 추가해야됨
	addNewLines(); //그리고 뷰 렌더링 완료되면 기본적으로 가져온 데이러라인에 +5 사용자가 기본적으로 입력할 수 있는 라인 추가
	
	//지출이 수입보다 크면 총합이 -가 나와야하는데 +가 나온다 뷰렌더링 되는 속도가 너무 빨라서인듯... 그래서 다음과 같이 편법으로 타이머 함수를 추가한다.
	setTimeout(function(){
		$("select[name='category']").last().trigger('click'); 
	},500);
	
	//결과를 보여주는 테이블에 넘버함수 적용
	$("#spend_result").number(true,0); 
	$("#income_result").number(true,0); 
	$("#total_result").number(true,0); 
	
	// 검색 버튼 눌렀을 때 작업 -라인참조할때 data-line 애트리뷰트를 이용할 것
 	$('#HOUSEHOLD_SEARCH').click(function(){
 		setYearAndMonth();
 		renderView(searchAjax());
		addNewLines();		
	});
 	
 	//금액 입력부분 클릭했을때 input 태그안의 텍스트 전체선택
 	$("input[name='money']").click(function(){
 		$(this).select();
 	});
	
 	//라인추가 버튼을 눌렀을 때 작업
	$(document).on('click', '#HOUSEHOLD_NEW_LINE', function(){
		addNewLines();
	});	
	
	//삭제 버튼을 눌렀을 때 작업
	$(document).on('click', '#HOUSEHOLD_DELETE', function(){				
		
		var obj={}; //전송시 JSON전달할때 필요한 객체
 		
 		//삭제할 NO값을 세팅하는 배열
 		var arr=[];
				
		//obj에 년도와 월을 세팅해준다
		var year=$("select[name='year']").val();
 		var month=$("select[name='month']").val();
 		var x={};
 		x.year=year;
 		x.month=month;
		obj.yearAndMonth=x;
 		
 		//삭제 여부를 묻는다.
 		if(!confirm("선택한 내용을 삭제하시겠습니까?"))
 			return;
 		
 		//삭제할 데이터를 선택하지 않은 경우
 		if($("input[name='no']:checked").length==0){
 			alert("삭제할 데이터를 먼저 선택해 주시기 바랍니다");
 			return;
 		}
 		
 		//삭제할 데이터중에서 no값이 없는 라인은 그냥 여기서 삭제된것 처럼 처리하고 체크박스 체크풀고 걸러낸다
 		$("input[name='no']:checked").each(function(){
 			var x=$(this).val();
 			if(x=='on'){
 				var line=$(this).attr("data-line");
 				$("input[name='date'][data-line="+line+"]").val("");
 				$("input[name='money'][data-line="+line+"]").val(0);
 				$("input[name='content'][data-line="+line+"]").val("");
 				$("select[name='category'][data-line="+line+"] option").filter(function() {
 				    return $(this).val() == SPEND; //카테고리를 지출로 바꾸고
 				}).prop('selected', true);
 				$("select[name='category'][data-line="+line+"]").click(); //클릭처리...
 				$("select[name='spend'][data-line="+line+"] option").filter(function() {
 				    return $(this).val() == SPEND_ETC; //지출항목을 기타로 바꿈
 				}).prop('selected', true);
 				$(this).prop("checked",false);
 			}
 		});
 		
 		//체크 박스 반복 - 디비에서 실제삭제할 데이터들...
		$("input[name='no']:checked").each(function(){
			arr.push($(this).val()); 
		});
		
		
		//arr의 길이가 0보다 크면 삭제할 데이터가 있다는 이야기이므로
		if(arr.length>0){
			//obj에 다음과 같이 세팅해준다 --obj객체는 {"yearAndMonth":{"year":...:"month":...},"deleteList":[{},{}...]}
			obj.deleteList=arr;
			
			$.ajax({
				type:"DELETE",
				url:"/scrooge/household/list",
				data:JSON.stringify(obj),
				//csrf방어를 위한 헤어 세팅
				beforeSend:function(xhr){
					//로딩 이미지 시작
					startLoadingBar();
		            xhr.setRequestHeader(header, token);
		        },
				success:function(data,status,xhr){
					renderView(data);
					addNewLines();		
				},
				async: false,
				error:function(xhr,status,error){
					alert("error"+xhr.status);
					alert("삭제에 실패하였습니다")
				},complete:function(){
					//로딩 이미지 완료
					endLoadingBar();
				},
				contentType:"application/json",
				dataType:"json"
			});
		}
	});
	
	//등록 및 수정 버튼을 눌렀을 때 작업 처리 함수 시작
	$(document).on('click', '#HOUSEHOLD_INPUT_MODIFY', function(){
		var lastLine=nextLine.substring(nextLine.lastIndexOf("_")+1,nextLine.length)-1;
		var startLine=1;
		var linePrefix='LINE_';
		var index=0;
		var obj={}; //전송시 JSON전달할때 필요한 객체
		var registerOK=true;
		
		//content의 값이 공백으로만 이루어져있는지 확인하기 위해
		var spaceRegex=  /^\s+|\s+$/g;
		
		
		//obj에 년도와 월을 세팅해준다
		var year=$("select[name='year']").val();
 		var month=$("select[name='month']").val();
 		var x={};
 		x.year=year;
 		x.month=month;
		obj.yearAndMonth=x;
		
		//데이터를 json 배열을 만들기 위한 변수
		var arr=[];
		var line,no,date,content,category,spend,money,income;
		
		//기존 데이터와 비교하여 업데이트된 내용이 있는지 확인
		$(ORIGIN_DATA).each(function(i){
			index=startLine+i;
			line=linePrefix+index; 
			no=$("input[name='no'][data-line="+line+"]").val();
			date=$("input[name='date'][data-line="+line+"]").val();
			content=$("input[name='content'][data-line="+line+"]").val();
			category=$("select[name='category'][data-line="+line+"]").val();
			spend=$("select[name='spend'][data-line="+line+"]").val();
			income=$("select[name='income'][data-line="+line+"]").val();
			money=$("input[name='money'][data-line="+line+"]").val();
			
			
			if((content.replace(spaceRegex,"") == "")||!content){
				alert("내역을 입력하세요");
				$("input[name='content'][data-line="+line+"]").focus();
				registerOK=false;
				return;
			}
			
			if(!date)
			{
				alert("날짜를 입력하세요");
				$("input[name='date'][data-line="+line+"]").focus();
				registerOK=false;
				return;
			}
			
			//날짜와 내용이 입력되었을 때만 수정 및 등록이 가능하게 한다. 
			if(date&&content&&!(content.replace(spaceRegex,"") == "")){					
				
				if(date!=makeISOdate(this.date)||content!=this.content||category!=this.category||spend!=this.spend||income!=this.income||money!=this.money){
			
					var x={};
					x.no=no;
					x.date=date;
					x.content=content;
					x.category=category;
					if(x.category==SPEND){				
						x.spend=spend;
						x.income=INCOME_ETC;
					}else{						
						x.income=income;
						x.spend=SPEND_ETC;
					}
					x.money=money;
					//x.spend=spend;
					//x.income=income;
					arr.push(x);
					
				}				
			}			
		});
		startLine=++index; 
		
		if(registerOK){
			//반복문 시작 새로운 입력처리
			for(var i=startLine;i<=lastLine;i++){
				line=linePrefix+i;
				date=$("input[name='date'][data-line="+line+"]").val();
				content=$("input[name='content'][data-line="+line+"]").val();
				category=$("select[name='category'][data-line="+line+"]").val();
				spend=$("select[name='spend'][data-line="+line+"]").val();
				income=$("select[name='income'][data-line="+line+"]").val();
				money=$("input[name='money'][data-line="+line+"]").val();
				
				if(date&&(content.replace(spaceRegex,"") == "")||!date&&!(content.replace(spaceRegex,"") == "")){
					if(!date){
						alert("날짜를 입력해 주세요");
						$("input[name='date'][data-line="+line+"]").focus();
						return;
					}
					
					if(content.replace(spaceRegex,"") == ""){
						alert("내역을 입력해 주세요");
						$("input[name='content'][data-line="+line+"]").focus();
						return;
					}
					
				}
			
			
				//날짜와 내용이 입력되었을 때만 수정 및 등록이 가능하게 한다. 
				if(date&&content&&!(content.replace(spaceRegex,"") == "")){
					//object 생성
					var x={};
				
					x.date=date;
					x.content=content;
					x.category=category;
				
					//선택한 구분이 지출목록이라면
					if(category==SPEND){
						x.spend=spend; //지출목록은 그대로 입력
						x.income=INCOME_ETC;  //수입목록은 기타로 바꿈
					}else{
						x.income=income;
						x.spend=SPEND_ETC;
					}		
				
					x.money=money;
					arr.push(x);
				
				}
			
			}//반복문 종료 
			//alert(JSON.stringify(arr)); //업데이트된 데이터가 있는지 JSON으로 보여주기 위함
			//arr에 데이터가 있을때만 ajax요청을 해야하기 때문에
			if(arr.length>0){
			
				//obj객체는 {"yearAndMonth":{"year":...:"month":...},"householdList":[{},{}...]}
				obj.houseHoldList=arr;
				$.ajax({
					type:"POST",
					url:"/scrooge/household/list",
					data:JSON.stringify(obj),
					//csrf방어를 위한 헤더 세팅
					beforeSend:function(xhr){
						//로딩 이미지 적용
						startLoadingBar();
						xhr.setRequestHeader(header, token);
					},
					success:function(data,status,xhr){
						renderView(data); //등록 및 수정 처리후 뷰지우고 다시 뷰렌더링
						addNewLines();//그리고 사용자가 입력할 수 있는 라인 5줄 다시 추가								
					},
					async: false,
					error:function(xhr,status,error){
						alert("error"+xhr.status);
						alert("저장에 실패하였습니다");
					},complete:function(){
						endLoadingBar();
					},
					contentType:"application/json",
					dataType:"json"
				});
			}
		}
	});//등록 및 수정 버튼을 눌렀을 때 작업 처리 함수 끝
	
	//삭제 탭 눌렀을때 전체 체크박스 해제 및 선택
	$(document).on('click', 'th', function(){
		if(($(this).text()=='삭제')&&(wholeCheck==false)){
			$("input[type='checkbox']").prop("checked",true);
			wholeCheck=true;
		}else if(($(this).text()=='삭제')&&(wholeCheck!=false)){
			$("input[type='checkbox']").prop("checked",false);
			wholeCheck=false;
		}
	});//삭제 탭 눌렀을때 전체 체크박스 해제 및 선택 함수 끝  
	
	//구분 셀렉트 버튼 클릭시 뷰에서 처리해주는 함수
	$(document).on('click', "select[name='category']", function(){
		var line=$(this).attr('data-line');
		console.log(line);
		//구분에서 지출을 선택시 지출항목을 선택하면 수입항목이 disabled hidden 수입항목을 선택하면 지출항목이 disabled hidden이 되게한다.
		if($(this).val()==SPEND){
			$(this).removeClass("w3-blue").addClass("w3-red");
			$("select[data-line="+line+"][name='spend']").prop("disabled",false);
			$("select[data-line="+line+"][name='spend']").css({"visibility":"visible"});
			$("select[data-line="+line+"][name='income']").prop("disabled",true);
			$("select[data-line="+line+"][name='income']").css({"visibility":"hidden"});
			$("input[name='money'][data-line="+line+"]").css("color","red");
		}else{
			$(this).removeClass("w3-red").addClass("w3-blue");
			$("select[data-line="+line+"][name='spend']").prop("disabled",true);
			$("select[data-line="+line+"][name='spend']").css({"visibility":"hidden"});
			$("select[data-line="+line+"][name='income']").prop("disabled",false);
			$("select[data-line="+line+"][name='income']").css({"visibility":"visible"});
			$("input[name='money'][data-line="+line+"]").css("color","green");
		}
		
		//구분이 달라지면 총합계 지출도 달라야 하므로 값을 수정해야 한다.
		//지출합계와 수입합계를 변수에 받아서 저장
		var x=sumSpend();
		var y=sumIncome();
		
		//결과 테이블의 지출합계 부분에 지출합계 저장
		$("#spend_result").val(x);
		
		//결과 테이블의 수입합계 부분에 수입합계 저장
		$("#income_result").val(y);
		
		//총합을 구한다음 결과 테이블의 총합계 부분에 총합결과값을 저장
		var total=calTotal(x,y);
		$("#total_result").val(total);
		
	});//구분 셀렉트 버튼 클릭시 뷰에서 처리해주는 함수 함수 끝
	
	//검색버튼 눌렀을 때 년과 월 값 세팅 - 등록/수정 및 삭제시필요
	function setYearAndMonth(){		
		var year=$("select[name='year']").val();
 		var month=$("select[name='month']").val();
		$("meta[name='month']").attr("content",month); 
		$("meta[name='year']").attr("content",year); 		
	}
	
	
	//검색 버튼 눌렀을 때는 ajax로 서치해서 데이터를 가져와야한다.
	function searchAjax(){
		var ajaxVal;
		
		//서버에서 받아와서 메타태그에 저장한 년과 월을 읽어온다
		var year=$("select[name='year']").val();
 		var month=$("select[name='month']").val();

 		var yearAndMonth={};
 		yearAndMonth.year=year;
 		yearAndMonth.month=month;
 		
		$.ajax({
			type:"POST",
			url:"/scrooge/household/search",
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
	
	
	//검색후 데이터를 가져올때 사용하는 함수 -renderView 함수 시작
	function renderView(ajaxVal){
		
 		nextLine='LINE_1';
 		
 		//spendList와 incomeList와 categoryList는 다른 기능에서도 참조하기 때문에 전역 변수로 냅둔다.
 		spendList=ajaxVal.spendList;
		categoryList=ajaxVal.categoryList;
		incomeList=ajaxVal.incomeList;
		ORIGIN_DATA=ajaxVal.houseHoldList;
 		var houseHoldList=ajaxVal.houseHoldList;
 		
 		//지출 수입 총합을 보여줄 테이블에서 뿌려줄 값을 저장하는 변수
 		var incomeTotal=ajaxVal.incomeTotal;
 		var spendTotal=ajaxVal.spendTotal;
 		var total=ajaxVal.total;
 		
 		//지출,수입의 각 카테고리값과 기타값 세팅
 		spendSetter(categoryList);
 		incomeSetter(categoryList);
 		spendETCSetter(spendList);
 		incomeETCSetter(incomeList);
 		
 		//지출 수입 총합을 보여줄 테이블에 값을 뿌려준다.
 		$("#spend_result").val(spendTotal);
 		$("#income_result").val(incomeTotal);
 		$("#total_result").val(total);
 		
 		//검색 버튼 누를시 버튼그룹 태그안에 있는 코드를 한번 비우고 시작
 		$('#HOUSEHOLD_BUTTON_GROUP').empty();
 		$('#HOUSEHOLD_TABLE_TITLE').empty();
 		$('#HOUSEHOLD_TABLE_CONTENT').empty();
 		
 		//등록 및 수정, 삭제 버튼 그룹 생성
		$("<input/>",{'type':'button','id':'HOUSEHOLD_INPUT_MODIFY','class':'w3-btn w3-large w3-teal','value':'등록 및 수정'}).appendTo('#HOUSEHOLD_BUTTON_GROUP');
		$("<input/>",{'type':'button','id':'HOUSEHOLD_DELETE','class':'w3-btn w3-large w3-red','value':'삭제'}).appendTo('#HOUSEHOLD_BUTTON_GROUP');
		$("<input/>",{'type':'button','id':'HOUSEHOLD_NEW_LINE','class':'w3-btn w3-large w3-blue','value':'라인 추가'}).appendTo('#HOUSEHOLD_BUTTON_GROUP');
	
		var table=$('<table/>',{'data-table':'HOUSEHOLD_TABLE_TITLE','class':'w3-table w3-bordered w3-border w3-large'}).appendTo("#HOUSEHOLD_TABLE_TITLE");
		var tr=$('<tr/>',{'data-line':'LINE_0','class':'w3-green'}).appendTo(table);
		var th=$('<th/>',{'style':'text-align:left; width:70px;'});
		th.text('삭제');
		$(th).appendTo(tr);
		th=$('<th/>',{'style':'text-align:center; width:200px;'});
		th.text('일시');
		$(th).appendTo(tr);
		th=$('<th/>',{'style':'text-align:left;width:130px;'});
		th.text('구분');
		$(th).appendTo(tr);
		th=$('<th/>',{'style':'text-align:left;'});
		th.text('항목');
		$(th).appendTo(tr);
		th=$('<th/>',{'style':'text-align:left;'});
		th.text('내역');
		$(th).appendTo(tr);
		th=$('<th/>',{'style':'text-align:left;'});
		th.text('금액');
		$(th).appendTo(tr);
		
		
		table=$('<table/>',{'data-table':'HOUSEHOLD_TABLE_CONTENT','class':'w3-table w3-bordered w3-border'}).appendTo("#HOUSEHOLD_TABLE_CONTENT");
		//시작할 라인의 넘버 추출과 LINE_ 접두사
		var lineNum=nextLine.substring(nextLine.lastIndexOf("_")+1,nextLine.length);
		var linePrefix='LINE_';
		
		$(houseHoldList).each(function(){
			var line=linePrefix+lineNum;
			var date=makeISOdate(this.date);
			
			//내부 반복문에서 참조하기 위한 변수
			var ref=this;
			
			tr=$('<tr/>',{'data-line':line,'class':'w3-hover-yellow'}).appendTo(table);
			//체크박스 추가
			td=$('<td/>').appendTo(tr);
			input=$('<input/>',{'class':'w3-check','data-line':line,'type':'checkbox','name':'no','value':this.no}).appendTo(td);
			
			//날짜 선택 추가
			td=$('<td/>').appendTo(tr);
			input=$('<input/>',{'class':'w3-input','data-line':line,'type':'text','name':'date','data-calendar':'calendar','value':date,'readonly':'readonly'}).appendTo(td);
					
			//카테고리 작업
			td=$('<td/>').appendTo(tr);
			var select=$('<select/>',{'class':'w3-select','data-line':line,'name':'category'}).appendTo(td);
			$(categoryList).each(function(){
				if(ref.category==this.no)
					$('<option/>',{'value':this.no,'selected':'selected','label':this.name}).appendTo(select);
				else
					$('<option/>',{'value':this.no,'label':this.name}).appendTo(select);
			});
			
			//지출 항목 작업
			td=$('<td/>').appendTo(tr);
			if(this.category==SPEND)
				select=$('<select/>',{'class':'w3-select','data-line':line,'name':'spend'}).appendTo(td);
			else
				select=$('<select/>',{'class':'w3-select','data-line':line,'name':'spend','disabled':'disabled'}).appendTo(td);
			
			$(spendList).each(function(){
				//실제 DB에 저장된 값을 바탕으로 option에 selected를 해주기 위한 코드
				if(ref.spend==this.no){
					$('<option/>',{'selected':'selected','value':this.no,'label':this.name}).appendTo(select);
				}else
					$('<option/>',{'value':this.no,'label':this.name}).appendTo(select);
			});
			
			//수입 항목 작업
			td=$('<td/>').appendTo(tr);
			if(this.category==INCOME)
				select=$('<select/>',{'class':'w3-select','data-line':line,'name':'income'}).appendTo(td);
			else
				select=$('<select/>',{'class':'w3-select','data-line':line,'name':'income','disabled':'disabled'}).appendTo(td);
			
			$(incomeList).each(function(){
				//실제 DB에 저장된 값을 바탕으로 option에 selected를 해주기 위한 코드
				if(ref.income==this.no)
					$('<option/>',{'selected':'selected','value':this.no,'label':this.name}).appendTo(select);
				else
					$('<option/>',{'value':this.no,'label':this.name}).appendTo(select);
			});
			
			//내역 항목 작업
			td=$('<td/>').appendTo(tr);
			input=$('<input/>',{'class':'w3-input','data-line':line,'type':'text','name':'content','maxlength':'20','value':this.content}).appendTo(td);
			
			//금액 작업
			td=$('<td/>').appendTo(tr);
			input=$('<input/>',{'class':'w3-input','data-line':line,'type':'text','name':'money','maxlength':'10','value':this.money}).appendTo(td);
			
			lineNum++;
		});
		
		nextLine=linePrefix+lineNum;
		
		//$('#HOUSEHOLD_TABLE_CONTENT').append(table);
		
		$("input[data-calendar='calendar']").datepicker($.datepicker.regional[ "ko" ]);
		$("input[name='money']").number(true,0);
		
		//select태그가 disable인 항목은 지출목록이 보이지 않도록 수정
		$("select[disabled='disabled']").each(function(){
			$(this).css({"visibility":"hidden"});
		}); 
		
		//지출 수입 항목 색표시
		$("select[name='category'] > option").addClass("w3-white");
		
		$("select[name='category']").each(function(){
			var line=$(this).attr("data-line");
			if($(this).val()==INCOME){
				$(this).addClass("w3-blue");
				$("input[name='money'][data-line="+line+"]").css("color","green");
			}else{
				$(this).addClass("w3-red");
				$("input[name='money'][data-line="+line+"]").css("color","red");
			}
		});
		
	}//renderView 함수 끝
	
	//yyyy-MM-dd 형태로 날짜를 만들어줌
	function makeISOdate(d){
		var date=new Date(d);
		var year=date.getFullYear();
		var month=Number(date.getMonth())+1;
		var day=date.getDate();
		return year+"-"+month+"-"+day;
	}
	
	//라인을 5줄 추가해주는 메소드
	function addNewLines(){
		
		var year=$("select[name='year']").val();
 		var month=$("select[name='month']").val();
 		
		var lineNum=nextLine.substring(nextLine.lastIndexOf("_")+1,nextLine.length);
		var linePrefix='LINE_';
		for(var i=0;i<5;i++){
			var line=linePrefix+lineNum;
			var tr=$("<tr/>",{'data-line':line,'class':'w3-hover-yellow'});
			var td=$("<td/>").appendTo(tr);
			var input=$("<input/>",{'class':'w3-check','data-line':line,'type':'checkbox','name':'no'}).appendTo(td);
			
			td=$("<td/>").appendTo(tr);
			input=$('<input/>',{'class':'w3-input','data-line':line,'type':'text','name':'date','data-calendar':'calendar','readonly':'readonly'}).appendTo(td);
			
			td=$("<td/>").appendTo(tr);
			//<select class='w3-select' data-line="+line+" name='category'>
			var select=$("<select/>",{'class':'w3-select','data-line':line,'name':'category'}).appendTo(td);
			$(categoryList).each(function(){
				var option=$("<option/>",{'value':this.no,'label':this.name}).appendTo(select);				
			}); 
			
			//지출 항목 추가
			td=$("<td/>").appendTo(tr);
			
			//console.log(JSON.stringify(spendList));
			select=$("<select/>",{'class':'w3-select','data-line':line,'name':'spend'}).appendTo(td);
			$(spendList).each(function(){
				var option;
				if(this.no==SPEND_ETC)
					option=$("<option/>",{'value':this.no,'label':this.name,'selected':'selected'}).appendTo(select);
				else
					option=$("<option/>",{'value':this.no,'label':this.name}).appendTo(select);
			});
			
			//수입 항목 추가
			td=$("<td/>").appendTo(tr);
			
			//수입 select는 처음에는 disabled되어 있고 hidden상태로 되어 있음
			select=$("<select/>",{'class':'w3-select','data-line':line,'name':'income','disabled':'disabled'}).appendTo(td);
			select.css({"visibility":"hidden"});
			$(incomeList).each(function(){
				var option;
				if(this.no==INCOME_ETC)
					option=$("<option/>",{'value':this.no,'label':this.name,'selected':'selected'}).appendTo(select);
				else
					option=$("<option/>",{'value':this.no,'label':this.name}).appendTo(select);
			});
			
			td=$("<td/>").appendTo(tr);
			input=$("<input/>",{'class':'w3-input','data-line':line,'type':'text','name':'content','maxlength':'20'}).appendTo(td);
			
			td=$("<td/>").appendTo(tr);
			input=$("<input/>",{'class':'w3-input','data-line':line,'type':'text','name':'money','maxlength':'10','value':'0'}).appendTo(td);
			$("table[data-table='HOUSEHOLD_TABLE_CONTENT']").append(tr);
			
			input.css("color","red");
			
			//datepicker세부 옵션을 사용할때는 세부 옵션을 먼저 지정하고 datepicker-ko를 세팅할 것
			$("input[data-calendar='calendar'][data-line="+line+"]").datepicker({defaultDate:new Date(year,month-1)}).datepicker($.datepicker.regional[ "ko" ]);
			lineNum++;
			
		}
		nextLine=linePrefix+lineNum;	
		$("input[name='money']").number(true,0);
		
		$("select[name='category'] > option").addClass("w3-white");
		
		$("select[name='category']").each(function(){
			if($(this).val()==INCOME){
				$(this).addClass("w3-blue");
			}else{
				$(this).addClass("w3-red");
			}
		});
	}//라인 5줄 추가해주는 메소드 끝
	
	//금액 입력창에서 focus out 했을때 널값이나 공백값 검사
	$(document).on('focusout',"input[name='money']",function(){
		if(!$(this).val())
			$(this).val(0);
		
		//지출합계와 수입합계를 변수에 받아서 저장
		var x=sumSpend();
		var y=sumIncome();
		
		//결과 테이블의 지출합계 부분에 지출합계 저장
		$("#spend_result").val(x);
		
		//결과 테이블의 수입합계 부분에 수입합계 저장
		$("#income_result").val(y);
		
		//총합을 구한다음 결과 테이블의 총합계 부분에 총합결과값을 저장
		var total=calTotal(x,y);
		$("#total_result").val(total); 
		
		
	});//금액 입력창에서 focus out 했을때 널값이나 공백값 검사 함수 끝
	
	//지출 합계를 구하는 함수
	function sumSpend(){
		var lastLine=nextLine.substring(nextLine.lastIndexOf("_")+1,nextLine.length)-1;
		var startLine=1;
		var linePrefix='LINE_';
		var total=0;
		for(var i=startLine;i<=lastLine;i++){
			var line=linePrefix+i;
			var x=Number($("select[name='category'][data-line="+line+"]").val());
			if(x==SPEND){
				total+=Number($("input[name='money'][data-line="+line+"]").val());
			}	
			
		}
		return total;
	}
	
	//수입 합계를 구하는 함수
	function sumIncome(){
		var lastLine=nextLine.substring(nextLine.lastIndexOf("_")+1,nextLine.length)-1;
		var startLine=1;
		var linePrefix='LINE_';
		var total=0;
		for(var i=startLine;i<=lastLine;i++){
			var line=linePrefix+i;
			var x=Number($("select[name='category'][data-line="+line+"]").val());
			if(x==INCOME){
				total+=Number($("input[name='money'][data-line="+line+"]").val());
			}	
			
		}
		return total;
	}
	
	//총합을 구해주는 함수
	function calTotal(spend,income)
	{
		var total=Number(income)-Number(spend);
		return total;
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

	//지출값 세팅을 위해 필요한 함수
	function spendSetter(categoryList){
		$(categoryList).each(function(){
			if(this.name=='지출'){
				SPEND=this.no;
			}
		});
	}
	
	//수입값 세팅을 위해 필요한 함수
	function incomeSetter(categoryList){
		$(categoryList).each(function(){
			if(this.name=='수입'){
				INCOME=this.no;
			}
		});
	}
	
	//지출의 기타값 세팅을 위해 필요한함수
	function spendETCSetter(spendList){
		$(spendList).each(function(){
			if(this.name=='기타'){
				SPEND_ETC=this.no;
			}
		});
	}
	
	//수입의 기타값 세팅
	function incomeETCSetter(incomeList){
		$(incomeList).each(function(){
			if(this.name=='기타'){
				INCOME_ETC=this.no;
			}
		});
	}
});