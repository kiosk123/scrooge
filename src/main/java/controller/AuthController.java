package controller;

import static util.Utils.isEmpty;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import exception.IdNotFoundException;
import exception.PasswordDifferentException;
import service.PasswordService;
import service.UserService;
import vo.User;

//아이디 패스워드 확인 및 check하는 컨트롤러 클라이언트와 json을 통한 비동기 통신을 한다.
@Controller
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private PasswordService pwService;

	@Autowired
	private UserService userService;

	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.NO_CONTENT) // 204
	public @ResponseBody void userCheck(@RequestBody User user) {
		
		// 데이터베이스에서 User 오브젝트를 하나 꺼내온다.
		User obj = userService.getUserObject(user);

		// 비어있는 오브젝트 이면
		if (isEmpty(obj))
			// 아이디가 존재하지 않는다는 예외를 던진다.
			throw new IdNotFoundException();
		// 아이디는 존재하는데 패스워드가 일치하지 않는다면
		else if (!pwService.matches(user.getPassword(), obj.getPassword()))
			// 패스워드가 일치하지 않는다는 예외를 던진다.
			throw new PasswordDifferentException();
	}

	// 아이디가 존재하지 않을때 대신처리해주는 예외 핸들러
	@ExceptionHandler(IdNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND) // 404
	public @ResponseBody void idNotFound(){}

	// 패스워드가 틀렸을 때 처리하는 예외 핸들러
	@ExceptionHandler(PasswordDifferentException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST) // 400
	public @ResponseBody void passwordDifferent(){}

}
