package repository;

import java.util.List;

import vo.Income;
import vo.Spend;

public interface IncomeRespository {
	List<Income> selectList();
	public void update(Income income);
	public void delete(Integer no);
	public void insert(Income income);
}
