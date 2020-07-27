package controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.ItemService;
import vo.Income;
import vo.Spend;



@Controller
@RequestMapping("/admin")
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	
	@RequestMapping(value="/item",method=RequestMethod.GET)
	public String itemEditPage(){
		return "admin/item";
	}
	
	@RequestMapping(value="/spend",method=RequestMethod.GET)
	public @ResponseBody List<Spend> spendList(){
		return itemService.getSpendList();
	}
	
	@RequestMapping(value="/income",method=RequestMethod.GET)
	public @ResponseBody List<Income> incomeList(){
		return itemService.getIncomeList();
	}
}
