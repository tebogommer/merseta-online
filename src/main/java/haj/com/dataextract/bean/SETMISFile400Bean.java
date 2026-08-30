package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class SETMISFile400Bean  implements IDataEntity {
	
	
	/** 
	 * SETMIS Files spec
	 */
	
	/** 
	 * File 400 - Persons
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
	/** * Equity_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String equityCode;
	/** * Nationality_Code * Required: Y */
	@CSVAnnotation(length = 3)
	private String nationalityCode;
	/** * Home_Language_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String homeLanguageCode;
	/** * Gender_Code * Required: Y */
	@CSVAnnotation(length = 1)
	private String genderCode;
	/** * Citizen_Resident_Status_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String citizenResidentStatusCode;
	/** * Filler01 * Required: */
	@CSVAnnotation(length = 2)
	private String filler01;
	/** * Filler02 * Required: */
	@CSVAnnotation(length = 10)
	private String filler02;
	/** * Person_Last_Name * Required: N */
	@CSVAnnotation(length = 45)
	private String personLastName;
	/** * Person_First_Name * Required: N */
	@CSVAnnotation(length = 26)
	private String personFirstName;
	/** * Person_Middle_Name * Required: N */
	@CSVAnnotation(length = 50)
	private String personMiddleName;
	/** * Person_Title * Required: N */
	@CSVAnnotation(length = 10)
	private String personTitle;
	/** * Person_Birth_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date personBirthDate;
	/** * Person_Home_Address_1 * Required: N */
	@CSVAnnotation(length = 50)
	private String personHomeAddress1;
	/** * Person_Home_Address_2 * Required: N */
	@CSVAnnotation(length = 50)
	private String personHomeAddress2;
	/** * Person_Home_Address_3 * Required: N */
	@CSVAnnotation(length = 50)
	private String personHomeAddress3;
	/** * Person_Postal_Address_1 * Required: N */
	@CSVAnnotation(length = 50)
	private String personPostalAddress1;
	/** * Person_Postal_Address_2 * Required: N */
	@CSVAnnotation(length = 50)
	private String personPostalAddress2;
	/** * Person_Postal_Address_3 * Required: N */
	@CSVAnnotation(length = 50)
	private String personPostalAddress3;
	/** * Person_Home_Addr_Postal_Code * Required: N */
	@CSVAnnotation(length = 4)
	private String personHomeAddrPostalCode;
	/** * Person_Postal_Addr_Postal_Code * Required: N */
	@CSVAnnotation(length = 4)
	private String personPostalAddrPostalCode;
	/** * Person_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String personPhoneNumber;
	/** * Person_Cell_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String personCellPhoneNumber;
	/** * Person_Fax_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String personFaxNumber;
	/** * Person_Email_Address * Required: N */
	@CSVAnnotation(length = 50)
	private String personEmailAddress;
	/** * Province_Code * Required: Y */
	@CSVAnnotation(length = 2)
	private String provinceCode;
	/** * Provider_Code * Required: N */
	@CSVAnnotation(length = 20)
	private String providerCode;
	/** * Provider_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String providerETQEId;
	/** * Person_Previous_Lastname * Required: N */
	@CSVAnnotation(length = 45)
	private String personPreviousLastname;
	/** * Person_Previous_Alternate_Id * Required: N */
	@CSVAnnotation(length = 20)
	private String personPreviousAlternateId;
	/** * Person_Previous_Alternative_Id_Type * Required: N */
	@CSVAnnotation(length = 3)
	private String personPreviousAlternativeIdType;
	/** * Person_Previous_Provider_Code * Required: N */
	@CSVAnnotation(length = 20)
	private String personPreviousProviderCode;
	/** * Person_Previous_Provider_ETQE_Id * Required: N */
	@CSVAnnotation(length = 10)
	private String personPreviousProviderETQEId;
	/** * Seeing_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String seeingRatingId;
	/** * Hearing_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String hearingRatingId;
	/** * Walking_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String walkingRatingId;
	/** * Remembering_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String rememberingRatingId;
	/** * Communication_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String communicationRatingId;
	/** * Selfcare_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String selfcareRatingId;
	/** * Last_School_EMIS_No * Required: N */
	@CSVAnnotation(length = 20)
	private String lastSchoolEMISNo;
	/** * Last_School_Year * Required: N */
	@CSVAnnotation(length = 4, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date lastSchoolYear;
	/** * STATSSA_Area_Code * Required: N */
	@CSVAnnotation(length = 20)
	private String sTATSSAAreaCode;
	/** * POPI_Act_Status_ID * Required: N */
	@CSVAnnotation(length = 2)
	private String pOPIActStatusID;
	/** * POPI_Act_Status_Date * Required: N */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date pOPIActStatusDate;
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
	public String getEquityCode() {
		return equityCode;
	}
	public void setEquityCode(String equityCode) {
		this.equityCode = equityCode;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}
	public String getHomeLanguageCode() {
		return homeLanguageCode;
	}
	public void setHomeLanguageCode(String homeLanguageCode) {
		this.homeLanguageCode = homeLanguageCode;
	}
	public String getGenderCode() {
		return genderCode;
	}
	public void setGenderCode(String genderCode) {
		this.genderCode = genderCode;
	}
	public String getCitizenResidentStatusCode() {
		return citizenResidentStatusCode;
	}
	public void setCitizenResidentStatusCode(String citizenResidentStatusCode) {
		this.citizenResidentStatusCode = citizenResidentStatusCode;
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
	public String getPersonLastName() {
		return personLastName;
	}
	public void setPersonLastName(String personLastName) {
		this.personLastName = personLastName;
	}
	public String getPersonFirstName() {
		return personFirstName;
	}
	public void setPersonFirstName(String personFirstName) {
		this.personFirstName = personFirstName;
	}
	public String getPersonMiddleName() {
		return personMiddleName;
	}
	public void setPersonMiddleName(String personMiddleName) {
		this.personMiddleName = personMiddleName;
	}
	public String getPersonTitle() {
		return personTitle;
	}
	public void setPersonTitle(String personTitle) {
		this.personTitle = personTitle;
	}
	public Date getPersonBirthDate() {
		return personBirthDate;
	}
	public void setPersonBirthDate(Date personBirthDate) {
		this.personBirthDate = personBirthDate;
	}
	public String getPersonHomeAddress1() {
		return personHomeAddress1;
	}
	public void setPersonHomeAddress1(String personHomeAddress1) {
		this.personHomeAddress1 = personHomeAddress1;
	}
	public String getPersonHomeAddress2() {
		return personHomeAddress2;
	}
	public void setPersonHomeAddress2(String personHomeAddress2) {
		this.personHomeAddress2 = personHomeAddress2;
	}
	public String getPersonHomeAddress3() {
		return personHomeAddress3;
	}
	public void setPersonHomeAddress3(String personHomeAddress3) {
		this.personHomeAddress3 = personHomeAddress3;
	}
	public String getPersonPostalAddress1() {
		return personPostalAddress1;
	}
	public void setPersonPostalAddress1(String personPostalAddress1) {
		this.personPostalAddress1 = personPostalAddress1;
	}
	public String getPersonPostalAddress2() {
		return personPostalAddress2;
	}
	public void setPersonPostalAddress2(String personPostalAddress2) {
		this.personPostalAddress2 = personPostalAddress2;
	}
	public String getPersonPostalAddress3() {
		return personPostalAddress3;
	}
	public void setPersonPostalAddress3(String personPostalAddress3) {
		this.personPostalAddress3 = personPostalAddress3;
	}
	public String getPersonHomeAddrPostalCode() {
		return personHomeAddrPostalCode;
	}
	public void setPersonHomeAddrPostalCode(String personHomeAddrPostalCode) {
		this.personHomeAddrPostalCode = personHomeAddrPostalCode;
	}
	public String getPersonPostalAddrPostalCode() {
		return personPostalAddrPostalCode;
	}
	public void setPersonPostalAddrPostalCode(String personPostalAddrPostalCode) {
		this.personPostalAddrPostalCode = personPostalAddrPostalCode;
	}
	public String getPersonPhoneNumber() {
		return personPhoneNumber;
	}
	public void setPersonPhoneNumber(String personPhoneNumber) {
		this.personPhoneNumber = personPhoneNumber;
	}
	public String getPersonCellPhoneNumber() {
		return personCellPhoneNumber;
	}
	public void setPersonCellPhoneNumber(String personCellPhoneNumber) {
		this.personCellPhoneNumber = personCellPhoneNumber;
	}
	public String getPersonFaxNumber() {
		return personFaxNumber;
	}
	public void setPersonFaxNumber(String personFaxNumber) {
		this.personFaxNumber = personFaxNumber;
	}
	public String getPersonEmailAddress() {
		return personEmailAddress;
	}
	public void setPersonEmailAddress(String personEmailAddress) {
		this.personEmailAddress = personEmailAddress;
	}
	public String getProvinceCode() {
		return provinceCode;
	}
	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
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
	public String getPersonPreviousLastname() {
		return personPreviousLastname;
	}
	public void setPersonPreviousLastname(String personPreviousLastname) {
		this.personPreviousLastname = personPreviousLastname;
	}
	public String getPersonPreviousAlternateId() {
		return personPreviousAlternateId;
	}
	public void setPersonPreviousAlternateId(String personPreviousAlternateId) {
		this.personPreviousAlternateId = personPreviousAlternateId;
	}
	public String getPersonPreviousAlternativeIdType() {
		return personPreviousAlternativeIdType;
	}
	public void setPersonPreviousAlternativeIdType(String personPreviousAlternativeIdType) {
		this.personPreviousAlternativeIdType = personPreviousAlternativeIdType;
	}
	public String getPersonPreviousProviderCode() {
		return personPreviousProviderCode;
	}
	public void setPersonPreviousProviderCode(String personPreviousProviderCode) {
		this.personPreviousProviderCode = personPreviousProviderCode;
	}
	public String getPersonPreviousProviderETQEId() {
		return personPreviousProviderETQEId;
	}
	public void setPersonPreviousProviderETQEId(String personPreviousProviderETQEId) {
		this.personPreviousProviderETQEId = personPreviousProviderETQEId;
	}
	public String getSeeingRatingId() {
		return seeingRatingId;
	}
	public void setSeeingRatingId(String seeingRatingId) {
		this.seeingRatingId = seeingRatingId;
	}
	public String getHearingRatingId() {
		return hearingRatingId;
	}
	public void setHearingRatingId(String hearingRatingId) {
		this.hearingRatingId = hearingRatingId;
	}
	public String getWalkingRatingId() {
		return walkingRatingId;
	}
	public void setWalkingRatingId(String walkingRatingId) {
		this.walkingRatingId = walkingRatingId;
	}
	public String getRememberingRatingId() {
		return rememberingRatingId;
	}
	public void setRememberingRatingId(String rememberingRatingId) {
		this.rememberingRatingId = rememberingRatingId;
	}
	public String getCommunicationRatingId() {
		return communicationRatingId;
	}
	public void setCommunicationRatingId(String communicationRatingId) {
		this.communicationRatingId = communicationRatingId;
	}
	public String getSelfcareRatingId() {
		return selfcareRatingId;
	}
	public void setSelfcareRatingId(String selfcareRatingId) {
		this.selfcareRatingId = selfcareRatingId;
	}
	public String getLastSchoolEMISNo() {
		return lastSchoolEMISNo;
	}
	public void setLastSchoolEMISNo(String lastSchoolEMISNo) {
		this.lastSchoolEMISNo = lastSchoolEMISNo;
	}
	public Date getLastSchoolYear() {
		return lastSchoolYear;
	}
	public void setLastSchoolYear(Date lastSchoolYear) {
		this.lastSchoolYear = lastSchoolYear;
	}
	public String getsTATSSAAreaCode() {
		return sTATSSAAreaCode;
	}
	public void setsTATSSAAreaCode(String sTATSSAAreaCode) {
		this.sTATSSAAreaCode = sTATSSAAreaCode;
	}
	public String getpOPIActStatusID() {
		return pOPIActStatusID;
	}
	public void setpOPIActStatusID(String pOPIActStatusID) {
		this.pOPIActStatusID = pOPIActStatusID;
	}
	public Date getpOPIActStatusDate() {
		return pOPIActStatusDate;
	}
	public void setpOPIActStatusDate(Date pOPIActStatusDate) {
		this.pOPIActStatusDate = pOPIActStatusDate;
	}
	public Date getDateStamp() {
		return dateStamp;
	}
	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}
	


		

		

		
		
}


