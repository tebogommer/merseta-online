package haj.com.dataextract.bean;

import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

public class NLRDFile26BeanPersonDesignationVersionTwo implements IDataEntity {

	/**
	 * NLRD Files
	 * 
	 * Version Two Bean
	 * 
	 * 30 December 2019
	 * 
	 * Enhanced extract added for File 26 NLRD Person Designation
	 * 
	 * @author Arno
	 */

	/*
	 * SUBSTRING('', 1, 15) as nationalId,
	 * SUBSTRING('', 1, 20) as personAlternateId,
	 * SUBSTRING('', 1, 3) as alternativeIdType,
	 * SUBSTRING('', 1, 5) as designationId,
	 * SUBSTRING('', 1, 20) as designationRegistrationNumber,
	 * SUBSTRING('', 1, 10) as designationETQAId,
	 * '' as designationStartDate,
	 * '' as designationEndDate,
	 * SUBSTRING('', 1, 10) as structureStatusId,
	 * SUBSTRING('', 1, 20) as etqaDecisionNumber,
	 * SUBSTRING('', 1, 20) as providerCode,
	 * SUBSTRING('', 1, 10) as providerETQAID,
	 * '' as dateStamp
	 */

	/** File 26 - Person Designation */

	/** National_Id * Required: C */
	@CSVAnnotation(length = 15)
	private String nationalId;

	/** Person_Alternate_Id * Required: C */
	@CSVAnnotation(length = 20)
	private String personAlternateId;

	/** Alternative_Id_Type * Required: Y */
	@CSVAnnotation(length = 3)
	private String alternativeIdType;

	/** Designation_Id * Required: Y */
	@CSVAnnotation(length = 5)
	private String designationId;

	/** Designation_Registration_Number * Required: Y */
	@CSVAnnotation(length = 20)
	private String designationRegistrationNumber;

	/** Designation_ETQA_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String designationETQAId;

	/** Designation_Start_Date * Required: C */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date designationStartDate;

	/** Designation_End_Date * Required: C */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date designationEndDate;

	/** Structure_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String structureStatusId;

	/** Etqa_Decision_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String etqaDecisionNumber;

	/** Provider_Code * Required: C */
	@CSVAnnotation(length = 20)
	private String providerCode;

	/** Provider_ETQA_ID * Required: C */
	@CSVAnnotation(length = 10)
	private String providerETQAID;

	/** Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;
	
	public NLRDFile26BeanPersonDesignationVersionTwo() {
		super();
	}

	public NLRDFile26BeanPersonDesignationVersionTwo(String nationalId, String personAlternateId, String alternativeIdType, String designationId, String designationRegistrationNumber, String designationETQAId, Date designationStartDate, Date designationEndDate, String structureStatusId, String etqaDecisionNumber, String providerCode, String providerETQAID, Date dateStamp) {
		super();
		this.nationalId = nationalId;
		this.personAlternateId = personAlternateId;
		this.alternativeIdType = alternativeIdType;
		this.designationId = designationId;
		this.designationRegistrationNumber = designationRegistrationNumber;
		this.designationETQAId = designationETQAId;
		this.designationStartDate = designationStartDate;
		this.designationEndDate = designationEndDate;
		this.structureStatusId = structureStatusId;
		this.etqaDecisionNumber = etqaDecisionNumber;
		this.providerCode = providerCode;
		this.providerETQAID = providerETQAID;
		this.dateStamp = dateStamp;
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

	public String getDesignationId() {
		return designationId;
	}

	public void setDesignationId(String designationId) {
		this.designationId = designationId;
	}

	public String getDesignationRegistrationNumber() {
		return designationRegistrationNumber;
	}

	public void setDesignationRegistrationNumber(String designationRegistrationNumber) {
		this.designationRegistrationNumber = designationRegistrationNumber;
	}

	public String getDesignationETQAId() {
		return designationETQAId;
	}

	public void setDesignationETQAId(String designationETQAId) {
		this.designationETQAId = designationETQAId;
	}

	public Date getDesignationStartDate() {
		return designationStartDate;
	}

	public void setDesignationStartDate(Date designationStartDate) {
		this.designationStartDate = designationStartDate;
	}

	public Date getDesignationEndDate() {
		return designationEndDate;
	}

	public void setDesignationEndDate(Date designationEndDate) {
		this.designationEndDate = designationEndDate;
	}

	public String getStructureStatusId() {
		return structureStatusId;
	}

	public void setStructureStatusId(String structureStatusId) {
		this.structureStatusId = structureStatusId;
	}

	public String getEtqaDecisionNumber() {
		return etqaDecisionNumber;
	}

	public void setEtqaDecisionNumber(String etqaDecisionNumber) {
		this.etqaDecisionNumber = etqaDecisionNumber;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderETQAID() {
		return providerETQAID;
	}

	public void setProviderETQAID(String providerETQAID) {
		this.providerETQAID = providerETQAID;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getAlternativeIdType() {
		return alternativeIdType;
	}

	public void setAlternativeIdType(String alternativeIdType) {
		this.alternativeIdType = alternativeIdType;
	}
}