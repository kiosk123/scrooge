package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import static util.Utils.*;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ChartService;
import service.UserService;
import vo.ChartJSON;
import vo.User;
import vo.YearAndMonth;

@Controller
@RequestMapping("/chart")
public class ChartController {

	@Autowired
	private ChartService chartService;
	
	@Autowired
	private UserService userService;

	// 먼저 메타데이터에 년도와 날짜를 세팅한다.
	@RequestMapping(method = RequestMethod.GET)
	public String chartPage(Model model) {

		// 파라미터로 년과 월 값이 없는 경우는 다음과 같이 요청한 날짜에 해당하는 월
		LocalDate date = LocalDate.now();
		Integer year = date.getYear();
		Integer month = date.getMonthValue();
		model.addAttribute("year", year);
		model.addAttribute("month", month);
		return "household/chart";
	}

	@RequestMapping(value = "/chartData", method = RequestMethod.POST)
	public @ResponseBody List<ChartJSON> chartData(@RequestBody YearAndMonth yearAndMonth) {
		User user = getUser(getPrincipal());		
		return chartService.getChartData(yearAndMonth, user);
	}
	
	
	private User getUser(String userId){
		User user=new User();
		user.setId(userId);
		user=userService.getUserObject(user);
		return user;
	}
}
