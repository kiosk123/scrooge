package vo;

import java.util.List;

//JSON 통신후 household.jsp에서 뷰를 구성하는 데 쓰인다.
public class HouseHoldViewJSON {
	private List<Spend> spendList;
	private List<HouseHold> houseHoldList;
	private List<Category> categoryList;
	private List<Income> incomeList;
	private Integer incomeTotal;
	private Integer spendTotal;
	private Integer total;
	
	//디폴트 값 세팅
	public HouseHoldViewJSON(){
		this.incomeTotal=0;
		this.spendTotal=0;
		this.total=0;
	}
	
	public List<Spend> getSpendList() {
		return spendList;
	}
	public void setSpendList(List<Spend> spendList) {
		this.spendList = spendList;
	}
	public List<HouseHold> getHouseHoldList() {
		return houseHoldList;
	}
	public void setHouseHoldList(List<HouseHold> houseHoldList) {
		this.houseHoldList = houseHoldList;
	}
	public List<Category> getCategoryList() {
		return categoryList;
	}
	public void setCategoryList(List<Category> categoryList) {
		this.categoryList = categoryList;
	}
	public Integer getIncomeTotal() {
		return incomeTotal;
	}
	public void setIncomeTotal(Integer incomeTotal) {
		this.incomeTotal = incomeTotal;
	}
	public Integer getSpendTotal() {
		return spendTotal;
	}
	public void setSpendTotal(Integer spendTotal) {
		this.spendTotal = spendTotal;
	}
	public Integer getTotal() {
		return total;
	}
	
	public List<Income> getIncomeList() {
		return incomeList;
	}

	public void setIncomeList(List<Income> incomeList) {
		this.incomeList = incomeList;
	}

	//컨트롤러에서 수입 및 지출의 총합을 계산할 때만 사용한다
	public void calTotal(){
		this.total=this.incomeTotal-this.spendTotal;
	}
}
