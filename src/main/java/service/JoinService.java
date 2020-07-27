package service;

import javax.servlet.http.HttpSession;

import vo.Authenticode;
import vo.Email;
import vo.User;

public interface JoinService {
	public boolean alreadyExistsUserId(User user);
	public boolean alreadyExistsEmail(String email);
	public void sendEmail(Email email,HttpSession session);
	public void insertUser(User user);
}
