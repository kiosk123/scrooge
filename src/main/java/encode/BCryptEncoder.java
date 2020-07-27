package encode;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import service.PasswordService;

@Component
public class BCryptEncoder implements PasswordService{
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public String encode(String origin){
		return passwordEncoder.encode(origin);
	}
	
	@Override
	public boolean matches(String origin,String hashed){
		return passwordEncoder.matches(origin, hashed);
	}	
	
}
