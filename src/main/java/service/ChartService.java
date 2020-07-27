package service;

import java.util.List;

import vo.ChartJSON;
import vo.User;
import vo.YearAndMonth;

public interface ChartService {
	public ChartJSON getSpendData(YearAndMonth yearAndMonth,User user);
	public ChartJSON getIncomeData(YearAndMonth yearAndMonth,User user);
	public ChartJSON getPieChartData(YearAndMonth yearAndMonth,User user);
	public List<ChartJSON> getChartData(YearAndMonth yearAndMonth,User user);
}
