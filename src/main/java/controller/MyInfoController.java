package controller;

import javax.servlet.http.HttpSession;
import static util.Utils.getPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import exception.ChangingPasswordIsMeaninglessException;
import exception.PasswordDifferentException;
import service.PasswordService;
import service.UserService;
import vo.User;

@Controller
@RequestMapping("/myinfo")
public class MyInfoController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private PasswordService passwordService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String myinfo(){
		return "myinfo/myinfo";
	}
	
	@RequestMapping(value="/changePassword",method=RequestMethod.GET)
	public String modifyPasswordPage(){
		return "myinfo/changePassword";
	}
	
	@RequestMapping(value="/withdrawMember",method=RequestMethod.GET)
	public String withdrawMemberPage(){
		return "myinfo/withdrawMember";
	}
	
	@RequestMapping(value="/changePassword",method=RequestMethod.PUT)
	public @ResponseBody void changePassword(@RequestBody User user){
		User fromDB=getUser(getPrincipal());
		if(passwordService.matches(user.getPassword(),fromDB.getPassword())){
			throw new ChangingPasswordIsMeaninglessException(); //비밀번호가 같으면 바꾸는 의미가 없으므로
		}
		user.setNo(fromDB.getNo());
		userService.changePassword(user);
	}
	
	@RequestMapping(value="/withdrawMember",method=RequestMethod.DELETE)
	public @ResponseBody User withdrawMember(@RequestBody User user,HttpSession session){
		User fromDB=getUser(getPrincipal());
		if(!passwordService.matches(user.getPassword(),fromDB.getPassword())){
			throw new PasswordDifferentException(); //비밀번호가 다르면 회원 탈퇴 못하게 함
		}
		user.setNo(fromDB.getNo());
		user.setId(fromDB.getId());
		userService.withdrawMember(user);
		session.invalidate(); //탈퇴한 회원은 서비스 이용못하게 세션 다 날림
		return user;
	}
	
	//기존 패스워드와 변경하려는 패스워드가 같으면 의미가 없으므로...
	@ExceptionHandler(ChangingPasswordIsMeaninglessException.class)
	@ResponseStatus(HttpStatus.CONFLICT) //409
	public @ResponseBody void changingPasswordIsMeaningless(){}
	
	@ExceptionHandler(PasswordDifferentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)//400
	public @ResponseBody void passwordNotEqual(){}
	
	private User getUser(String userId){
		User user=new User();
		user.setId(userId);
		user=userService.getUserObject(user);
		return user;
	}
}
