package service;

import java.util.List;

import vo.Income;
import vo.Spend;

public interface ItemService {
	List<Spend> getSpendList();
	List<Income> getIncomeList();	
}
