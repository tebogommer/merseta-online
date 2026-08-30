package haj.com.bean;

import haj.com.framework.IDataEntity;

public class LegacyDataReportBean implements IDataEntity {

	private String entityId;
	private String companyName;
	private String tradingName;
	
	private String providerEntityId;
	private String providerName;
	private String providerTradingName;
	private String accreditationNumber;
	
	private String firstName;
	private String middleNames;
	private String lastName;
	private String email;
	private String rsaIdNumber;
	private String passportNumber;

	private String status;
	private String code;
	private String title;
	private String effectiveDate;
	private String startDate;
	private String endDate;

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

	public String getProviderEntityId() {
		return providerEntityId;
	}

	public String getProviderName() {
		return providerName;
	}

	public String getProviderTradingName() {
		return providerTradingName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public String getRsaIdNumber() {
		return rsaIdNumber;
	}

	public String getPassportNumber() {
		return passportNumber;
	}

	public String getStatus() {
		return status;
	}

	public String getCode() {
		return code;
	}

	public String getTitle() {
		return title;
	}

	public String getStartDate() {
		return startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setProviderEntityId(String providerEntityId) {
		this.providerEntityId = providerEntityId;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public void setProviderTradingName(String providerTradingName) {
		this.providerTradingName = providerTradingName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setRsaIdNumber(String rsaIdNumber) {
		this.rsaIdNumber = rsaIdNumber;
	}

	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getAccreditationNumber() {
		return accreditationNumber;
	}

	public void setAccreditationNumber(String accreditationNumber) {
		this.accreditationNumber = accreditationNumber;
	}

	public String getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	public String getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}