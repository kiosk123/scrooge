package controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static util.Utils.*;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.HouseHoldService;
import service.UserService;
import vo.Category;
import vo.HouseHold;
import vo.HouseHoldViewJSON;
import vo.HouseHoldCRUD;
import vo.Spend;
import vo.User;
import vo.YearAndMonth;
import static util.Utils.getPrincipal;

@Controller
@RequestMapping("/household")
public class HouseHoldController {
	
	@Autowired
	private HouseHoldService houseHoldService;
	
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String householdPage(Model model) {
		// 파라미터로 년과 월 값이 없는 경우는 다음과 같이 요청한 날짜에 해당하는 월
		LocalDate date = LocalDate.now();
		Integer year = date.getYear();
		Integer month = date.getMonthValue();
		model.addAttribute("year",year);
		model.addAttribute("month",month);
		return "household/household";
	}
	
	//검색 버튼 클릭시 데이터를 전송해줄 컨트롤러
	@RequestMapping(value="/search", method=RequestMethod.POST)
	public @ResponseBody HouseHoldViewJSON householdList(
			@RequestBody YearAndMonth yearAndMonth){
				User user=new User();
				user.setId(getPrincipal());
				user=userService.getUserObject(user);
		//JSON으로 household.jsp에서 사용할 JSON을 만들어서 뷰에 보냄
		return houseHoldService.createHouseholdView(yearAndMonth, user);
	}
	
	//등록 및 수정 버튼을 눌렀을 때 새로운 데이터는 등록하고 기존데이터는 업데이트하는 메소드
	@RequestMapping(value="/list",method=RequestMethod.POST)
	public @ResponseBody HouseHoldViewJSON houseHoldInsertAndUpdate(@RequestBody HouseHoldCRUD insertAndUpdateJSON){
		User user=getUser(getPrincipal());
		return houseHoldService.insertAndUpdateHouseHold(insertAndUpdateJSON.getHouseHoldList(),insertAndUpdateJSON.getYearAndMonth(), user);		
	}
	
	@RequestMapping(value="/list",method=RequestMethod.DELETE)
	public @ResponseBody HouseHoldViewJSON deleteHouseHold(@RequestBody HouseHoldCRUD deleteJSON){
		User user=getUser(getPrincipal());
		return houseHoldService.deleteHouseHold(deleteJSON.getDeleteList(), deleteJSON.getYearAndMonth(), user);
	}
	
	private User getUser(String userId){
		User user=new User();
		user.setId(userId);
		user=userService.getUserObject(user);
		return user;
	}

}
