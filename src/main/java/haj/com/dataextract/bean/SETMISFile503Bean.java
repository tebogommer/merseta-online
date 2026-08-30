package haj.com.dataextract.bean;



import java.math.BigInteger;
import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile503Bean  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 503 - Unit Standard Enrolment 
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
	/** * Unit_Standard_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private Integer unitStandardId;
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
	/** * Part_Of * Required: */
	@CSVAnnotation(length = 2)
	private String partOf;
	/** * Qualification_Id * Required: N */
	@CSVAnnotation(length = 10)
	private Integer qualificationId;
	/** * Learnership_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String learnershipId;
	/** * Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;
	/** * Provide_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerETQEId;
	/** * Assessor_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String assessorETQEId;
	/** * Filler02 * Required: */
	@CSVAnnotation(length = 20)
	private String filler02;
	/** * Filler03 * Required: */
	@CSVAnnotation(length = 20)
	private String filler03;
	/** * Enrolment_Status_Reason_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String enrolmentStatusReasonId;
	/** * Most_Recent_Registration_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date mostRecentRegistrationDate;
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
	/** * Filler08 * Required: */
	@CSVAnnotation(length = 1)
	private String filler08;
	/** * Filler09 * Required: */
	@CSVAnnotation(length = 10)
	private String filler09;
	/** * Cumulative_Spend * Required: N */
	@CSVAnnotation(length = 10)
	private String cumulativeSpend;
	/** * Certificate_Number * Required: N */
	@CSVAnnotation(length = 30)
	private String certificateNumber;
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String fundingId;
	/** * OFO_Code * Required: N */
	@CSVAnnotation(length = 15)
	private String oFOCode;
	/** * SDL_No * Required: N */
	@CSVAnnotation(length = 10)
	private String sDLNo;
	/** * Site_No * Required: N */
	@CSVAnnotation(length = 10)
	private String siteNo;
	/** * Non_NQF_Interv_Code * Required: N */
	@CSVAnnotation(length = 20)
	private String nonNQFIntervCode;
	/** * Non_NQF_Interv_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String nonNQFIntervETQEId;
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
	public Integer getUnitStandardId() {
		return unitStandardId;
	}
	public void setUnitStandardId(Integer unitStandardId) {
		this.unitStandardId = unitStandardId;
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
	public String getPartOf() {
		return partOf;
	}
	public void setPartOf(String partOf) {
		this.partOf = partOf;
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
	public String getFiller03() {
		return filler03;
	}
	public void setFiller03(String filler03) {
		this.filler03 = filler03;
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
	public String getCumulativeSpend() {
		return cumulativeSpend;
	}
	public void setCumulativeSpend(String cumulativeSpend) {
		this.cumulativeSpend = cumulativeSpend;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public String getFundingId() {
		return fundingId;
	}
	public void setFundingId(String fundingId) {
		this.fundingId = fundingId;
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
	public String getNonNQFIntervCode() {
		return nonNQFIntervCode;
	}
	public void setNonNQFIntervCode(String nonNQFIntervCode) {
		this.nonNQFIntervCode = nonNQFIntervCode;
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
	public Integer getQualificationId() {
		return qualificationId;
	}
	public void setQualificationId(Integer qualificationId) {
		this.qualificationId = qualificationId;
	}
}


