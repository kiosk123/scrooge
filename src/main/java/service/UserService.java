package service;

import vo.User;

public interface UserService {
	public User getUserObject(User user);
	public void changePassword(User user);
	public void withdrawMember(User user);
}
