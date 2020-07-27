package service.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.ChartRepository;
import service.ChartService;
import vo.Chart;
import vo.ChartJSON;
import vo.User;
import vo.YearAndMonth;

@Service
public class ChartServiceImpl implements ChartService {

	@Autowired
	private ChartRepository chartRepository;

	// 지출 관련 데이터 반환
	@Transactional
	@Override
	public ChartJSON getSpendData(YearAndMonth yearAndMonth, User user) {

		ChartJSON chartJSON = new ChartJSON();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("year", yearAndMonth.getYear());
		param.put("month", yearAndMonth.getMonth());
		param.put("user", user.getNo());

		List<Chart> list = chartRepository.selectSpendData(param);

		List<String> nameList = new ArrayList<String>();
		List<Integer> moneyList = new ArrayList<Integer>();

		for (int i = 0; i < list.size(); i++) {
			nameList.add(list.get(i).getName());
			moneyList.add(list.get(i).getMoney());
		}

		chartJSON.setMoneyList(moneyList);
		chartJSON.setNameList(nameList);

		return chartJSON;
	}

	// 수입 관련 데이터 반환
	@Transactional
	@Override
	public ChartJSON getIncomeData(YearAndMonth yearAndMonth, User user) {

		ChartJSON chartJSON = new ChartJSON();

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("year", yearAndMonth.getYear());
		param.put("month", yearAndMonth.getMonth());
		param.put("user", user.getNo());

		List<Chart> list = chartRepository.selectIncomeData(param);

		List<String> nameList = new ArrayList<String>();
		List<Integer> moneyList = new ArrayList<Integer>();

		for (int i = 0; i < list.size(); i++) {
			nameList.add(list.get(i).getName());
			moneyList.add(list.get(i).getMoney());
		}

		chartJSON.setMoneyList(moneyList);
		chartJSON.setNameList(nameList);

		return chartJSON;
	}

	// 파이차트를 그리기 위해 필요한 데이터를 만들어 준다.
	@Transactional
	@Override
	public ChartJSON getPieChartData(YearAndMonth yearAndMonth, User user) {
		
		ChartJSON incomeData = this.getIncomeData(yearAndMonth, user);
		ChartJSON spendData = this.getSpendData(yearAndMonth, user);
		ChartJSON pieChartData=new ChartJSON();
		
		Integer incomeTotal = sum(incomeData.getMoneyList());
		Integer spendTotal = sum(spendData.getMoneyList());
		
		List<String> nameList=new ArrayList<String>();
		List<Integer> moneyList=new ArrayList<Integer>();
		
		if((incomeTotal!=0)||(spendTotal!=0)){
			nameList.add("수입");
			nameList.add("지출");
			moneyList.add(incomeTotal);
			moneyList.add(spendTotal);
		}
		
		pieChartData.setMoneyList(moneyList);
		pieChartData.setNameList(nameList);
		
		return pieChartData;
	}
	

	//차트 페이지에서  차트를 렌더링하기 위한 JSON 데이터를 만들어 준다 
	@Transactional
	@Override
	public List<ChartJSON> getChartData(YearAndMonth yearAndMonth, User user) {
		ArrayList<ChartJSON> list = new ArrayList<ChartJSON>();
		list.add(this.getSpendData(yearAndMonth, user));
		list.add(this.getIncomeData(yearAndMonth, user));
		list.add(this.getPieChartData(yearAndMonth, user));
		return list;
	}

	private Integer sum(List<Integer> list) {
		Integer total = 0;
		for (int i = 0; i < list.size(); i++) {
			total += list.get(i);
		}
		return total;
	}

}
