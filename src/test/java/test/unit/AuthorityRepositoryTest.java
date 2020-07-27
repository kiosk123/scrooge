package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import reference.ROLE;
import repository.AuthorityRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-config.xml" })
public class AuthorityRepositoryTest {
	@Autowired
	private AuthorityRepository authorityRepository;
	
	@Transactional
	@Test
	public void selectList() {
		String no=authorityRepository.selectAuthorityNo(ROLE.ROLE_ADMIN);
		assertNotNull(no);
		assertEquals(no, "1");
	}
}
