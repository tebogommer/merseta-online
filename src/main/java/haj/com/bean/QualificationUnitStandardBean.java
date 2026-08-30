package haj.com.bean;

public class QualificationUnitStandardBean {
	
	private String saqaID;
	private String title;
	private String strApprovedDate;
	
	
	public QualificationUnitStandardBean() {
		super();
		// TODO Auto-generated constructor stub
	}


	public QualificationUnitStandardBean(String saqaID, String title, String strApprovedDate) {
		super();
		this.saqaID = saqaID;
		this.title = title;
		this.strApprovedDate = strApprovedDate;
	}


	public String getSaqaID() {
		return saqaID;
	}


	public void setSaqaID(String saqaID) {
		this.saqaID = saqaID;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getStrApprovedDate() {
		return strApprovedDate;
	}


	public void setStrApprovedDate(String strApprovedDate) {
		this.strApprovedDate = strApprovedDate;
	}
	
	
	
	

}
