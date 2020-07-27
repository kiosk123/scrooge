package view;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.CellUtil;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import vo.ExcelData;
import vo.ExcelSheetConfiguration;
import vo.YearAndMonth;

public class ExcelView extends AbstractXlsView {

	//엑셀 파일로 만들기 위한 뷰 처리부
	@Override
	protected void buildExcelDocument(Map<String, Object> model, Workbook work, HttpServletRequest req,
			HttpServletResponse res) throws Exception {

		ExcelSheetConfiguration excelSheetConfiguration = (ExcelSheetConfiguration) model.get("excelSheetConfiguration");
		YearAndMonth yearAndMonth = (YearAndMonth) model.get("yearAndMonth");
		
		String downName=createDownName(createFileName(yearAndMonth), req) ;		
		res.setHeader("Content-Disposition", "attachment;filename=\"" + downName + "\";");
		
		//지출 데이터처리
		List<ExcelData> list=excelSheetConfiguration.getSpendDataList();
		if(list.size()>0){
			createSpendDataSheet(work, yearAndMonth,excelSheetConfiguration);
		}
		
		//수입 데이터처리
		list=excelSheetConfiguration.getIncomeDataList();
		if(list.size()>0){
			createIncomeDataSheet(work, yearAndMonth,excelSheetConfiguration);
		}
	}
	
	//지출 목록을 뿌려주는 함수
	private void createSpendDataSheet(Workbook work,YearAndMonth yearAndMonth,ExcelSheetConfiguration excelSheetConfiguration){
		Sheet sheet=work.createSheet("지출 목록");
		sheet.setColumnWidth(1,5000);
		sheet.setColumnWidth(2,8750);
		sheet.setColumnWidth(3,4000);
		sheet.setColumnWidth(4,7500);
		sheet.setZoom(130);
		createSheetHeader(sheet,work,createSpendSheetHeaderName(yearAndMonth));
		createTableHeader(sheet,work);
		int endRow=setTableData(sheet,work,excelSheetConfiguration.getSpendDataList());
		setTablefilter("B4",("E"+endRow),sheet);
		setTotalData(endRow,"지출 합계", excelSheetConfiguration.getSpendTotal(), sheet, work,true);
	}
	//수입 목록을 뿌려주는 함수
	private void createIncomeDataSheet(Workbook work,YearAndMonth yearAndMonth,ExcelSheetConfiguration excelSheetConfiguration){
		Sheet sheet=work.createSheet("수입 목록");
		sheet.setColumnWidth(1,5000);
		sheet.setColumnWidth(2,8750);
		sheet.setColumnWidth(3,4000);
		sheet.setColumnWidth(4,7500);
		sheet.setZoom(130);
		createSheetHeader(sheet,work,createIncomeSheetHeaderName(yearAndMonth));
		createTableHeader(sheet,work);
		int endRow=setTableData(sheet,work,excelSheetConfiguration.getIncomeDataList());
		setTablefilter("B4",("E"+endRow),sheet);
		setTotalData(endRow,"수입 합계", excelSheetConfiguration.getIncomeTotal(), sheet, work,false);
	}
	
	//지출이나 수입의 총 합계를 보여주기 위해 사용하는 함수
	private void setTotalData(Integer endRow,String title,Integer total,Sheet sheet,Workbook work,boolean isSpendTotal){
		   
         Row row=sheet.createRow(endRow+1);
         Cell cell=row.createCell(4);
         cell.setCellValue(title);
         
         //폰트설정
         Font font=work.createFont();
         font.setFontHeightInPoints((short)14);
         font.setFontName("맑은 고딕");
         font.setColor(IndexedColors.WHITE.getIndex());
         font.setBold(true);
         

         CellStyle style=work.createCellStyle();
         style.setFont(font);
         
         if(isSpendTotal)
        	 style.setFillForegroundColor(IndexedColors.RED.getIndex()); // Foreground not Background
         else
        	 style.setFillForegroundColor(IndexedColors.GREEN.getIndex()); // Foreground not Background
         
         style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         style.setBorderLeft(BorderStyle.THIN);
         style.setBorderBottom(BorderStyle.THIN);
         style.setBorderRight(BorderStyle.THIN);
         style.setBorderTop(BorderStyle.THIN);
         cell.setCellStyle(style);
         CellUtil.setAlignment(cell, HorizontalAlignment.CENTER); //셀 가운데 정렬  
         
         //금액 적용
         font=work.createFont();
         font.setFontHeightInPoints((short)14);
         font.setFontName("맑은 고딕");
         style=work.createCellStyle();
         style.setFont(font);
         
         style.setBorderLeft(BorderStyle.THIN);
         style.setBorderBottom(BorderStyle.THIN);
         style.setBorderRight(BorderStyle.THIN);
         style.setBorderTop(BorderStyle.THIN);
         
         DataFormat format=work.createDataFormat();
	     style.setDataFormat(format.getFormat("#,##0 원"));
	    
	     row=sheet.createRow(endRow+2);
	     cell=row.createCell(4);
         cell.setCellValue(total);
         cell.setCellStyle(style);
         CellUtil.setAlignment(cell, HorizontalAlignment.RIGHT); //셀 오른쪽 정렬
         
	}
	
	
	//엑셀 테이블에 필터를 만들어 준다
	private void setTablefilter(String startCell, String endCell,Sheet sheet){
		  CellReference start = new CellReference(startCell);
		  CellReference end = new CellReference(endCell);
		  CellRangeAddress address = new CellRangeAddress(start.getRow(),end.getRow(), start.getCol(), end.getCol());
		  sheet.setAutoFilter(address);		  
	}
	
	//파일 이름을 만들어 주는 함수
	private String createFileName(YearAndMonth yearAndMonth){
		StringBuffer sb=new StringBuffer();
		sb.append(yearAndMonth.getYear());
		sb.append("년 ");
		sb.append(yearAndMonth.getMonth());
		sb.append("월 가계부.xls");
		return sb.toString();
	}
	
	//각 시트 헤더에 타이틀을 표시해주기 위한 엑셀 셀을 세팅해줌
	private void createSheetHeader(Sheet sheet, Workbook work,String headerName){
		 CellReference start = new CellReference("B2");
		 CellReference end = new CellReference("E2");
         CellRangeAddress address = new CellRangeAddress(start.getRow(),end.getRow(), start.getCol(),end.getCol());
         sheet.addMergedRegion(address);
         Row row=sheet.createRow(1);
         Cell cell1=row.createCell(1);
         Cell cell2=row.createCell(2);
         Cell cell3=row.createCell(3);
         Cell cell4=row.createCell(4);
         cell1.setCellValue(headerName);
         
         //폰트설정
         Font font=work.createFont();
         font.setFontHeightInPoints((short)14);
         font.setFontName("맑은 고딕");
         
         CellStyle style=work.createCellStyle();
         style.setFont(font);
         style.setFillForegroundColor(IndexedColors.YELLOW.getIndex()); // Foreground not Background
         style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
         style.setBorderLeft(BorderStyle.THIN);
         style.setBorderBottom(BorderStyle.THIN);
         style.setBorderRight(BorderStyle.THIN);
         style.setBorderTop(BorderStyle.THIN);
         cell1.setCellStyle(style);
         cell2.setCellStyle(style);
         cell3.setCellStyle(style);
         cell4.setCellStyle(style);
         CellUtil.setAlignment(cell1, HorizontalAlignment.CENTER); //셀 가운데 정렬
         
	}
	
	//데이터를 표시할 테이블의 헤더를 세팅한다.
	private void createTableHeader(Sheet sheet, Workbook work){
		Row row=sheet.createRow(3);
		
		Cell cell1=row.createCell(1);
		Cell cell2=row.createCell(2);
		Cell cell3=row.createCell(3);
		Cell cell4=row.createCell(4);
		
		cell1.setCellValue("날짜");
		cell2.setCellValue("내역");
		cell3.setCellValue("분류");
		cell4.setCellValue("금액");
		
		//폰트설정
        Font font=work.createFont();
        font.setFontHeightInPoints((short)12);
        font.setFontName("맑은 고딕");
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBold(true);
        
        CellStyle style=work.createCellStyle();
        style.setFont(font);
        style.setFillForegroundColor(IndexedColors.TEAL.getIndex()); // Foreground not Background
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderBottom(BorderStyle.THICK);
        style.setBorderRight(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        cell1.setCellStyle(style);
        cell2.setCellStyle(style);
        cell3.setCellStyle(style);
        cell4.setCellStyle(style);
        
        CellUtil.setAlignment(cell1, HorizontalAlignment.CENTER); //셀 가운데 정렬
        CellUtil.setAlignment(cell2, HorizontalAlignment.CENTER); //셀 가운데 정렬
        CellUtil.setAlignment(cell3, HorizontalAlignment.CENTER); //셀 가운데 정렬
        CellUtil.setAlignment(cell4, HorizontalAlignment.CENTER); //셀 가운데 정렬 
		
	}
	//데이터를 표시할 테이블에 데이터를 세팅한다.
	private int setTableData(Sheet sheet, Workbook work, List<ExcelData> list){
		int startRow=4;
		for(int i=0;i<list.size();i++){
			
			ExcelData data=list.get(i);
			
			Row row=sheet.createRow(startRow);
			Cell cell1=row.createCell(1);
			Cell cell2=row.createCell(2);
			Cell cell3=row.createCell(3);
			Cell cell4=row.createCell(4);
			
			
			cell1.setCellValue(data.getDate());
			cell2.setCellValue(data.getContent());
			cell3.setCellValue(data.getName());
			cell4.setCellValue(data.getMoney());
			
			CellStyle style=work.createCellStyle();
			
			//폰트설정
	        Font font=work.createFont();
	        font.setFontHeightInPoints((short)12);
	        font.setFontName("맑은 고딕");
	        font.setColor(IndexedColors.BLACK.getIndex());
	        
	        style.setFont(font);
	        style.setBorderLeft(BorderStyle.THIN);
	        style.setBorderBottom(BorderStyle.THIN);
	        style.setBorderRight(BorderStyle.THIN);
	        
	        cell1.setCellStyle(style);
	        cell2.setCellStyle(style);
	        cell3.setCellStyle(style);
	        
	        //금액 적용
	        DataFormat format=work.createDataFormat();
	        style.setDataFormat(format.getFormat("#,##0 원"));
	        cell4.setCellStyle(style);
	        
	        CellUtil.setAlignment(cell1, HorizontalAlignment.CENTER); //셀 가운데 정렬
	        CellUtil.setAlignment(cell2, HorizontalAlignment.CENTER); //셀 가운데 정렬
	        CellUtil.setAlignment(cell3, HorizontalAlignment.CENTER); //셀 가운데 정렬
	        CellUtil.setAlignment(cell4, HorizontalAlignment.RIGHT); //셀 가운데 정렬 
	        
	        startRow++;

		}
		return startRow;
	}
	
	//다운 로드시 파일이름을 적용해주는 함수
	private String createDownName(String fileName,HttpServletRequest req)throws Exception{
		String downName = "";
		if (req.getHeader("user-agent").indexOf("MSIE") == -1) {
			downName = new String(fileName.getBytes("UTF-8"), "8859_1");
		} else {
			downName = new String(fileName.getBytes("EUC-KR"), "8859_1");
		}
		return downName;
	}
	
	//엑셀지출시트에서 타이틀을 만들어주는 함수
	private String createSpendSheetHeaderName(YearAndMonth yearAndMonth){
		StringBuffer sb=new StringBuffer();
		sb.append(yearAndMonth.getYear());
		sb.append("년 ");
		sb.append(yearAndMonth.getMonth());
		sb.append("월 지출 목록");
		return sb.toString();
	}
	
	//엑셀수입시트에서 타이틀을 만들어주는 함수
	private String createIncomeSheetHeaderName(YearAndMonth yearAndMonth){
		StringBuffer sb=new StringBuffer();
		sb.append(yearAndMonth.getYear());
		sb.append("년 ");
		sb.append(yearAndMonth.getMonth());
		sb.append("월 수입 목록");
		return sb.toString();
	}

}
