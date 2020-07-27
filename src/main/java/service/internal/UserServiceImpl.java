package service.internal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import repository.UserRepository;
import service.PasswordService;
import service.UserService;
import vo.User;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordService passwordService;
	
	//User 객체를 가져온다.
	@Override
	public User getUserObject(User user) {
		return userRepository.selectOne(user.getId());
	}

	@Override
	public void changePassword(User user) {
		String newPassword=passwordService.encode(user.getPassword());
		user.setPassword(newPassword);
		userRepository.update(user);
	}

	@Override
	public void withdrawMember(User user) {
		userRepository.delete(user);		
	}
}
