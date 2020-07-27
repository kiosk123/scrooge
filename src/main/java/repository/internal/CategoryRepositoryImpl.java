package repository.internal;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import repository.CategoryRepository;
import vo.Category;

@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

	private final static String namespace="mapper.CategoryMapper.";
	
	@Autowired
	private SqlSession sqlSession;
	
	@Transactional
	@Override
	public List<Category> selectList() {		
		return sqlSession.selectList(namespace+"select");
	}

}
