package haj.com.bean;

public class MillwrightToolsListBean 
{
	private String equipment;
	private String ration;
	private Boolean yesNo;
	private String comments;
	
	
	public MillwrightToolsListBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public MillwrightToolsListBean(String equipment, String ration, Boolean yesNo, String comments) {
		super();
		this.equipment = equipment;
		this.ration = ration;
		this.yesNo = yesNo;
		this.comments = comments;
	}
	public String getEquipment() {
		return equipment;
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getRation() {
		return ration;
	}
	public void setRation(String ration) {
		this.ration = ration;
	}
	public Boolean isYesNo() {
		return yesNo;
	}
	public void setYesNo(Boolean yesNo) {
		this.yesNo = yesNo;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	

}
