package test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import repository.SpendRepository;
import vo.Spend;
import vo.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-config.xml" })
public class SpendRepositoryTest {

	private final static Logger logger = LoggerFactory.getLogger(SpendRepositoryTest.class);
	
	@Autowired
	private SpendRepository spendRepository;
	
	@Transactional
	@Test
	public void customizetest(){
		
	}
}
