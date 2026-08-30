package haj.com.bean;

import java.util.Date;

public class MgVerificationReportBean {

	private String levyNumber;
	private String organisationName; 
	private String tradingName;
	private Integer numberOfEmployees;
	private String organisationType;
	private String region;

	private String cloFirstName;
	private String cloLastName;
	private String cloFirstNameOnMg;
	private String cloLastNameOnMg;
	private Date cloSignOffDate;

	private String crmFirstName;
	private String crmLastName;
	private String crmFirstNameOnMg;
	private String crmLastNameOnMg;
	private Date crmSignOffDate;

	private Date sdfSignOffDate;
	private String mgVerificationStatus;
	private String rejectionReasons;

	public MgVerificationReportBean() {
		super();
	}

	/* Getters and Setters */
	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(String organisationType) {
		this.organisationType = organisationType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCloFirstName() {
		return cloFirstName;
	}

	public void setCloFirstName(String cloFirstName) {
		this.cloFirstName = cloFirstName;
	}

	public String getCloLastName() {
		return cloLastName;
	}

	public void setCloLastName(String cloLastName) {
		this.cloLastName = cloLastName;
	}

	public String getCloFirstNameOnMg() {
		return cloFirstNameOnMg;
	}

	public void setCloFirstNameOnMg(String cloFirstNameOnMg) {
		this.cloFirstNameOnMg = cloFirstNameOnMg;
	}

	public String getCloLastNameOnMg() {
		return cloLastNameOnMg;
	}

	public void setCloLastNameOnMg(String cloLastNameOnMg) {
		this.cloLastNameOnMg = cloLastNameOnMg;
	}

	public Date getCloSignOffDate() {
		return cloSignOffDate;
	}

	public void setCloSignOffDate(Date cloSignOffDate) {
		this.cloSignOffDate = cloSignOffDate;
	}

	public String getCrmFirstName() {
		return crmFirstName;
	}

	public void setCrmFirstName(String crmFirstName) {
		this.crmFirstName = crmFirstName;
	}

	public String getCrmLastName() {
		return crmLastName;
	}

	public void setCrmLastName(String crmLastName) {
		this.crmLastName = crmLastName;
	}

	public String getCrmFirstNameOnMg() {
		return crmFirstNameOnMg;
	}

	public void setCrmFirstNameOnMg(String crmFirstNameOnMg) {
		this.crmFirstNameOnMg = crmFirstNameOnMg;
	}

	public String getCrmLastNameOnMg() {
		return crmLastNameOnMg;
	}

	public void setCrmLastNameOnMg(String crmLastNameOnMg) {
		this.crmLastNameOnMg = crmLastNameOnMg;
	}

	public Date getCrmSignOffDate() {
		return crmSignOffDate;
	}

	public void setCrmSignOffDate(Date crmSignOffDate) {
		this.crmSignOffDate = crmSignOffDate;
	}

	public Date getSdfSignOffDate() {
		return sdfSignOffDate;
	}

	public void setSdfSignOffDate(Date sdfSignOffDate) {
		this.sdfSignOffDate = sdfSignOffDate;
	}

	public String getMgVerificationStatus() {
		return mgVerificationStatus;
	}

	public void setMgVerificationStatus(String mgVerificationStatus) {
		this.mgVerificationStatus = mgVerificationStatus;
	}

	public String getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}
}