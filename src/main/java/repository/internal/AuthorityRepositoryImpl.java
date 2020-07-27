package repository.internal;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import repository.AuthorityRepository;

@Repository
public class AuthorityRepositoryImpl implements AuthorityRepository{
	
	private final static String namespace="mapper.AuthorityMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Override
	public String selectAuthorityNo(String role) {
		return sqlSession.selectOne(namespace+"selectAuthorityNo",role);
	}
}
