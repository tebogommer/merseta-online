package haj.com.bean;

import java.util.Date;

import haj.com.framework.IDataEntity;

public class PipTaskTrackerReportBean implements IDataEntity {

	private String entityId;
	private String companyName;
	private String chamber;
	private String region;
	private String referanceNumber;
	private String moaType;
	private Double contractValue;
	private Date approvalDate;
	private Date acceptanceDate;
	private String sdfFullName;
	private Date sdfSignDate;
	private String cloFullName;
	private Date cloSignDate;
	private String crmFullName;
	private Date crmSignDate;

	/* getters and setters */
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

	public String getChamber() {
		return chamber;
	}

	public void setChamber(String chamber) {
		this.chamber = chamber;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getReferanceNumber() {
		return referanceNumber;
	}

	public void setReferanceNumber(String referanceNumber) {
		this.referanceNumber = referanceNumber;
	}

	public String getMoaType() {
		return moaType;
	}

	public void setMoaType(String moaType) {
		this.moaType = moaType;
	}

	public Double getContractValue() {
		return contractValue;
	}

	public void setContractValue(Double contractValue) {
		this.contractValue = contractValue;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public Date getAcceptanceDate() {
		return acceptanceDate;
	}

	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}

	public String getSdfFullName() {
		return sdfFullName;
	}

	public void setSdfFullName(String sdfFullName) {
		this.sdfFullName = sdfFullName;
	}

	public Date getSdfSignDate() {
		return sdfSignDate;
	}

	public void setSdfSignDate(Date sdfSignDate) {
		this.sdfSignDate = sdfSignDate;
	}

	public String getCloFullName() {
		return cloFullName;
	}

	public void setCloFullName(String cloFullName) {
		this.cloFullName = cloFullName;
	}

	public Date getCloSignDate() {
		return cloSignDate;
	}

	public void setCloSignDate(Date cloSignDate) {
		this.cloSignDate = cloSignDate;
	}

	public String getCrmFullName() {
		return crmFullName;
	}

	public void setCrmFullName(String crmFullName) {
		this.crmFullName = crmFullName;
	}

	public Date getCrmSignDate() {
		return crmSignDate;
	}

	public void setCrmSignDate(Date crmSignDate) {
		this.crmSignDate = crmSignDate;
	}

}