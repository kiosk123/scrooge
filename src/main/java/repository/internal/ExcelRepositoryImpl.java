package repository.internal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import repository.ExcelRepository;
import vo.ExcelData;

@Repository
public class ExcelRepositoryImpl implements ExcelRepository{

	private final static String namespace="mapper.ExcelMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Transactional
	@Override
	public List<String> selectYearAndMonthList(Map<String, Object> param) {
		return sqlSession.selectList(namespace+"selectYearAndMonthList",param);
	}

	@Transactional
	@Override
	public List<ExcelData> selectIncomeDataList(Map<String, Object> param) {
		return sqlSession.selectList(namespace+"selectIncomeDataList",param);
	}

	@Transactional
	@Override
	public List<ExcelData> selectSpendDataList(Map<String, Object> param) {
		return sqlSession.selectList(namespace+"selectSpendDataList",param);
	}
	
	
}
