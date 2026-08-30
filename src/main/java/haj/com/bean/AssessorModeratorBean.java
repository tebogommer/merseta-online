package haj.com.bean;

import java.util.Date;

public class AssessorModeratorBean {
	private Date createDate;
	private Date dateOfBirth;
	private String firstName;
	private String middleName;
	private String lastName;
	private String email;	
	private String cellNumber;
	private String telNumber;
	private String rsaIDNumber;
	private String passportNumber;
	
	private Date startDate;
	private Date endDate;
	private String startDateString;
	private String endDateString;
	private String reviewDateString;
	private Date reviewDate;
	private Date approvedDate;
	private String status;
	private String type;
	private String assModType;
	private String registrationNumber;
	private String decisionNumber;
	
	public AssessorModeratorBean() {
		super();
	}

	public Date getCreateDate() {
		return createDate;
	}


	public String getCellNumber() {
		return cellNumber;
	}

	public String getTelNumber() {
		return telNumber;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getRsaIDNumber() {
		return rsaIDNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setCellNumber(String cellNumber) {
		this.cellNumber = cellNumber;
	}

	public void setTelNumber(String telNumber) {
		this.telNumber = telNumber;
	}

	public void setRsaIDNumber(String rsaIDNumber) {
		this.rsaIDNumber = rsaIDNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public String getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	public String getAssModType() {
		return assModType;
	}

	public String getRegistrationNumber() {
		return registrationNumber;
	}

	public String getDecisionNumber() {
		return decisionNumber;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setAssModType(String assModType) {
		this.assModType = assModType;
	}

	public void setRegistrationNumber(String registrationNumber) {
		this.registrationNumber = registrationNumber;
	}

	public void setDecisionNumber(String decisionNumber) {
		this.decisionNumber = decisionNumber;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getStartDateString() {
		return startDateString;
	}

	public String getEndDateString() {
		return endDateString;
	}

	public String getReviewDateString() {
		return reviewDateString;
	}

	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public void setEndDateString(String endDateString) {
		this.endDateString = endDateString;
	}

	public void setReviewDateString(String reviewDateString) {
		this.reviewDateString = reviewDateString;
	}
}
