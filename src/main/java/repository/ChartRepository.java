package repository;

import java.util.List;
import java.util.Map;

import vo.Chart;

public interface ChartRepository {
	public List<Chart> selectSpendData(Map<String, Object> param);
	public List<Chart> selectIncomeData(Map<String, Object> param);
}
