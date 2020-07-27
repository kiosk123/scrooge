package test.unit;

import static org.junit.Assert.fail;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import repository.IncomeRespository;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-config.xml" })
public class IncomeRepostioryTest {

	@Autowired
	private IncomeRespository incomeRespository;
	
	@Transactional
	@Test
	public void select() {
		incomeRespository.selectList();
	}

}
