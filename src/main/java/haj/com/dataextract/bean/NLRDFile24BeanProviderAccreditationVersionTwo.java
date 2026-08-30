package haj.com.dataextract.bean;

import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

public class NLRDFile24BeanProviderAccreditationVersionTwo implements IDataEntity {

	/**
	 * NLRD Files 
	 * 
	 * Version Two Bean
	 * 
	 * 30 December 2019
	 * 
	 * Enhanced extract added for File 24 NLRD Provider Accreditation
	 * 
	 * @author Arno
	 */
	
	/** File 24 - Provider Accreditation */

	/** Learnership_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String learnershipId;

	/** Qualification_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String qualificationId;

	/** Unit_Standard_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String unitStandardId;

	/** Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;

	/** Provider_Etqa_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerEtqaId;

	/** Provider_Accreditation_Num * Required: N */
	@CSVAnnotation(length = 20)
	private String providerAccreditationNum;

	/** Provider_Accredit_Assessor_Ind * Required: N */
	@CSVAnnotation(length = 1)
	private String providerAccreditAssessorInd;

	/** Provider_Accred_Start_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date providerAccredStartDate;

	/** Provider_Accred_End_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date providerAccredEndDate;

	/** Etqa_Decision_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String etqaDecisionNumber;

	/** Provider_Accred_Status_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerAccredStatusCode;

	/** Date Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;
	
	/*
	 * SUBSTRING('', 1, 10) as learnershipId,
	 * SUBSTRING('', 1, 10) as qualificationId,
	 * SUBSTRING('', 1, 10) as unitStandardId,
	 * SUBSTRING('', 1, 20) as providerCode,
	 * SUBSTRING('', 1, 10) as providerEtqaId,
	 * SUBSTRING('', 1, 20) as providerAccreditationNum,
	 * SUBSTRING('', 1, 1) as providerAccreditAssessorInd,
	 * '' as providerAccredStartDate,
	 * '' as providerAccredEndDate,
	 * SUBSTRING('', 1, 20) as etqaDecisionNumber,
	 * SUBSTRING('', 1, 10) as providerAccredStatusCode,
	 * '' as dateStamp
	 */
	
	public NLRDFile24BeanProviderAccreditationVersionTwo() {
		super();
	}

	public NLRDFile24BeanProviderAccreditationVersionTwo(String learnershipId, String qualificationId, String unitStandardId, String providerCode, String providerEtqaId, String providerAccreditationNum, String providerAccreditAssessorInd, Date providerAccredStartDate, Date providerAccredEndDate, String etqaDecisionNumber, String providerAccredStatusCode, Date dateStamp) {
		super();
		this.learnershipId = learnershipId;
		this.qualificationId = qualificationId;
		this.unitStandardId = unitStandardId;
		this.providerCode = providerCode;
		this.providerEtqaId = providerEtqaId;
		this.providerAccreditationNum = providerAccreditationNum;
		this.providerAccreditAssessorInd = providerAccreditAssessorInd;
		this.providerAccredStartDate = providerAccredStartDate;
		this.providerAccredEndDate = providerAccredEndDate;
		this.etqaDecisionNumber = etqaDecisionNumber;
		this.providerAccredStatusCode = providerAccredStatusCode;
		this.dateStamp = dateStamp;
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

	public String getProviderEtqaId() {
		return providerEtqaId;
	}

	public void setProviderEtqaId(String providerEtqaId) {
		this.providerEtqaId = providerEtqaId;
	}

	public String getProviderAccreditationNum() {
		return providerAccreditationNum;
	}

	public void setProviderAccreditationNum(String providerAccreditationNum) {
		this.providerAccreditationNum = providerAccreditationNum;
	}

	public String getProviderAccreditAssessorInd() {
		return providerAccreditAssessorInd;
	}

	public void setProviderAccreditAssessorInd(String providerAccreditAssessorInd) {
		this.providerAccreditAssessorInd = providerAccreditAssessorInd;
	}

	public Date getProviderAccredStartDate() {
		return providerAccredStartDate;
	}

	public void setProviderAccredStartDate(Date providerAccredStartDate) {
		this.providerAccredStartDate = providerAccredStartDate;
	}

	public Date getProviderAccredEndDate() {
		return providerAccredEndDate;
	}

	public void setProviderAccredEndDate(Date providerAccredEndDate) {
		this.providerAccredEndDate = providerAccredEndDate;
	}

	public String getEtqaDecisionNumber() {
		return etqaDecisionNumber;
	}

	public void setEtqaDecisionNumber(String etqaDecisionNumber) {
		this.etqaDecisionNumber = etqaDecisionNumber;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getUnitStandardId() {
		return unitStandardId;
	}

	public void setUnitStandardId(String unitStandardId) {
		this.unitStandardId = unitStandardId;
	}

	public String getProviderAccredStatusCode() {
		return providerAccredStatusCode;
	}

	public void setProviderAccredStatusCode(String providerAccredStatusCode) {
		this.providerAccredStatusCode = providerAccredStatusCode;
	}
}