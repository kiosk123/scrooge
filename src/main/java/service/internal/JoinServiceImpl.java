package service.internal;

import static reference.ROLE.ROLE_USER;
import static util.Utils.isEmpty;

import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import exception.RunTimeMessageException;
import exception.UserDataDuplicatedException;
import repository.AuthorityRepository;
import repository.UserRepository;
import service.JoinService;
import vo.Email;
import vo.User;

@Service
public class JoinServiceImpl implements JoinService {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private AuthorityRepository authorityRepository;

	@Autowired
	private JavaMailSender mailSender;

	// 회원가입에서 이미 존재하는 아이디가 있는지 검증하는 용도로 사용함
	@Override
	public boolean alreadyExistsUserId(User user) {
		boolean isExist = false;
		User obj = userRepository.selectOne(user.getId());
		if (!isEmpty(obj)) {
			isExist = true;
		}
		return isExist;
	}

	// 이메일 중복확인시 사용
	@Override
	public boolean alreadyExistsEmail(String email) {
		boolean isExist = false;
		String obj = userRepository.selectEmailOne(email);
		if (!isEmpty(obj)) {
			isExist = true;
		}
		return isExist;
	}

	// 이메일 보내는 서비스
	@Override
	public void sendEmail(Email email, HttpSession session) {
		String code = generateCode();
		session.setAttribute("code", code);
		sendCode(email, code);
	}

	// 새로운 유저데이터를 삽입한다.
	@Transactional
	@Override
	public void insertUser(User user) {
		user.setAuthority(authorityRepository.selectAuthorityNo(ROLE_USER));
		user.setEnabled(true);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		try {
			userRepository.insert(user);
		} catch (DataIntegrityViolationException e) {
			throw new UserDataDuplicatedException();
		}
	}

	// 인증코드를 보내주기 위한 처리를 하는 메소드
	private void sendCode(Email email, String code) {

		MimeMessage msg = mailSender.createMimeMessage();
		try {
			msg.setFrom(new InternetAddress("heo9910@naver.com"));
			msg.setSubject("구두쇠 2.0에서 발송하는 인증 코드 입니다.");
			msg.setText("인증코드는 [" + code + "] 입니다");
			msg.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email.getEmail()));
		} catch (MessagingException e) {			
			throw new RunTimeMessageException(new RuntimeException(e));
		}
		try {
			mailSender.send(msg);
		} catch (MailException e) {
			e.initCause(e);
			throw e;			
		}
	}

	// 인증코드를 만들어주는 메소드
	private String generateCode() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			Integer code = (int) (Math.random() * 10);
			sb.append(code.toString());
		}
		return sb.toString();
	}

}
