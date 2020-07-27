package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import repository.UserRepository;
import vo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-config.xml" })
public class UserRepositoryTest {

	@Autowired
	UserRepository repository;

	private final static Logger logger = LoggerFactory.getLogger(UserRepositoryTest.class);

	// DB에 없는 데이터를 가져오려고 할때 결과 테스트
	@Test
	public void select1() {

		User user = repository.selectOne("gogothing001");

		assertNotNull(user);
	}

	
	// DB에 있는 데이터를 잘 가져오는지 테스트
	@Test
	public void select2() {
		assertNotNull(repository);

		User user = repository.selectOne("niceguy123");

		assertNotNull(user);

		assertEquals(user.getId(), "niceguy123");
		logger.info("----------------------------------------------------------------------------------------------");
		logger.info("USER Key Index : " + user.getNo());
		logger.info("USER ID : " + user.getId());
		logger.info("USER PASSWORD : " + user.getPassword());
		logger.info("USER EMAIL : " + user.getEmail());
		logger.info("USER AUTHORITY : " + user.getAuthority());
		logger.info("USER ENABLED : " + user.getEnabled());
		logger.info("----------------------------------------------------------------------------------------------");
	}

}
