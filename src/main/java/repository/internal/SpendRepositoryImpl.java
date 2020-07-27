package repository.internal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import repository.SpendRepository;
import vo.Spend;

//T_SPEND 테이블과 CRUD연산을 할 리포지토리
@Repository
public class SpendRepositoryImpl implements SpendRepository {
	
	private final static String namespace="mapper.SpendMapper.";
	
	@Autowired
	private SqlSession sqlSession;		
	
	@Transactional
	@Override
	public void update(Spend spend) {
		sqlSession.update(namespace+"update",spend);
	}

	@Transactional
	@Override
	public void delete(Integer no) {
		sqlSession.delete(namespace+"delete", no);
	}

	@Transactional
	@Override
	public void insert(Spend spend) {
		sqlSession.insert(namespace+"insert",spend);
	}

	@Transactional
	@Override
	public List<Spend> selectList() {
		return sqlSession.selectList(namespace+"selectList");
	}
}
