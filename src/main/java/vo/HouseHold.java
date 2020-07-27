package vo;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

public class HouseHold {
	private Long no;
	private Integer money;
	private Integer category;
	
	@DateTimeFormat(iso=ISO.DATE,pattern="yyyy-MM-dd")
	private Date date; //REG_DATE
	private Integer user; //T_USER's NO
	private Integer spend; //T_SPEND's NO
	private Integer income; //T_INCOME's NO
	private String content; //CONTENT
	
	public Long getNo() {
		return no;
	}
	public void setNo(Long no) {
		this.no = no;
	}
	public Integer getMoney() {
		return money;
	}
	public void setMoney(Integer money) {
		this.money = money;
	}
	public Integer getCategory() {
		return category;
	}
	public void setCategory(Integer category) {
		this.category = category;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getUser() {
		return user;
	}
	public void setUser(Integer user) {
		this.user = user;
	}
	public Integer getSpend() {
		return spend;
	}
	public void setSpend(Integer spend) {
		this.spend = spend;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getIncome() {
		return income;
	}
	public void setIncome(Integer income) {
		this.income = income;
	}
	
	
}
