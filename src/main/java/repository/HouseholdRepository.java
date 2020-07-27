package repository;

import java.util.List;
import java.util.Map;

import vo.HouseHold;

public interface HouseholdRepository {
	
	public void insert(HouseHold houseHold);
	
	/*필요한 파라미터 년도 월 user 인덱스 (세션에서 꺼내서 사용하면됨)*/
	public List<HouseHold> selectList(Map<String, Object> param);
	public void update(HouseHold houseHold);
	public void delete(Integer no);
	
}
