package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile401Bean  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 401 - Person Designation 
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
	
	/** * Designation_Id * Required: Y */
	@CSVAnnotation(length = 5)
	private String designationId;
	
	/** * Designation_Registration_Number * Required: Y */
	@CSVAnnotation(length = 20)
	private String designationRegistrationNumber;
	
	/** * Designation_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String designationETQEId;
	
	/** * Designation_Start_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date designationStartDate;
	
	/** * Designation_End_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date designationEndDate;
	
	/** * Designation_Structure_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String designationStructureStatusId;
	
	/** * ETQE_Decision_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String eTQEDecisionNumber;
	
	/** * Provider_Code * Required: N */
	@CSVAnnotation(length = 20)
	private String providerCode;
	
	/** * Provider_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String providerETQEId;
	
	/** * Filler01 * Required: N */
	@CSVAnnotation(length = 10)
	private String filler01;
	
	/** * Filler02 * Required: N */
	@CSVAnnotation(length = 10)
	private String filler02;
	
	/** * Filler03 * Required: N */
	@CSVAnnotation(length = 10)
	private String filler03;
	
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
	public String getDesignationETQEId() {
		return designationETQEId;
	}
	public void setDesignationETQEId(String designationETQEId) {
		this.designationETQEId = designationETQEId;
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
	public String getDesignationStructureStatusId() {
		return designationStructureStatusId;
	}
	public void setDesignationStructureStatusId(String designationStructureStatusId) {
		this.designationStructureStatusId = designationStructureStatusId;
	}
	public String geteTQEDecisionNumber() {
		return eTQEDecisionNumber;
	}
	public void seteTQEDecisionNumber(String eTQEDecisionNumber) {
		this.eTQEDecisionNumber = eTQEDecisionNumber;
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
	public String getFiller01() {
		return filler01;
	}
	public void setFiller01(String filler01) {
		this.filler01 = filler01;
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
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}



		
		
}


