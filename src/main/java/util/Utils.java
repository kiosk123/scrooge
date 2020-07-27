package util;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class Utils {
	
	// 사용자 정보를 가져오는 메소드
	public static String getPrincipal() {
		String userName = null;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}
	
	private static Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	
	//로그인한 유저인지 아닌지 판단하는 메소드
	public static boolean isAnonymous(){
		if(getAuthentication() instanceof AnonymousAuthenticationToken)
			return true;
		else
			return false;
	}
	
	//오브젝트가 null값인지 아닌지 확인하는 메소드
	public static boolean isEmpty(Object obj){
		if(obj!=null)
			return false;
		else
			return true;
	}
}
