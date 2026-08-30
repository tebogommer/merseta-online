package haj.com.dataextract.bean;

import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

public class NLRDFile27BeanNQFDesignationRegistrationVersionTwo implements IDataEntity {

	/**
	 * NLRD Files 
	 * 
	 * Version Two Bean
	 * 
	 * 30 December 2019
	 * 
	 * Enhanced extract added for File 27 NLRD NQF Designation Registration
	 * 
	 * @author Arno
	 */

	/*
	 * SUBSTRING('', 1, 10) as learnershipId,
	 * SUBSTRING('', 1, 10) as qualificationId,
	 * SUBSTRING('', 1, 10) as unitStandardId,
	 * SUBSTRING('', 1, 5) as designationId,
	 * SUBSTRING('', 1, 20) as designationRegistrationNumber,
	 * SUBSTRING('', 1, 10) as designationETQAId,
	 * '' as nQFDesignationStartDate,
	 * '' as nQFDesignationEndDate,
	 * SUBSTRING('', 1, 20) as etqaDecisionNumber,
	 * SUBSTRING('', 1, 10) as nQFDesigStatusCode,
	 * '' as dateStamp
	 */

	/** File 27 - NQF Designation Registration */

	/** Learnership_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String learnershipId;

	/** Qualification_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String qualificationId;

	/** Unit_Standard_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String unitStandardId;

	/** Designation_Id * Required: Y */
	@CSVAnnotation(length = 5)
	private String designationId;

	/** Designation_Registration_Number * Required: Y */
	@CSVAnnotation(length = 20)
	private String designationRegistrationNumber;

	/** Designation_ETQA_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String designationETQAId;

	/** NQF_Designation_Start_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date nQFDesignationStartDate;

	/** NQF_Designation_End_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date nQFDesignationEndDate;

	/** Etqa_Decision_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String etqaDecisionNumber;

	/** NQF_Desig_Status_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String nQFDesigStatusCode;

	/** Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;

	public NLRDFile27BeanNQFDesignationRegistrationVersionTwo(String learnershipId, String qualificationId, String unitStandardId, String designationId, String designationRegistrationNumber, String designationETQAId, Date nQFDesignationStartDate, Date nQFDesignationEndDate, String etqaDecisionNumber, String nQFDesigStatusCode, Date dateStamp) {
		super();
		this.learnershipId = learnershipId;
		this.qualificationId = qualificationId;
		this.unitStandardId = unitStandardId;
		this.designationId = designationId;
		this.designationRegistrationNumber = designationRegistrationNumber;
		this.designationETQAId = designationETQAId;
		this.nQFDesignationStartDate = nQFDesignationStartDate;
		this.nQFDesignationEndDate = nQFDesignationEndDate;
		this.etqaDecisionNumber = etqaDecisionNumber;
		this.nQFDesigStatusCode = nQFDesigStatusCode;
		this.dateStamp = dateStamp;
	}

	public NLRDFile27BeanNQFDesignationRegistrationVersionTwo() {
		super();
	}

	public String getLearnershipId() {
		return learnershipId;
	}

	public void setLearnershipId(String learnershipId) {
		this.learnershipId = learnershipId;
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

	public Date getnQFDesignationStartDate() {
		return nQFDesignationStartDate;
	}

	public void setnQFDesignationStartDate(Date nQFDesignationStartDate) {
		this.nQFDesignationStartDate = nQFDesignationStartDate;
	}

	public Date getnQFDesignationEndDate() {
		return nQFDesignationEndDate;
	}

	public void setnQFDesignationEndDate(Date nQFDesignationEndDate) {
		this.nQFDesignationEndDate = nQFDesignationEndDate;
	}

	public String getEtqaDecisionNumber() {
		return etqaDecisionNumber;
	}

	public void setEtqaDecisionNumber(String etqaDecisionNumber) {
		this.etqaDecisionNumber = etqaDecisionNumber;
	}

	public String getnQFDesigStatusCode() {
		return nQFDesigStatusCode;
	}

	public void setnQFDesigStatusCode(String nQFDesigStatusCode) {
		this.nQFDesigStatusCode = nQFDesigStatusCode;
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
}