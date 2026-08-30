package haj.com.bean;

import java.util.Date;

import haj.com.framework.IDataEntity;

public class BankingDetailsReportBean implements IDataEntity {

	private String entityId;
	private String companyName;
	private String companyRegNumber;
	private String tradingName;
	private String bankName;
	private String accountNumber;
	private String branchCode;
	private String bankHolder;
	private String bankDetailStatus;
	private Date approvalDate;
	private String approvedBy;

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

	public String getCompanyRegNumber() {
		return companyRegNumber;
	}

	public void setCompanyRegNumber(String companyRegNumber) {
		this.companyRegNumber = companyRegNumber;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBankHolder() {
		return bankHolder;
	}

	public void setBankHolder(String bankHolder) {
		this.bankHolder = bankHolder;
	}

	public String getBankDetailStatus() {
		return bankDetailStatus;
	}

	public void setBankDetailStatus(String bankDetailStatus) {
		this.bankDetailStatus = bankDetailStatus;
	}

	public Date getApprovalDate() {
		return approvalDate;
	}

	public void setApprovalDate(Date approvalDate) {
		this.approvalDate = approvalDate;
	}

	public String getApprovedBy() {
		return approvedBy;
	}

	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

}