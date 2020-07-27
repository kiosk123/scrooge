package vo;

import java.util.List;

//이 클래스는 excel.jsp파일에서 데이터를 뿌리기 위해 사용되는 클래스 
public class ExcelViewJSON {
	private List<String> contentList;
	private List<String> linkList;
	public List<String> getContentList() {
		return contentList;
	}
	public void setContentList(List<String> contentList) {
		this.contentList = contentList;
	}
	public List<String> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<String> linkList) {
		this.linkList = linkList;
	}	
}
