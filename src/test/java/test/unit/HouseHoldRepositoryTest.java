package test.unit;

import static org.junit.Assert.*;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import repository.HouseholdRepository;
import vo.HouseHold;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:config/application-config.xml" })
public class HouseHoldRepositoryTest {

	@Autowired
	private HouseholdRepository householdRepository;
	
	@Test
	@Transactional
	public void selectList() {
		Date date = new Date();
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(date);
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("year",calendar.get(Calendar.YEAR));
		param.put("month", calendar.get(Calendar.MONTH) + 1);
		param.put("user", 1);
		
		List<HouseHold> list=householdRepository.selectList(param);
		assertEquals(list.size(), 3);
		
	}
	
	@Test
	@Transactional
	public void insertAndUpdate(){
		Date date = new Date();
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(date);
		
		HouseHold houseHold=new HouseHold();
		houseHold.setMoney(10000);
		houseHold.setContent("감자튀김");
		houseHold.setCategory(1);
		houseHold.setDate(new Date());
		houseHold.setSpend(1);
		houseHold.setUser(1);
		householdRepository.insert(houseHold);
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("year", calendar.get(Calendar.YEAR));
		param.put("month", calendar.get(Calendar.MONTH) + 1);
		param.put("user", 1);
		
		householdRepository.selectList(param);
		
		assertNotNull(houseHold.getNo()); 
		
		houseHold.setMoney(5000);
		houseHold.setContent("조기구이");
		householdRepository.update(houseHold);
		
		householdRepository.selectList(param);
		
	}
	
	@Test
	@Transactional
	public void delete(){
		
		Date date = new Date();
		Calendar calendar= Calendar.getInstance();
		calendar.setTime(date);
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("year", calendar.get(Calendar.YEAR));
		param.put("month", calendar.get(Calendar.MONTH) + 1);
		param.put("user", 1);
		
		householdRepository.selectList(param);
		
		householdRepository.delete(1);
		
		householdRepository.selectList(param);
	}

}
