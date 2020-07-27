package repository;

import java.util.List;

import vo.Category;

public interface CategoryRepository {
	public List<Category> selectList();
}
