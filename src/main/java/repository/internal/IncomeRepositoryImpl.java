package repository.internal;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import repository.IncomeRespository;
import vo.Income;

@Repository
public class IncomeRepositoryImpl implements IncomeRespository {

	private final static String namespace="mapper.IncomeMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Transactional
	@Override
	public List<Income> selectList() {
		return sqlSession.selectList(namespace+"select");
	}

	@Override
	public void update(Income income) {
		sqlSession.update(namespace+"select",income);		
	}

	@Override
	public void delete(Integer no) {
		sqlSession.delete(namespace+"delete",no);		
	}

	@Override
	public void insert(Income income) {
		sqlSession.insert(namespace+"insert",income);		
	}

}
