package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile502Bean  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 502 - Non NQF Intervention Enrolments 
	 */
	
	/** * National_Id * Required: Y */
	@CSVAnnotation(length = 15)
	private String nationalId;
	/** * Person_Alternative_Id * Required: Y */
	@CSVAnnotation(length = 20)
	private String personAlternateId;
	/** * Aternative_Id_Type_Id * Required: Y */
	@CSVAnnotation(length = 3)
	private String alternativeIdType;
	/** * Non_NQF_Intervention_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String NonNQFIntervCode;
	/** * Enrolment_Status_Id * Required: Y */
	@CSVAnnotation(length = 3)
	private String enrolmentStatusId;
	/** * Assessor_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String assessorRegistrationNumber;
	/** * Enrolment_Type_Id * Required: Y */
	@CSVAnnotation(length = 3)
	private String enrolmentTypeId;
	/** * Enrolment_Status_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date enrolmentStatusDate;
	/** * Enrolment_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date enrolmentDate;
	/** * Part_Of_Id * Required: Y */
	@CSVAnnotation(length = 2)
	private String partOfId;
	/** * Qualification_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String qualificationId;
	/** * Learnership_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String learnershipId;
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
	/** * Economic_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String economicStatusId;
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String fundingId;
	/** * Cumulative_Spending * Required: N */
	@CSVAnnotation(length = 10)
	private String cumulativeSpending;
	/** * OFO_Code * Required: N */
	@CSVAnnotation(length = 15)
	private String oFOCode;
	/** * SDL_No * Required: N */
	@CSVAnnotation(length = 10)
	private String sDLNo;
	/** * Site_No * Required: N */
	@CSVAnnotation(length = 10)
	private String siteNo;
	/** * Non_NQF_Interv_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String nonNQFIntervETQEId; 
	/** * Urban_Rural_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String urbanRuralId;
	/** * Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;
	

	public SETMISFile502Bean() {
		super();
		// TODO Auto-generated constructor stub
	}


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


	public String getNonNQFIntervCode() {
		return NonNQFIntervCode;
	}


	public void setNonNQFIntervCode(String nonNQFIntervCode) {
		NonNQFIntervCode = nonNQFIntervCode;
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


	public String getEnrolmentTypeId() {
		return enrolmentTypeId;
	}


	public void setEnrolmentTypeId(String enrolmentTypeId) {
		this.enrolmentTypeId = enrolmentTypeId;
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


	public String getPartOfId() {
		return partOfId;
	}


	public void setPartOfId(String partOfId) {
		this.partOfId = partOfId;
	}


	public String getQualificationId() {
		return qualificationId;
	}


	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}


	public String getLearnershipId() {
		return learnershipId;
	}


	public void setLearnershipId(String learnershipId) {
		this.learnershipId = learnershipId;
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


	public String getCumulativeSpending() {
		return cumulativeSpending;
	}


	public void setCumulativeSpending(String cumulativeSpending) {
		this.cumulativeSpending = cumulativeSpending;
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


	public String getNonNQFIntervETQEId() {
		return nonNQFIntervETQEId;
	}


	public void setNonNQFIntervETQEId(String nonNQFIntervETQEId) {
		this.nonNQFIntervETQEId = nonNQFIntervETQEId;
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
}

