package haj.com.bean;

public class SiteVisitQualUnitStandardsBean {
	
	private String saqaID;
	private String nqfLevel;
	private String title;
	
	public SiteVisitQualUnitStandardsBean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public SiteVisitQualUnitStandardsBean(String saqaID, String nqfLevel, String title) {
		super();
		this.saqaID = saqaID;
		this.nqfLevel = nqfLevel;
		this.title = title;
	}
	
	public String getSaqaID() {
		return saqaID;
	}
	public void setSaqaID(String saqaID) {
		this.saqaID = saqaID;
	}
	public String getNqfLevel() {
		return nqfLevel;
	}
	public void setNqfLevel(String nqfLevel) {
		this.nqfLevel = nqfLevel;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
