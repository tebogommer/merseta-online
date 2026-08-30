package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile501Bean  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 500 - Qualification Enrolment 
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
	/** * Qualification_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String qualificationId;
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
	/** * Filler01 * Required: */
	@CSVAnnotation(length = 3)
	private String filler01;
	/** * Part_Of_Id * Required: Y */
	@CSVAnnotation(length = 2)
	private String partOfId;
	/** * Learnership_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String learnershipId;
	/** * Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;
	/** * Provider_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerETQEId;
	/** * Filler02 * Required: */
	@CSVAnnotation(length = 10)
	private String assessorETQEId;
	/** * Filler02 * Required: */
	@CSVAnnotation(length = 20)
	private String filler02;
	/** * Enrolment_Status_Reason_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String enrolmentStatusReasonId;
	/** * Most_Recent_Registration_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date mostRecentRegistrationDate;
	/** * Certificate_Number * Required: N */
	@CSVAnnotation(length = 30)
	private String certificateNumber;
	/** * Filler03 * Required: */
	@CSVAnnotation(length = 4)
	private String filler03;
	/** * Filler04 * Required: */
	@CSVAnnotation(length = 10)
	private String filler04;
	/** * Filler05 * Required: */
	@CSVAnnotation(length = 10)
	private String filler05;
	/** * Filler06 * Required: */
	@CSVAnnotation(length = 10)
	private String filler06;
	/** * Economic_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String economicStatusId;
	/** * Filler07 * Required: */
	@CSVAnnotation(length = 10)
	private String filler07;
	/** * SDL_No * Required: N */
	@CSVAnnotation(length = 10)
	private String sDLNo;
	/** * Filler08 * Required: */
	@CSVAnnotation(length = 10)
	private String filler08;
	/** * Filler09 * Required: */
	@CSVAnnotation(length = 1)
	private String filler09;
	/** * Filler10 * Required: */
	@CSVAnnotation(length = 10)
	private String filler10;
	/** * Filler10 * Required: */
	@CSVAnnotation(length = 10)
	private String filler11;
	/** * Site_No * Required: N */
	@CSVAnnotation(length = 10)
	private String siteNo;
	/** * Practical_Provider_Code * Required: N */
	@CSVAnnotation(length = 20)
	private String practicalProviderCode;
	/** * Practical_Provider_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String practicalProviderETQEId;
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String fundingId;
	/** * Cumulative_Spending * Required: N */
	@CSVAnnotation(length = 10)
	private String cumulativeSpending;
	/** * OFO_Code * Required: N */
	@CSVAnnotation(length = 15)
	private String oFOCode;
	/** * Urban_Rural_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String urbanRuralId;
	/** * Learning_Programme_Type_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String learningProgrammeTypeId;
	/** * Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;
	
	
	//getset
	
	
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
	public String getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
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
	public String getFiller01() {
		return filler01;
	}
	public void setFiller01(String filler01) {
		this.filler01 = filler01;
	}
	public String getPartOfId() {
		return partOfId;
	}
	public void setPartOfId(String partOfId) {
		this.partOfId = partOfId;
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
	public String getFiller02() {
		return filler02;
	}
	public void setFiller02(String filler02) {
		this.filler02 = filler02;
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
	public String getFiller03() {
		return filler03;
	}
	public void setFiller03(String filler03) {
		this.filler03 = filler03;
	}
	public String getFiller04() {
		return filler04;
	}
	public void setFiller04(String filler04) {
		this.filler04 = filler04;
	}
	public String getFiller05() {
		return filler05;
	}
	public void setFiller05(String filler05) {
		this.filler05 = filler05;
	}
	public String getFiller06() {
		return filler06;
	}
	public void setFiller06(String filler06) {
		this.filler06 = filler06;
	}
	public String getEconomicStatusId() {
		return economicStatusId;
	}
	public void setEconomicStatusId(String economicStatusId) {
		this.economicStatusId = economicStatusId;
	}
	public String getFiller07() {
		return filler07;
	}
	public void setFiller07(String filler07) {
		this.filler07 = filler07;
	}
	public String getsDLNo() {
		return sDLNo;
	}
	public void setsDLNo(String sDLNo) {
		this.sDLNo = sDLNo;
	}
	public String getFiller08() {
		return filler08;
	}
	public void setFiller08(String filler08) {
		this.filler08 = filler08;
	}
	public String getFiller09() {
		return filler09;
	}
	public void setFiller09(String filler09) {
		this.filler09 = filler09;
	}
	public String getFiller10() {
		return filler10;
	}
	public void setFiller10(String filler10) {
		this.filler10 = filler10;
	}
	public String getFiller11() {
		return filler11;
	}
	public void setFiller11(String filler11) {
		this.filler11 = filler11;
	}
	public String getSiteNo() {
		return siteNo;
	}
	public void setSiteNo(String siteNo) {
		this.siteNo = siteNo;
	}
	public String getPracticalProviderCode() {
		return practicalProviderCode;
	}
	public void setPracticalProviderCode(String practicalProviderCode) {
		this.practicalProviderCode = practicalProviderCode;
	}
	public String getPracticalProviderETQEId() {
		return practicalProviderETQEId;
	}
	public void setPracticalProviderETQEId(String practicalProviderETQEId) {
		this.practicalProviderETQEId = practicalProviderETQEId;
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
	public String getUrbanRuralId() {
		return urbanRuralId;
	}
	public void setUrbanRuralId(String urbanRuralId) {
		this.urbanRuralId = urbanRuralId;
	}
	public String getLearningProgrammeTypeId() {
		return learningProgrammeTypeId;
	}
	public void setLearningProgrammeTypeId(String learningProgrammeTypeId) {
		this.learningProgrammeTypeId = learningProgrammeTypeId;
	}
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	

}


