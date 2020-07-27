package service;

import java.util.List;

import vo.HouseHold;
import vo.HouseHoldViewJSON;
import vo.User;
import vo.YearAndMonth;

public interface HouseHoldService {
	public HouseHoldViewJSON createHouseholdView(YearAndMonth yearAndMonth,User user);
	public HouseHoldViewJSON insertAndUpdateHouseHold(List<HouseHold> houseHoldList,YearAndMonth yearAndMonth,User user);
	public HouseHoldViewJSON deleteHouseHold(List<Integer> deleteList,YearAndMonth yearAndMonth,User user);
}
