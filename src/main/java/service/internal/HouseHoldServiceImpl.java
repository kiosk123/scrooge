package service.internal;

import static reference.CategoryIndex.INCOME;
import static reference.CategoryIndex.SPEND;
import static util.Utils.isEmpty;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import repository.CategoryRepository;
import repository.HouseholdRepository;
import repository.IncomeRespository;
import repository.SpendRepository;
import service.HouseHoldService;
import vo.Category;
import vo.HouseHold;
import vo.HouseHoldViewJSON;
import vo.Income;
import vo.Spend;
import vo.User;
import vo.YearAndMonth;

@Service
public class HouseHoldServiceImpl implements HouseHoldService {

	@Autowired
	private HouseholdRepository householdRepository;
	@Autowired
	private SpendRepository spendRepository;
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private IncomeRespository incomeRespository;

	@Transactional
	//household.jsp에서 뿌려주기위한 JSON데이터를 위한 오브젝트를 생성
	public HouseHoldViewJSON createHouseholdView(YearAndMonth yearAndMonth, User user) {
		
		//데이터베이스를 조회하기 위한 user의 no
		Integer userNo=user.getNo();
		Integer year=yearAndMonth.getYear();
		Integer month=yearAndMonth.getMonth();
		
		//구분 목록을 불러온다.
		List<Category> categoryList=categoryRepository.selectList();
		
		//지출 항목 부분을 불러온다.
		List<Spend> spendList=spendRepository.selectList();
		
		//수입 항목 부분을 불러온다.
		List<Income> incomeList=incomeRespository.selectList();
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("year", yearAndMonth.getYear());
		param.put("month", yearAndMonth.getMonth());
		param.put("user", userNo);
		
		//가계부 기록을 불러온다.
		List<HouseHold> houseHoldList=householdRepository.selectList(param);
		
		//HouseHoldView에서 JSON형태의 데이터로 전송하기 위한 구조를 만든다.
		HouseHoldViewJSON view=new HouseHoldViewJSON();
		view.setCategoryList(categoryList);
		view.setHouseHoldList(houseHoldList);
		view.setSpendList(spendList);	
		view.setIncomeList(incomeList);
		
		Integer spendTotal=sumMoneyByCategory(houseHoldList,SPEND);
		Integer incomeTotal=sumMoneyByCategory(houseHoldList,INCOME);
		
		view.setSpendTotal(spendTotal);
		view.setIncomeTotal(incomeTotal);
		view.calTotal();
		
		return view;
	}
	
	
	@Transactional
	@Override
	public HouseHoldViewJSON insertAndUpdateHouseHold(List<HouseHold> houseHoldList,YearAndMonth yearAndMonth, User user) {
		
		//새로운 데이터를 삽입할 때 필요한 것
		Integer userNo=user.getNo();
		
		for(int i=0;i<houseHoldList.size();i++){
			HouseHold houseHold=houseHoldList.get(i);
			
			//no가 비어 있다면 새로운 데이터를 집어넣는 것이기 때문에 이것을 이용해서 insert와 update를 구분해줄 수 있음
			if(isEmpty(houseHold.getNo())){				
				//새로운 데이터에 user테이블을 참조할 값을 집어 넣는다.
				houseHold.setUser(userNo);
				householdRepository.insert(houseHold);
			}else {
				householdRepository.update(houseHold);
			}
		}
		
		return createHouseholdView(yearAndMonth, user);
	}
	
	//가계부 데이터를 삭제하는 메소드
	@Transactional
	@Override
	public HouseHoldViewJSON deleteHouseHold(List<Integer> deleteList,YearAndMonth yearAndMonth,User user) {
		for(int i=0; i<deleteList.size();i++){
			householdRepository.delete(deleteList.get(i));
		}
		return createHouseholdView(yearAndMonth, user);
	}


	//수입 총합, 지출 홍합을 계산
	private Integer sumMoneyByCategory(List<HouseHold> houseHoldList,Integer CategoryIndex){
		Integer total=0;
		for(int i=0;i<houseHoldList.size();i++){
			if(houseHoldList.get(i).getCategory()==CategoryIndex)
				total+=houseHoldList.get(i).getMoney();
		}
		return total;
	}

}
