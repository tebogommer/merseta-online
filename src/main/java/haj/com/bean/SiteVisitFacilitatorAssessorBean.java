package haj.com.bean;

public class SiteVisitFacilitatorAssessorBean {

	private String fullName;
	private String idNumber;
	private String title;
	
	
	
	
	public SiteVisitFacilitatorAssessorBean() {
		super();
		// TODO Auto-generated constructor stub
	}
	public SiteVisitFacilitatorAssessorBean(String fullName, String idNumber, String title) {
		super();
		this.fullName = fullName;
		this.idNumber = idNumber;
		this.title = title;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getIdNumber() {
		return idNumber;
	}
	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
}
