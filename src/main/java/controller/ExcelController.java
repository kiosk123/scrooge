package controller;

import static util.Utils.getPrincipal;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ExcelService;
import service.UserService;
import vo.ExcelViewJSON;
import vo.User;
import vo.YearAndMonth;

@Controller
@RequestMapping("/excel")
public class ExcelController {

	@Autowired
	private ExcelService excelService;
	
	@Autowired
	private UserService userService;
	
	final static Logger log=LoggerFactory.getLogger(ExcelController.class);
	
	@RequestMapping(method = RequestMethod.GET)
	public String excelPage(Model model) {

		// 파라미터로 년값을 만들어서 세팅
		LocalDate date = LocalDate.now();
		Integer year = date.getYear();
		model.addAttribute("year", year);
		return "household/excel";
	}
	
	@RequestMapping(value="/search",method=RequestMethod.POST)
	public @ResponseBody ExcelViewJSON searchList(@RequestBody YearAndMonth yearAndMonth){
		User user=getUser(getPrincipal());
		return excelService.createExcelView(yearAndMonth, user);
	}
	
	@RequestMapping(value="/{date}/excel",method=RequestMethod.GET)
	public String excelProcess(@PathVariable("date") 
						@DateTimeFormat(pattern="yyyy-MM") Date date,Model model){
		User user=getUser(getPrincipal());
		LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		YearAndMonth yearAndMonth=new YearAndMonth();
		yearAndMonth.setYear(localDate.getYear());
		yearAndMonth.setMonth(localDate.getMonthValue());		
		model.addAttribute("excelSheetConfiguration",excelService.createExcelSheetData(yearAndMonth, user));
		model.addAttribute("yearAndMonth",yearAndMonth);		
		return "excel";
	}
	
	private User getUser(String userId){
		User user=new User();
		user.setId(userId);
		user=userService.getUserObject(user);
		return user;
	}
}
