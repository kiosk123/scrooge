package repository.internal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import repository.ChartRepository;
import vo.Chart;

@Repository
public class ChartRepostioryImpl implements ChartRepository{

	private final static String namespace="mapper.ChartMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public List<Chart> selectSpendData(Map<String, Object> param) {
		return sqlSession.selectList(namespace+"selectSpendData",param);
	}

	@Override
	public List<Chart> selectIncomeData(Map<String, Object> param) {
		return sqlSession.selectList(namespace+"selectIncomeData",param);
	}	
}
