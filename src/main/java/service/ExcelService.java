package service;

import vo.ExcelSheetConfiguration;
import vo.ExcelViewJSON;
import vo.User;
import vo.YearAndMonth;

public interface ExcelService {
	public ExcelViewJSON createExcelView(YearAndMonth yearAndMonth,User user);
	public ExcelSheetConfiguration createExcelSheetData(YearAndMonth yearAndMonth,User user);
}
