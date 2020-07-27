package repository.internal;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import repository.HouseholdRepository;
import vo.HouseHold;

@Repository
public class HouseHoldRepositoryImpl implements HouseholdRepository{

	
	private final static String namespace="mapper.HouseHoldMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	
	@Transactional
	@Override
	public void insert(HouseHold houseHold) {
		sqlSession.insert(namespace+"insert",houseHold);				
	}

	@Transactional
	@Override
	public List<HouseHold> selectList(Map<String, Object> param) {	
		return sqlSession.selectList(namespace+"select", param);
	}

	@Transactional
	@Override
	public void update(HouseHold houseHold) {
		sqlSession.update(namespace+"update",houseHold);		
	}

	@Transactional
	@Override
	public void delete(Integer no) {
		sqlSession.delete(namespace+"delete",no);
	}
	
}
