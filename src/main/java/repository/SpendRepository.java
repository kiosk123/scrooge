package repository;

import java.util.List;
import java.util.Map;

import vo.Spend;

public interface SpendRepository {
	public void update(Spend spend);
	public void delete(Integer no);
	public void insert(Spend spend);
	public List<Spend> selectList();
}
