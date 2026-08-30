package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile506BeanSandra  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 506 - Internship Placement 
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
	/** * Qualification_Achievement_Date * Required: Y */
	@CSVAnnotation(length = 8)
	private String qualificationAchievementDate;
	/** * Internship_Status_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String internshipStatusId;
	/** * Start_Date * Required: Y */
	@CSVAnnotation(length = 8)
	private String startDate;
	/** * End_Date * Required: Y */
	@CSVAnnotation(length = 8)
	private String endDate;
	/** * SDL_No * Required: Y */
	@CSVAnnotation(length = 10)
	private String sDLNo;
	/** * Site_No * Required: Y */
	@CSVAnnotation(length = 10)
	private String siteNo;
	/** * Provider_Code * Required: Y */
	@CSVAnnotation(length = 20)
	private String providerCode;
	/** * Provider_ETQE_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String providerETQEId;
	/** * Funding_Id * Required: Y */
	@CSVAnnotation(length = 10)
	private String fundingId;
	/** * Cumulative_Spend * Required: N */
	@CSVAnnotation(length = 10)
	private String cumulativeSpend;
	/** * OFO_Code * Required: N */
	@CSVAnnotation(length = 15)
	private String oFOCode;
	/** * Urban_Rural_ID * Required: Y */
	@CSVAnnotation(length = 10)
	private String urbanRuralID;
	/** * Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8)
	private String dateStamp;

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

	public String getQualificationAchievementDate() {
		return qualificationAchievementDate;
	}

	public void setQualificationAchievementDate(String qualificationAchievementDate) {
		this.qualificationAchievementDate = qualificationAchievementDate;
	}

	public String getInternshipStatusId() {
		return internshipStatusId;
	}

	public void setInternshipStatusId(String internshipStatusId) {
		this.internshipStatusId = internshipStatusId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
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

	public String getUrbanRuralID() {
		return urbanRuralID;
	}

	public void setUrbanRuralID(String urbanRuralID) {
		this.urbanRuralID = urbanRuralID;
	}

	public String getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}
	

}


