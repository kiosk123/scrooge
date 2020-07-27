package repository.internal;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import repository.UserRepository;
import vo.User;

//T_USER 테이블과 CRUD 연산을 할 리포지토리
@Repository
public class UserRepositoryImpl implements UserRepository{

	private final static String namespace="mapper.UserMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	//id 값을 이용해서 User 객체를 db에서 하나 꺼내온다.
	@Transactional
	@Override
	public User selectOne(String id) {
		return sqlSession.selectOne(namespace+"selectOne",id);
	}
	
	//이메일 중복검사시 사용되는 메소드
	@Transactional
	@Override
	public String selectEmailOne(String email){
		return sqlSession.selectOne(namespace+"selectEmailOne",email);
	}

	//회원 가입 처리
	@Transactional
	@Override
	public void insert(User user) {
		sqlSession.insert(namespace+"insert",user);		
	}
	
	
	//회원 업데이트 처리 메소드
	@Transactional
	@Override
	public void update(User user){
		sqlSession.update(namespace+"update",user);
	}

	@Transactional
	@Override
	public void delete(User user) {
		sqlSession.delete(namespace+"delete",user);		
	}
	
	
	
}
