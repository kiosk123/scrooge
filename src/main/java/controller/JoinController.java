package controller;

import static util.Utils.*;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import exception.CodeNotEqualException;
import exception.EmailAlreadyExistException;
import exception.IdAlreadyExistException;
import exception.UserDataDuplicatedException;
import service.JoinService;
import vo.Authenticode;
import vo.Email;
import vo.User;

@Controller
@RequestMapping("/join")
public class JoinController {
	
	@Autowired
	private JoinService joinService;

	@RequestMapping(method = RequestMethod.GET)
	public String joinpage() {
		return "join/join";
	}

	//아이디 중복확인
	@RequestMapping(value = "/idCheck", method = RequestMethod.POST)
	public @ResponseBody void idCheck(@RequestBody User user) {
		if (joinService.alreadyExistsUserId(user)) {
			throw new IdAlreadyExistException();
		}
	}
	
	//이메일 인증을 하기 위한 뷰
	@RequestMapping(value="/email",method=RequestMethod.GET)
	public String emailPopup(){
		return "popup/email";
	}
	
	//이메일 중복확인
	@RequestMapping(value="/emailCheck",method=RequestMethod.POST)
	public @ResponseBody void emailCheck(@RequestBody Email email){
		if(joinService.alreadyExistsEmail(email.getEmail())){
			throw new EmailAlreadyExistException();
		}	
	}
	
	//인증 코드 입력뷰으로 이동
	@RequestMapping(value="/inputCode",method=RequestMethod.POST)
	public String emailInputCode(Email email,Model model){
		if(isEmpty(email))
			return "redirect:/join/email";		
		model.addAttribute("email",email.getEmail());
		return "popup/emailcode";
	}
	
	@RequestMapping(value="/sendCode",method=RequestMethod.POST)
	public @ResponseBody void sendCodeByEmail(@RequestBody Email email,HttpSession session){
		joinService.sendEmail(email,session);
	}
	
	//인증코드가 같은지 아닌지 처리하는 부분
	@RequestMapping(value="/codeCheck",method=RequestMethod.POST)
	public @ResponseBody void codeCheck(@RequestBody Authenticode authenticode,HttpSession session){
		String originCode=(String)session.getAttribute("code");
		if(isEmpty(originCode)||!originCode.equals(authenticode.getCode()))
			throw new CodeNotEqualException();
		session.removeAttribute("code");
	}
	
	//회원가입처리
	@RequestMapping(value="/member",method=RequestMethod.POST)
	public @ResponseBody void joinMember(@RequestBody User user){
		joinService.insertUser(user);
	}

	// 아이디 중복일경우,이메일 중복일경우,인증코드가 일치하지 않을 경우 
	@ExceptionHandler({IdAlreadyExistException.class,EmailAlreadyExistException.class,CodeNotEqualException.class})
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public @ResponseBody void alreadyExist(){}
	
	
	//유저데이터 중복일 경우
	@ExceptionHandler(UserDataDuplicatedException.class)
	@ResponseStatus(HttpStatus.CONFLICT) // 409
	public @ResponseBody void duplicatedData(){}

}
