package haj.com.bean;

public class ApprovedApplicationBean {
	
	private String idNum;
	private String name;
	private String surname;
	private String qualificationList;
	private String unitStandardList;
	private String applicationType;
	private String status;
	private String accreditationNumber;
	private String startDate;
	private String endDate;
	private String reviewCommDate;
	
	
	
	public ApprovedApplicationBean(String idNum, String name, String surname, String qualificationList,
			String unitStandardList, String applicationType) {
		super();
		this.idNum = idNum;
		this.name = name;
		this.surname = surname;
		this.qualificationList = qualificationList;
		this.unitStandardList = unitStandardList;
		this.applicationType = applicationType;
	}
	public String getIdNum() {
		return idNum;
	}
	public void setIdNum(String idNum) {
		this.idNum = idNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	public String getQualificationList() {
		return qualificationList;
	}
	public void setQualificationList(String qualificationList) {
		this.qualificationList = qualificationList;
	}
	public String getUnitStandardList() {
		return unitStandardList;
	}
	public void setUnitStandardList(String unitStandardList) {
		this.unitStandardList = unitStandardList;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the accreditationNumber
	 */
	public String getAccreditationNumber() {
		return accreditationNumber;
	}
	/**
	 * @param accreditationNumber the accreditationNumber to set
	 */
	public void setAccreditationNumber(String accreditationNumber) {
		this.accreditationNumber = accreditationNumber;
	}
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the reviewCommDate
	 */
	public String getReviewCommDate() {
		return reviewCommDate;
	}
	/**
	 * @param reviewCommDate the reviewCommDate to set
	 */
	public void setReviewCommDate(String reviewCommDate) {
		this.reviewCommDate = reviewCommDate;
	}
	
	
	

}
