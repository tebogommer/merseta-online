package haj.com.bean;

import java.util.Date;

import haj.com.framework.IDataEntity;

public class SmeQualReportBean implements IDataEntity {

	private String companyName;
	private String tradingName;
	private String entityId;
	private String siteName;
	private String firstName;
	private String lastName;
	private String identityPassportNum;
	private String approvalStatus;
	private String saqaId;
	private String qualDescr;
	private String nqfDesc;
	private String smeStatus;
	private Date approvalDate;
	private String rejectionReasons;

	/* Getters and setters */
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

	public String getEntityId() {
		return entityId;
	}

	public void setEntityId(String entityId) {
		this.entityId = entityId;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getIdentityPassportNum() {
		return identityPassportNum;
	}

	public void setIdentityPassportNum(String identityPassportNum) {
		this.identityPassportNum = identityPassportNum;
	}

	public String getApprovalStatus() {
		return approvalStatus;
	}

	public void setApprovalStatus(String approvalStatus) {
		this.approvalStatus = approvalStatus;
	}

	public String getSaqaId() {
		return saqaId;
	}

	public void setSaqaId(String saqaId) {
		this.saqaId = saqaId;
	}

	public String getQualDescr() {
		return qualDescr;
	}

	public void setQualDescr(String qualDescr) {
		this.qualDescr = qualDescr;
	}

	public String getNqfDesc() {
		return nqfDesc;
	}

	public void setNqfDesc(String nqfDesc) {
		this.nqfDesc = nqfDesc;
	}

	public String getSmeStatus() {
		return smeStatus;
	}

	public void setSmeStatus(String smeStatus) {
		this.smeStatus = smeStatus;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getRejectionReasons() {
		return rejectionReasons;
	}

	public void setRejectionReasons(String rejectionReasons) {
		this.rejectionReasons = rejectionReasons;
	}

}