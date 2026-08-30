package haj.com.bean;

import java.util.Date;

public class DgVerificationReportBean {
	
	private String levyNumber;
	private String organisationName;
	private String tradingName;
	private Integer numberOfEmployees;
	private String organisationType;
	private String region;
	
	private Date dateVerificationGenerated;
	private String dgVerificationStatus;
	private String dgVerificationWithSdf;
	private String dgVerificationApplicationAppealed;
	private Date dateApplicationAppealed;
	
	private String cloFirstName;
	private String cloLastName;
	private String cloFirstNameOnVerification;
	private String cloLastNameOnVerification;
	private String cloRecommendationOnSubmission;
	private String cloRejectionReasons;
	
	private String crmFirstName;
	private String crmLastName;
	private String crmFirstNameOnVerification;
	private String crmLastNameOnVerification;
	private String crmDecision;
	private String crmRejectionReasons;
	private Date crmDecisionDateStamp;
	
	private String finalResponse;
	private Date dateCrmAppealedDateApproved;
	private Date finalApprovalDate;
	
	public DgVerificationReportBean() {
		super();
	}
	
	/* Getters and Setters*/
	public String getLevyNumber() {
		return levyNumber;
	}

	public void setLevyNumber(String levyNumber) {
		this.levyNumber = levyNumber;
	}

	public String getOrganisationName() {
		return organisationName;
	}

	public void setOrganisationName(String organisationName) {
		this.organisationName = organisationName;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public Integer getNumberOfEmployees() {
		return numberOfEmployees;
	}

	public void setNumberOfEmployees(Integer numberOfEmployees) {
		this.numberOfEmployees = numberOfEmployees;
	}

	public String getOrganisationType() {
		return organisationType;
	}

	public void setOrganisationType(String organisationType) {
		this.organisationType = organisationType;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public Date getDateVerificationGenerated() {
		return dateVerificationGenerated;
	}

	public void setDateVerificationGenerated(Date dateVerificationGenerated) {
		this.dateVerificationGenerated = dateVerificationGenerated;
	}

	public String getDgVerificationStatus() {
		return dgVerificationStatus;
	}

	public void setDgVerificationStatus(String dgVerificationStatus) {
		this.dgVerificationStatus = dgVerificationStatus;
	}

	public String getDgVerificationWithSdf() {
		return dgVerificationWithSdf;
	}

	public void setDgVerificationWithSdf(String dgVerificationWithSdf) {
		this.dgVerificationWithSdf = dgVerificationWithSdf;
	}

	public String getDgVerificationApplicationAppealed() {
		return dgVerificationApplicationAppealed;
	}

	public void setDgVerificationApplicationAppealed(String dgVerificationApplicationAppealed) {
		this.dgVerificationApplicationAppealed = dgVerificationApplicationAppealed;
	}

	public String getCloFirstName() {
		return cloFirstName;
	}

	public void setCloFirstName(String cloFirstName) {
		this.cloFirstName = cloFirstName;
	}

	public String getCloLastName() {
		return cloLastName;
	}

	public void setCloLastName(String cloLastName) {
		this.cloLastName = cloLastName;
	}

	public String getCloRecommendationOnSubmission() {
		return cloRecommendationOnSubmission;
	}

	public void setCloRecommendationOnSubmission(String cloRecommendationOnSubmission) {
		this.cloRecommendationOnSubmission = cloRecommendationOnSubmission;
	}

	public String getCloRejectionReasons() {
		return cloRejectionReasons;
	}

	public void setCloRejectionReasons(String cloRejectionReasons) {
		this.cloRejectionReasons = cloRejectionReasons;
	}

	public Date getDateApplicationAppealed() {
		return dateApplicationAppealed;
	}

	public void setDateApplicationAppealed(Date dateApplicationAppealed) {
		this.dateApplicationAppealed = dateApplicationAppealed;
	}

	public String getCrmFirstName() {
		return crmFirstName;
	}

	public void setCrmFirstName(String crmFirstName) {
		this.crmFirstName = crmFirstName;
	}

	public String getCrmLastName() {
		return crmLastName;
	}

	public void setCrmLastName(String crmLastName) {
		this.crmLastName = crmLastName;
	}

	public String getCrmDecision() {
		return crmDecision;
	}

	public void setCrmDecision(String crmDecision) {
		this.crmDecision = crmDecision;
	}

	public String getCrmRejectionReasons() {
		return crmRejectionReasons;
	}

	public void setCrmRejectionReasons(String crmRejectionReasons) {
		this.crmRejectionReasons = crmRejectionReasons;
	}

	public Date getCrmDecisionDateStamp() {
		return crmDecisionDateStamp;
	}

	public void setCrmDecisionDateStamp(Date crmDecisionDateStamp) {
		this.crmDecisionDateStamp = crmDecisionDateStamp;
	}

	public String getFinalResponse() {
		return finalResponse;
	}

	public void setFinalResponse(String finalResponse) {
		this.finalResponse = finalResponse;
	}

	public Date getDateCrmAppealedDateApproved() {
		return dateCrmAppealedDateApproved;
	}

	public void setDateCrmAppealedDateApproved(Date dateCrmAppealedDateApproved) {
		this.dateCrmAppealedDateApproved = dateCrmAppealedDateApproved;
	}

	public Date getFinalApprovalDate() {
		return finalApprovalDate;
	}

	public void setFinalApprovalDate(Date finalApprovalDate) {
		this.finalApprovalDate = finalApprovalDate;
	}

	public String getCloFirstNameOnVerification() {
		return cloFirstNameOnVerification;
	}

	public void setCloFirstNameOnVerification(String cloFirstNameOnVerification) {
		this.cloFirstNameOnVerification = cloFirstNameOnVerification;
	}

	public String getCloLastNameOnVerification() {
		return cloLastNameOnVerification;
	}

	public void setCloLastNameOnVerification(String cloLastNameOnVerification) {
		this.cloLastNameOnVerification = cloLastNameOnVerification;
	}

	public String getCrmFirstNameOnVerification() {
		return crmFirstNameOnVerification;
	}

	public void setCrmFirstNameOnVerification(String crmFirstNameOnVerification) {
		this.crmFirstNameOnVerification = crmFirstNameOnVerification;
	}

	public String getCrmLastNameOnVerification() {
		return crmLastNameOnVerification;
	}

	public void setCrmLastNameOnVerification(String crmLastNameOnVerification) {
		this.crmLastNameOnVerification = crmLastNameOnVerification;
	}
	
}
