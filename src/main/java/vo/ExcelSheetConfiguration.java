package vo;

import java.util.List;

public class ExcelSheetConfiguration {
	private List<ExcelData> incomeDataList;
	private List<ExcelData> spendDataList;
	private Integer incomeTotal;
	private Integer spendTotal;
	private Integer total;
	
	public List<ExcelData> getIncomeDataList() {
		return incomeDataList;
	}
	public void setIncomeDataList(List<ExcelData> incomeDataList) {
		this.incomeDataList = incomeDataList;
	}
	public List<ExcelData> getSpendDataList() {
		return spendDataList;
	}
	public void setSpendDataList(List<ExcelData> spendDataList) {
		this.spendDataList = spendDataList;
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
	public void setTotal(Integer total) {
		this.total = total;
	}
	
}
