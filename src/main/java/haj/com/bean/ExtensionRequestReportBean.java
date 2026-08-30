package haj.com.bean;

import java.util.Date;

import haj.com.framework.IDataEntity;

public class ExtensionRequestReportBean implements IDataEntity {

	private Date createDate;
	private String entityId;
	private String companyName;
	private Integer grantYear;
	private String applicantDetails;
	private String statusAssigned;
	private Date newSubmissionDate;
	private String reasonForExtensionHtml;

	/* Getters and setters */
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public Integer getGrantYear() {
		return grantYear;
	}

	public void setGrantYear(Integer grantYear) {
		this.grantYear = grantYear;
	}

	public String getApplicantDetails() {
		return applicantDetails;
	}

	public void setApplicantDetails(String applicantDetails) {
		this.applicantDetails = applicantDetails;
	}

	public String getStatusAssigned() {
		return statusAssigned;
	}

	public void setStatusAssigned(String statusAssigned) {
		this.statusAssigned = statusAssigned;
	}

	public Date getNewSubmissionDate() {
		return newSubmissionDate;
	}

	public void setNewSubmissionDate(Date newSubmissionDate) {
		this.newSubmissionDate = newSubmissionDate;
	}

	public String getReasonForExtensionHtml() {
		return reasonForExtensionHtml;
	}

	public void setReasonForExtensionHtml(String reasonForExtensionHtml) {
		this.reasonForExtensionHtml = reasonForExtensionHtml;
	}

}