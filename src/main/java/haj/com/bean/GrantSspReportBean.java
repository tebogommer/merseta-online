package haj.com.bean;

import java.math.BigInteger;

import haj.com.framework.IDataEntity;

public class GrantSspReportBean implements IDataEntity {
	
	private Integer grantYear;
	private String grantStatus;
	private String entityId;
	private String companyName;
	private String tradingName;
	private String sicCode;
	private String sicCodeDescription;
	private String chamber;
	private String ofoCode;
	private String ofoCodeDescription;
	private String interventionType;
	private String funding;
	private BigInteger amount;
	
	/* Getters and setters */
	public Integer getGrantYear() {
		return grantYear;
	}
	public void setGrantYear(Integer grantYear) {
		this.grantYear = grantYear;
	}
	public String getGrantStatus() {
		return grantStatus;
	}
	public void setGrantStatus(String grantStatus) {
		this.grantStatus = grantStatus;
	}
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
	public String getSicCode() {
		return sicCode;
	}
	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}
	public String getSicCodeDescription() {
		return sicCodeDescription;
	}
	public void setSicCodeDescription(String sicCodeDescription) {
		this.sicCodeDescription = sicCodeDescription;
	}
	public String getChamber() {
		return chamber;
	}
	public void setChamber(String chamber) {
		this.chamber = chamber;
	}
	public String getOfoCode() {
		return ofoCode;
	}
	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}
	public String getOfoCodeDescription() {
		return ofoCodeDescription;
	}
	public void setOfoCodeDescription(String ofoCodeDescription) {
		this.ofoCodeDescription = ofoCodeDescription;
	}
	public String getInterventionType() {
		return interventionType;
	}
	public void setInterventionType(String interventionType) {
		this.interventionType = interventionType;
	}
	public String getFunding() {
		return funding;
	}
	public void setFunding(String funding) {
		this.funding = funding;
	}
	public BigInteger getAmount() {
		return amount;
	}
	public void setAmount(BigInteger amount) {
		this.amount = amount;
	}
	
}