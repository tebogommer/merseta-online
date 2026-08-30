package haj.com.bean;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import haj.com.framework.IDataEntity;

public class WspFirmSubmissionBean implements IDataEntity {

	private String companyName;
	private String sdlNumber;
	private Date grantSubmissionDate;
	private Date grantApprovalDate;
	private Integer numberOfEmployeesOnComp;
	private BigDecimal totalMgPaid;
	private BigInteger numberOfAtr;
	private BigInteger totalEmployeesHistory;
	private BigInteger totalUnemployedEmployeesHistory;
	
	/* Getters and setters */
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getSdlNumber() {
		return sdlNumber;
	}
	public void setSdlNumber(String sdlNumber) {
		this.sdlNumber = sdlNumber;
	}
	public Date getGrantSubmissionDate() {
		return grantSubmissionDate;
	}
	public void setGrantSubmissionDate(Date grantSubmissionDate) {
		this.grantSubmissionDate = grantSubmissionDate;
	}
	public Date getGrantApprovalDate() {
		return grantApprovalDate;
	}
	public void setGrantApprovalDate(Date grantApprovalDate) {
		this.grantApprovalDate = grantApprovalDate;
	}
	public Integer getNumberOfEmployeesOnComp() {
		return numberOfEmployeesOnComp;
	}
	public void setNumberOfEmployeesOnComp(Integer numberOfEmployeesOnComp) {
		this.numberOfEmployeesOnComp = numberOfEmployeesOnComp;
	}
	public BigDecimal getTotalMgPaid() {
		return totalMgPaid;
	}
	public void setTotalMgPaid(BigDecimal totalMgPaid) {
		this.totalMgPaid = totalMgPaid;
	}
	public BigInteger getNumberOfAtr() {
		return numberOfAtr;
	}
	public void setNumberOfAtr(BigInteger numberOfAtr) {
		this.numberOfAtr = numberOfAtr;
	}
	public BigInteger getTotalEmployeesHistory() {
		return totalEmployeesHistory;
	}
	public void setTotalEmployeesHistory(BigInteger totalEmployeesHistory) {
		this.totalEmployeesHistory = totalEmployeesHistory;
	}
	public BigInteger getTotalUnemployedEmployeesHistory() {
		return totalUnemployedEmployeesHistory;
	}
	public void setTotalUnemployedEmployeesHistory(BigInteger totalUnemployedEmployeesHistory) {
		this.totalUnemployedEmployeesHistory = totalUnemployedEmployeesHistory;
	}
}