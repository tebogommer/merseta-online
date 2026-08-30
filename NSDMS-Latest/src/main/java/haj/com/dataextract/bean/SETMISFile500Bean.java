package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile500Bean  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 500 - Learnership Enrolment 
	 */
	

	/** * National_Id * Required: Y */
	@CSVAnnotation(length = 15)
	private String nationalId;
	/** * Person_Alternate_Id * Required: Y */
	@CSVAnnotation(length = 20)
	private String personAlternateId;
	/** * Alternative_Id_Type * Required: Y */
	@CSVAnnotation(length = 3)
	private String alternativeIdType;
	/** * Learnership_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String learnershipId;
	/** * Enrolment_Status_Id * Required: Y */
	@CSVAnnotation(length = 3)
	private String enrolmentStatusId;
	/** * Assessor_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String assessorRegistrationNumber;
	/** * Enrolment_Status_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date enrolmentStatusDate;
	/** * Enrolment_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date enrolmentDate;
	/** * Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;
	/** * Provider_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerETQEId;
	/** * Assessor_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String assessorETQEId;
	/** * Enrolment_Status_Reason_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String enrolmentStatusReasonId;
	/** * Most_Recent_Registration_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date mostRecentRegistrationDate;
	/** * Certificate_Number * Required: N */
	@CSVAnnotation(length = 30)
	private String certificateNumber;
	/** * Economic_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String economicStatusId;
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String fundingId;
	/** * Cumulative_Spend * Required: N */
	@CSVAnnotation(length = 10)
	private String cumulativeSpend;
	/** * OFO_Code * Required: N */
	@CSVAnnotation(length = 15)
	private String oFOCode;
	/** * SDL_No * Required: Y */
	@CSVAnnotation(length = 10)
	private String sDLNo;
	/** * Site_No * Required: Y */
	@CSVAnnotation(length = 10)
	private String siteNo;
	/** * Urban_Rural_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String urbanRuralId;
	/** * Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;



	public String getNationalId() {
		return nationalId;
	}


	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}


	public String getPersonAlternateId() {
		return personAlternateId;
	}


	public void setPersonAlternateId(String personAlternateId) {
		this.personAlternateId = personAlternateId;
	}


	public String getAlternativeIdType() {
		return alternativeIdType;
	}


	public void setAlternativeIdType(String alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
	}


	public String getLearnershipId() {
		return learnershipId;
	}


	public void setLearnershipId(String learnershipId) {
		this.learnershipId = learnershipId;
	}


	public String getEnrolmentStatusId() {
		return enrolmentStatusId;
	}


	public void setEnrolmentStatusId(String enrolmentStatusId) {
		this.enrolmentStatusId = enrolmentStatusId;
	}


	public String getAssessorRegistrationNumber() {
		return assessorRegistrationNumber;
	}


	public void setAssessorRegistrationNumber(String assessorRegistrationNumber) {
		this.assessorRegistrationNumber = assessorRegistrationNumber;
	}


	public Date getEnrolmentStatusDate() {
		return enrolmentStatusDate;
	}


	public void setEnrolmentStatusDate(Date enrolmentStatusDate) {
		this.enrolmentStatusDate = enrolmentStatusDate;
	}


	public Date getEnrolmentDate() {
		return enrolmentDate;
	}


	public void setEnrolmentDate(Date enrolmentDate) {
		this.enrolmentDate = enrolmentDate;
	}


	public String getProviderCode() {
		return providerCode;
	}


	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}


	public String getProviderETQEId() {
		return providerETQEId;
	}


	public void setProviderETQEId(String providerETQEId) {
		this.providerETQEId = providerETQEId;
	}


	public String getAssessorETQEId() {
		return assessorETQEId;
	}


	public void setAssessorETQEId(String assessorETQEId) {
		this.assessorETQEId = assessorETQEId;
	}


	public String getEnrolmentStatusReasonId() {
		return enrolmentStatusReasonId;
	}


	public void setEnrolmentStatusReasonId(String enrolmentStatusReasonId) {
		this.enrolmentStatusReasonId = enrolmentStatusReasonId;
	}


	public Date getMostRecentRegistrationDate() {
		return mostRecentRegistrationDate;
	}


	public void setMostRecentRegistrationDate(Date mostRecentRegistrationDate) {
		this.mostRecentRegistrationDate = mostRecentRegistrationDate;
	}


	public String getCertificateNumber() {
		return certificateNumber;
	}


	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}


	public String getEconomicStatusId() {
		return economicStatusId;
	}


	public void setEconomicStatusId(String economicStatusId) {
		this.economicStatusId = economicStatusId;
	}


	public String getFundingId() {
		return fundingId;
	}


	public void setFundingId(String fundingId) {
		this.fundingId = fundingId;
	}


	public String getCumulativeSpend() {
		return cumulativeSpend;
	}


	public void setCumulativeSpend(String cumulativeSpend) {
		this.cumulativeSpend = cumulativeSpend;
	}


	public String getoFOCode() {
		return oFOCode;
	}


	public void setoFOCode(String oFOCode) {
		this.oFOCode = oFOCode;
	}


	public String getsDLNo() {
		return sDLNo;
	}


	public void setsDLNo(String sDLNo) {
		this.sDLNo = sDLNo;
	}


	public String getSiteNo() {
		return siteNo;
	}


	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}


	public String getUrbanRuralId() {
		return urbanRuralId;
	}


	public void setUrbanRuralId(String urbanRuralId) {
		this.urbanRuralId = urbanRuralId;
	}


	public Date getDateStamp() {
		return dateStamp;
	}


	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

		//getset
		
}


