package service.internal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import controller.ExcelController;
import repository.ExcelRepository;
import service.ExcelService;
import vo.ExcelData;
import vo.ExcelSheetConfiguration;
import vo.ExcelViewJSON;
import vo.User;
import vo.YearAndMonth;

@Service
public class ExcelServiceImpl implements ExcelService {

	@Autowired
	private ExcelRepository excelRepository;

	@Transactional
	@Override
	public ExcelViewJSON createExcelView(YearAndMonth yearAndMonth, User user) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("user", user.getNo());
		param.put("month", yearAndMonth.getMonth());
		param.put("year", yearAndMonth.getYear());
		List<String> list = excelRepository.selectYearAndMonthList(param);

		List<String> contentList = new ArrayList<String>();
		List<String> linkList = new ArrayList<String>();

		for (int i = 0; i < list.size(); i++) {

			Link link = ControllerLinkBuilder.linkTo(ExcelController.class).slash(list.get(i)).slash("/excel.xls")
					.withSelfRel();
			linkList.add(link.getHref());
			contentList.add(makeContent(list.get(i)));
		}

		ExcelViewJSON excelViewJSON = new ExcelViewJSON();
		excelViewJSON.setContentList(contentList);
		excelViewJSON.setLinkList(linkList);

		return excelViewJSON;
	}

	//지출 데이터, 수입데이터, 수입합계, 지출합계, 총계의 데이터를 가지고 있는 클래스의 인스턴스를 만들어준다
	@Transactional
	@Override
	public ExcelSheetConfiguration createExcelSheetData(YearAndMonth yearAndMonth, User user) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("year", yearAndMonth.getYear());
		param.put("month", yearAndMonth.getMonth());
		param.put("user", user.getNo());

		ExcelSheetConfiguration excelSheetConfiguration = new ExcelSheetConfiguration();
		List<ExcelData> list = excelRepository.selectIncomeDataList(param);
		excelSheetConfiguration.setIncomeTotal(sumMoney(list));
		excelSheetConfiguration.setIncomeDataList(list);
		
		list=excelRepository.selectSpendDataList(param);
		excelSheetConfiguration.setSpendTotal(sumMoney(list));
		excelSheetConfiguration.setSpendDataList(list);
		
		excelSheetConfiguration.setTotal(excelSheetConfiguration.getIncomeTotal()-excelSheetConfiguration.getSpendTotal());
		return excelSheetConfiguration;
	}

	private Integer sumMoney(List<ExcelData> list) {
		Integer total = 0;
		for (int i = 0; i < list.size(); i++)
			total += list.get(i).getMoney();
		return total;
	}

	private String makeContent(String date) {

		List<String> dateToken = new ArrayList<String>();
		StringTokenizer token = new StringTokenizer(date, "-", false);

		while (token.hasMoreTokens())
			dateToken.add(token.nextToken());

		StringBuffer sb = new StringBuffer();
		sb.append(dateToken.get(0));
		sb.append("년 ");
		sb.append(dateToken.get(1));
		sb.append("월 가계부 데이터");
		return sb.toString();
	}
}
