package repository;

import java.util.List;
import java.util.Map;

import vo.ExcelData;

public interface ExcelRepository {
	public List<String> selectYearAndMonthList(Map<String,Object> param);
	public List<ExcelData> selectIncomeDataList(Map<String, Object> param);
	public List<ExcelData> selectSpendDataList(Map<String, Object> param);
}
