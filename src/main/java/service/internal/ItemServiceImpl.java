package service.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repository.IncomeRespository;
import repository.SpendRepository;
import service.ItemService;
import vo.Income;
import vo.Spend;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private SpendRepository spendRepository;
	
	@Autowired
	private IncomeRespository incomeRespository;
	
	@Override
	public List<Spend> getSpendList() {
		return spendRepository.selectList();
	}

	@Override
	public List<Income> getIncomeList() {
		return incomeRespository.selectList();
	}
	
}
