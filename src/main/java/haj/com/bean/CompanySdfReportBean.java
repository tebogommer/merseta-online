package haj.com.bean;

import haj.com.framework.IDataEntity;

public class CompanySdfReportBean implements IDataEntity {

	private String entityId;
	private String companyName;
	private String tradingName;
	private String region;
	private String cloUserFullName;
	private String sdfFirstName;
	private String sdfLastName;
	private String sdfEmail;
	private String sdfRsaIdNumber;
	private String sdfPassportNumber;
	private String sdfContactNumber;
	private String sdfType;
	private String companyStatus;
	private String rejectionReasons;

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

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCloUserFullName() {
		return cloUserFullName;
	}

	public void setCloUserFullName(String cloUserFullName) {
		this.cloUserFullName = cloUserFullName;
	}

	public String getSdfFirstName() {
		return sdfFirstName;
	}

	public void setSdfFirstName(String sdfFirstName) {
		this.sdfFirstName = sdfFirstName;
	}

	public String getSdfLastName() {
		return sdfLastName;
	}

	public void setSdfLastName(String sdfLastName) {
		this.sdfLastName = sdfLastName;
	}

	public String getSdfEmail() {
		return sdfEmail;
	}

	public void setSdfEmail(String sdfEmail) {
		this.sdfEmail = sdfEmail;
	}

	public String getSdfRsaIdNumber() {
		return sdfRsaIdNumber;
	}

	public void setSdfRsaIdNumber(String sdfRsaIdNumber) {
		this.sdfRsaIdNumber = sdfRsaIdNumber;
	}

	public String getSdfPassportNumber() {
		return sdfPassportNumber;
	}

	public void setSdfPassportNumber(String sdfPassportNumber) {
		this.sdfPassportNumber = sdfPassportNumber;
	}

	public String getSdfContactNumber() {
		return sdfContactNumber;
	}

	public void setSdfContactNumber(String sdfContactNumber) {
		this.sdfContactNumber = sdfContactNumber;
	}

	public String getSdfType() {
		return sdfType;
	}

	public void setSdfType(String sdfType) {
		this.sdfType = sdfType;
	}

	public String getCompanyStatus() {
		return companyStatus;
	}

	public void setCompanyStatus(String companyStatus) {
		this.companyStatus = companyStatus;
	}

	public String getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}
}