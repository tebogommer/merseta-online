package haj.com.bean;

import java.util.Date;

import haj.com.framework.IDataEntity;

public class GrantsSubmissionReportBean implements IDataEntity {

	private String entityId;
	private String companyName;
	private String tradingName;
	private String categorisation;
	private String sicCode;
	private String sicCodeDesc;
	private Integer grantYear;
	private String wspType;
	private String province;
	private String region;
	private Integer numberOfEmployees;
	private String companySize;
	private String unionAssigned;
	private String unionName;
	private Date submissionDate;
	private String extensionRequestFound;
	private Date approvedDate;
	private String wspStatus;
	private String rejectReasons;

	/* Getters and setters */
	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getCategorisation() {
		return categorisation;
	}

	public void setCategorisation(String categorisation) {
		this.categorisation = categorisation;
	}

	public String getSicCode() {
		return sicCode;
	}

	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	public String getSicCodeDesc() {
		return sicCodeDesc;
	}

	public void setSicCodeDesc(String sicCodeDesc) {
		this.sicCodeDesc = sicCodeDesc;
	}

	public Integer getGrantYear() {
		return grantYear;
	}

	public void setGrantYear(Integer grantYear) {
		this.grantYear = grantYear;
	}

	public String getWspType() {
		return wspType;
	}

	public void setWspType(String wspType) {
		this.wspType = wspType;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getCompanySize() {
		return companySize;
	}

	public void setCompanySize(String companySize) {
		this.companySize = companySize;
	}

	public String getUnionAssigned() {
		return unionAssigned;
	}

	public void setUnionAssigned(String unionAssigned) {
		this.unionAssigned = unionAssigned;
	}

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public Date getSubmissionDate() {
		return submissionDate;
	}

	public void setSubmissionDate(Date submissionDate) {
		this.submissionDate = submissionDate;
	}

	public String getExtensionRequestFound() {
		return extensionRequestFound;
	}

	public void setExtensionRequestFound(String extensionRequestFound) {
		this.extensionRequestFound = extensionRequestFound;
	}

	public Date getApprovedDate() {
		return approvedDate;
	}

	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	public String getWspStatus() {
		return wspStatus;
	}

	public void setWspStatus(String wspStatus) {
		this.wspStatus = wspStatus;
	}

	public String getRejectReasons() {
		return rejectReasons;
	}

	public void setRejectReasons(String rejectReasons) {
		this.rejectReasons = rejectReasons;
	}

}