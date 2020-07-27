package vo;

import java.util.List;



public class HouseHoldCRUD {
	private YearAndMonth yearAndMonth;
	//삽입과 수정할 오브젝트들이 들어가는 리스트
	private List<HouseHold> houseHoldList;
	//데이터를 삭제하기 위한 NO값을 받아오는 리스트
	private List<Integer> deleteList;
	public YearAndMonth getYearAndMonth() {
		return yearAndMonth;
	}
	public void setYearAndMonth(YearAndMonth yearAndMonth) {
		this.yearAndMonth = yearAndMonth;
	}
	public List<HouseHold> getHouseHoldList() {
		return houseHoldList;
	}
	public void setHouseHoldList(List<HouseHold> houseHoldList) {
		this.houseHoldList = houseHoldList;
	}
	public List<Integer> getDeleteList() {
		return deleteList;
	}
	public void setDeleteList(List<Integer> deleteList) {
		this.deleteList = deleteList;
	}
}
