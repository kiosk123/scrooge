package repository;

import vo.User;

public interface UserRepository {
	User selectOne(String id);
	String selectEmailOne(String email);
	void insert(User user);
	void update(User user);
	void delete(User user);
}
