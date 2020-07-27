package service;

public interface PasswordService {
	String encode(String origin);
	boolean matches(String origin,String hashed);
}
