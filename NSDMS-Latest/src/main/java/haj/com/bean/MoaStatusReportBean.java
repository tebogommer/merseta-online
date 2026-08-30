package haj.com.bean;

import java.util.Date;

import haj.com.framework.IDataEntity;

public class MoaStatusReportBean implements IDataEntity {

	private String entityId;
	private String companyName;
	private String chamber;
	private String region;
	private String referanceNumber;
	private Double contractValue;
	private String moaType;
	private Date acceptanceDate;
	private Date dueDate;
	private Date allocationApprovalDate;
	private String moaStatus;

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

	public Date getAcceptanceDate() {
		return acceptanceDate;
	}

	public void setAcceptanceDate(Date acceptanceDate) {
		this.acceptanceDate = acceptanceDate;
	}

	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

	public Date getAllocationApprovalDate() {
		return allocationApprovalDate;
	}

	public void setAllocationApprovalDate(Date allocationApprovalDate) {
		this.allocationApprovalDate = allocationApprovalDate;
	}

	public String getMoaStatus() {
		return moaStatus;
	}

	public void setMoaStatus(String moaStatus) {
		this.moaStatus = moaStatus;
	}

	public Double getContractValue() {
		return contractValue;
	}

	public void setContractValue(Double contractValue) {
		this.contractValue = contractValue;
	}

}