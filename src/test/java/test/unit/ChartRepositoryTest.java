package test.unit;

import static org.junit.Assert.*;

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

import repository.ChartRepository;
import vo.Chart;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-config.xml" })
public class ChartRepositoryTest {
	
	@Autowired
	private ChartRepository chartRepository;
	
	private final static Logger log=LoggerFactory.getLogger(ChartRepositoryTest.class);
	
	@Test
	public void test(){
		Map<String, Object> param=new HashMap<String,Object>();
		param.put("year", 2016);
		param.put("month", 12);
		param.put("user", 1);
		List<Chart> list=chartRepository.selectSpendData(param);
		assertEquals(list.size(), 0);
		
	}

}
