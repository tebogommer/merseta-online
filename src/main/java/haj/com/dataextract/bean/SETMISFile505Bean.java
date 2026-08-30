package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile505Bean  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 505 - Trade Test 
	 */
	
	/** * National_Id * Required: Y */
	@CSVAnnotation(length = 15)
	private String nationalId;
	/** * Person_Alternative_Id * Required: Y */
	@CSVAnnotation(length = 20)
	private String personAlternativeId;
	/** * Aternative_Id_Type_Id * Required: Y */
	@CSVAnnotation(length = 3)
	private String aternativeIdTypeId;
	/** * Qualification_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String qualificationId;
	/** * Trade_Test_Number * Required: Y */
	@CSVAnnotation(length = 2)
	private String tradeTestNumber;
	/** * Trade_Test_Result_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String tradeTestResultId;
	/** * Trade_Test_Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String tradeTestProviderCode;
	/** * Assessor_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String assessorRegistrationNumber;
	/** * Moderator_Registration_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String moderatorRegistrationNumber;
	/** * Trade_Test_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date tradeTestDate;
	/** * Trade_Test_Result_Reason_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String tradeTestResultReasonId;
	/** * Provider_Code * Required: N */
	@CSVAnnotation(length = 20)
	private String providerCode;
	/** * Provider_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String providerETQEId;
	/** * Trade_Test_Provider_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String tradeTestProviderETQEId;
	/** * Assessor_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String assessorETQEId;
	/** * Moderator_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String moderatorETQEId;
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


	public String getPersonAlternativeId() {
		return personAlternativeId;
	}


	public void setPersonAlternativeId(String personAlternativeId) {
		this.personAlternativeId = personAlternativeId;
	}


	public String getAternativeIdTypeId() {
		return aternativeIdTypeId;
	}


	public void setAternativeIdTypeId(String aternativeIdTypeId) {
		this.aternativeIdTypeId = aternativeIdTypeId;
	}


	public String getQualificationId() {
		return qualificationId;
	}


	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}


	public String getTradeTestNumber() {
		return tradeTestNumber;
	}


	public void setTradeTestNumber(String tradeTestNumber) {
		this.tradeTestNumber = tradeTestNumber;
	}


	public String getTradeTestResultId() {
		return tradeTestResultId;
	}


	public void setTradeTestResultId(String tradeTestResultId) {
		this.tradeTestResultId = tradeTestResultId;
	}


	public String getTradeTestProviderCode() {
		return tradeTestProviderCode;
	}


	public void setTradeTestProviderCode(String tradeTestProviderCode) {
		this.tradeTestProviderCode = tradeTestProviderCode;
	}


	public String getAssessorRegistrationNumber() {
		return assessorRegistrationNumber;
	}


	public void setAssessorRegistrationNumber(String assessorRegistrationNumber) {
		this.assessorRegistrationNumber = assessorRegistrationNumber;
	}
	
	public String getModeratorRegistrationNumber() {
		return moderatorRegistrationNumber;
	}

	public void setModeratorRegistrationNumber(String moderatorRegistrationNumber) {
		this.moderatorRegistrationNumber = moderatorRegistrationNumber;
	}

	public Date getTradeTestDate() {
		return tradeTestDate;
	}

	public void setTradeTestDate(Date tradeTestDate) {
		this.tradeTestDate = tradeTestDate;
	}

	public String getTradeTestResultReasonId() {
		return tradeTestResultReasonId;
	}


	public void setTradeTestResultReasonId(String tradeTestResultReasonId) {
		this.tradeTestResultReasonId = tradeTestResultReasonId;
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


	public String getTradeTestProviderETQEId() {
		return tradeTestProviderETQEId;
	}


	public void setTradeTestProviderETQEId(String tradeTestProviderETQEId) {
		this.tradeTestProviderETQEId = tradeTestProviderETQEId;
	}


	public String getAssessorETQEId() {
		return assessorETQEId;
	}


	public void setAssessorETQEId(String assessorETQEId) {
		this.assessorETQEId = assessorETQEId;
	}


	public String getModeratorETQEId() {
		return moderatorETQEId;
	}


	public void setModeratorETQEId(String moderatorETQEId) {
		this.moderatorETQEId = moderatorETQEId;
	}


	public Date getDateStamp() {
		return dateStamp;
	}


	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}


	
	

}


