package haj.com.bean;

public class ReportSignOffBean {
	
	// signed off by
	private String signOffFullName;
	// designation
	private String signOffType;
	// Date and Time SPF: dd MMM yyyy HH:mm
	private String dateSignedOff;
	// Acknowledgement
	private Boolean signedOff;
	
	public ReportSignOffBean() {
		super();
	}
	public ReportSignOffBean(String signOffFullName, String signOffType, String dateSignedOff, Boolean signedOff) {
		super();
		this.signOffFullName = signOffFullName;
		this.signOffType = signOffType;
		this.dateSignedOff = dateSignedOff;
		this.signedOff = signedOff;
	}

	/* getters and setters */
	public String getSignOffFullName() {
		return signOffFullName;
	}
	public void setSignOffFullName(String signOffFullName) {
		this.signOffFullName = signOffFullName;
	}
	public String getSignOffType() {
		return signOffType;
	}
	public void setSignOffType(String signOffType) {
		this.signOffType = signOffType;
	}
	public String getDateSignedOff() {
		return dateSignedOff;
	}
	public void setDateSignedOff(String dateSignedOff) {
		this.dateSignedOff = dateSignedOff;
	}
	public Boolean getSignedOff() {
		return signedOff;
	}
	public void setSignedOff(Boolean signedOff) {
		this.signedOff = signedOff;
	}
	
}
