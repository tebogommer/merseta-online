package haj.com.dataextract.bean;

import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

public class NLRDFile25BeanPersonInformationVersionTwo implements IDataEntity {

	/**
	 * NLRD Files 
	 * 
	 * Version Two Bean
	 * 
	 * 30 December 2019
	 * 
	 * Enhanced extract added for File 25 NLRD Person Information
	 * 
	 * @author Arno
	 */
	
	/** File 25 - Person Information */
	
	/** National_Id * Required: C */
	@CSVAnnotation(length = 15)
	private String nationalId;

	/** Person_Alternate_Id * Required: C */
	@CSVAnnotation(length = 20)
	private String personAlternateId;

	/** Alternative_Id_Type * Required: Y */
	@CSVAnnotation(length = 3)
	private String alternativeIdType;

	/** Equity_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String equityCode;

	/** Nationality_Code * Required: Y */
	@CSVAnnotation(length = 3)
	private String nationalityCode;

	/** Home_Language_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String homeLanguageCode;

	/** Gender_Code * Required: Y */
	@CSVAnnotation(length = 1)
	private String genderCode;

	/** Citizen_Resident_Status_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String citizenResidentStatusCode;

	/** Socioeconomic_Status_Code * Required: Y */
	@CSVAnnotation(length = 2)
	private String socioeconomicStatusCode;

	/** Disability_Status_Code * Required: Y */
	@CSVAnnotation(length = 10)
	private String disabilityStatusCode;

	/** Person_Last_Name * Required: Y */
	@CSVAnnotation(length = 45)
	private String personLastName;

	/** Person_First_Name * Required: Y */
	@CSVAnnotation(length = 26)
	private String personFirstName;

	/** Person_Middle_Name * Required: N */
	@CSVAnnotation(length = 50)
	private String personMiddleName;

	/** Person_Title * Required: N */
	@CSVAnnotation(length = 10)
	private String personTitle;

	/** Person_Birth_Date * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date personBirthDate;

	/** Person_Home_Address_1 * Required: N */
	@CSVAnnotation(length = 50)
	private String personHomeAddress1;

	/** Person_Home_Address_2 * Required: N */
	@CSVAnnotation(length = 50)
	private String personHomeAddress2;

	/** Person_Home_Address_3 * Required: N */
	@CSVAnnotation(length = 50)
	private String personHomeAddress3;

	/** Person_Postal_Address_1 * Required: N */
	@CSVAnnotation(length = 50)
	private String personPostalAddress1;

	/** Person_Postal_Address_2 * Required: N */
	@CSVAnnotation(length = 50)
	private String personPostalAddress2;

	/** Person_Postal_Address_3 * Required: N */
	@CSVAnnotation(length = 50)
	private String personPostalAddress3;

	/** Person_Home_Addr_Postal_Code * Required: N */
	@CSVAnnotation(length = 4)
	private String personHomeAddrPostalCode;

	/** Person_Postal_Addr_Post_Code * Required: N */
	@CSVAnnotation(length = 4)
	private String personPostalAddrPostCode;

	/** Person_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String personPhoneNumber;

	/** Person_Cell_Phone_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String personCellPhoneNumber;

	/** Person_Fax_Number * Required: N */
	@CSVAnnotation(length = 20)
	private String personFaxNumber;

	/** Person_Email_Address * Required: N */
	@CSVAnnotation(length = 50)
	private String personEmailAddress;

	/** Province_Code * Required: Y */
	@CSVAnnotation(length = 2)
	private String provinceCode;

	/** Provider_Code * Required: C */
	@CSVAnnotation(length = 20)
	private String providerCode;

	/** Provider_Etqa_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String providerEtqaId;

	/** Person_Previous_Lastname * Required: N */
	@CSVAnnotation(length = 45)
	private String personPreviousLastname;

	/** Person_Previous_Alternate_Id * Required: C */
	@CSVAnnotation(length = 20)
	private String personPreviousAlternateId;

	/** Person_Previous_Alternative_Id_Type * Required: C */
	@CSVAnnotation(length = 3)
	private String personPreviousAlternativeIdType;

	/** Person_Previous_Provider_Code * Required: C */
	@CSVAnnotation(length = 20)
	private String personPreviousProviderCode;

	/** Person_Previous_Provider_Etqa_Id * Required: C */
	@CSVAnnotation(length = 10)
	private String personPreviousProviderEtqaId;

	/** Seeing_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String seeingRatingId;

	/** Hearing_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String hearingRatingId;

	/** Communicating_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String communicatingRatingId;

	/** Walking_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String walkingRatingId;

	/** Remembering_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String rememberingRatingId;

	/** Selfcare_Rating_Id * Required: N */
	@CSVAnnotation(length = 2)
	private String selfcareRatingId;

	/** Date_Stamp * Required: Y */
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;
	
	/*
	 * SUBSTRING('', 1, 15) as nationalId,
	 * SUBSTRING('', 1, 20) as personAlternateId,
	 * SUBSTRING('', 1, 3) as alternativeIdType,
	 * SUBSTRING('', 1, 10) as equityCode,
	 * SUBSTRING('', 1, 3) as nationalityCode,
	 * SUBSTRING('', 1, 10) as homeLanguageCode,
	 * SUBSTRING('', 1, 1) as genderCode,
	 * SUBSTRING('', 1, 10) as citizenResidentStatusCode,
	 * SUBSTRING('', 1, 2) as socioeconomicStatusCode,
	 * SUBSTRING('', 1, 10) as disabilityStatusCode,
	 * SUBSTRING('', 1, 45) as personLastName,
	 * SUBSTRING('', 1, 26) as personFirstName,
	 * SUBSTRING('', 1, 50) as personMiddleName,
	 * SUBSTRING('', 1, 10) as personTitle,
	 * '' as personBirthDate,
	 * SUBSTRING('', 1, 50) as personHomeAddress1,
	 * SUBSTRING('', 1, 50) as personHomeAddress2,
	 * SUBSTRING('', 1, 50) as personHomeAddress3,
	 * SUBSTRING('', 1, 50) as personPostalAddress1,
	 * SUBSTRING('', 1, 50) as personPostalAddress2,
	 * SUBSTRING('', 1, 50) as personPostalAddress3,
	 * SUBSTRING('', 1, 4) as personHomeAddrPostalCode,
	 * SUBSTRING('', 1, 4) as personPostalAddrPostCode,
	 * SUBSTRING('', 1, 20) as personPhoneNumber,
	 * SUBSTRING('', 1, 20) as personCellPhoneNumber,
	 * SUBSTRING('', 1, 20) as personFaxNumber,
	 * SUBSTRING('', 1, 50) as personEmailAddress,
	 * SUBSTRING('', 1, 2) as provinceCode,
	 * SUBSTRING('', 1, 20) as providerCode,
	 * SUBSTRING('', 1, 10) as providerEtqaId,
	 * SUBSTRING('', 1, 45) as personPreviousLastname,
	 * SUBSTRING('', 1, 20) as personPreviousAlternateId,
	 * SUBSTRING('', 1, 3) as personPreviousAlternativeIdType,
	 * SUBSTRING('', 1, 20) as personPreviousProviderCode,
	 * SUBSTRING('', 1, 10) as personPreviousProviderEtqaId,
	 * SUBSTRING('', 1, 2) as seeingRatingId,
	 * SUBSTRING('', 1, 2) as hearingRatingId,
	 * SUBSTRING('', 1, 2) as communicatingRatingId,
	 * SUBSTRING('', 1, 2) as walkingRatingId,
	 * SUBSTRING('', 1, 2) as rememberingRatingId,
	 * SUBSTRING('', 1, 2) as selfcareRatingId,
	 * '' as dateStamp
	 */
	
	public NLRDFile25BeanPersonInformationVersionTwo() {
		super();
	}

	public NLRDFile25BeanPersonInformationVersionTwo(String nationalId, String personAlternateId, String alternativeIdType, String equityCode, String nationalityCode, String homeLanguageCode, String genderCode, String citizenResidentStatusCode, String socioeconomicStatusCode, String disabilityStatusCode, String personLastName, String personFirstName, String personMiddleName, String personTitle, Date personBirthDate, String personHomeAddress1, String personHomeAddress2, String personHomeAddress3, String personPostalAddress1, String personPostalAddress2, String personPostalAddress3,
			String personHomeAddrPostalCode, String personPostalAddrPostCode, String personPhoneNumber, String personCellPhoneNumber, String personFaxNumber, String personEmailAddress, String provinceCode, String providerCode, String providerEtqaId, String personPreviousLastname, String personPreviousAlternateId, String personPreviousAlternativeIdType, String personPreviousProviderCode, String personPreviousProviderEtqaId, String seeingRatingId, String hearingRatingId, String communicatingRatingId, String walkingRatingId, String rememberingRatingId, String selfcareRatingId, Date dateStamp) {
		super();
		this.nationalId = nationalId;
		this.personAlternateId = personAlternateId;
		this.alternativeIdType = alternativeIdType;
		this.equityCode = equityCode;
		this.nationalityCode = nationalityCode;
		this.homeLanguageCode = homeLanguageCode;
		this.genderCode = genderCode;
		this.citizenResidentStatusCode = citizenResidentStatusCode;
		this.socioeconomicStatusCode = socioeconomicStatusCode;
		this.disabilityStatusCode = disabilityStatusCode;
		this.personLastName = personLastName;
		this.personFirstName = personFirstName;
		this.personMiddleName = personMiddleName;
		this.personTitle = personTitle;
		this.personBirthDate = personBirthDate;
		this.personHomeAddress1 = personHomeAddress1;
		this.personHomeAddress2 = personHomeAddress2;
		this.personHomeAddress3 = personHomeAddress3;
		this.personPostalAddress1 = personPostalAddress1;
		this.personPostalAddress2 = personPostalAddress2;
		this.personPostalAddress3 = personPostalAddress3;
		this.personHomeAddrPostalCode = personHomeAddrPostalCode;
		this.personPostalAddrPostCode = personPostalAddrPostCode;
		this.personPhoneNumber = personPhoneNumber;
		this.personCellPhoneNumber = personCellPhoneNumber;
		this.personFaxNumber = personFaxNumber;
		this.personEmailAddress = personEmailAddress;
		this.provinceCode = provinceCode;
		this.providerCode = providerCode;
		this.providerEtqaId = providerEtqaId;
		this.personPreviousLastname = personPreviousLastname;
		this.personPreviousAlternateId = personPreviousAlternateId;
		this.personPreviousAlternativeIdType = personPreviousAlternativeIdType;
		this.personPreviousProviderCode = personPreviousProviderCode;
		this.personPreviousProviderEtqaId = personPreviousProviderEtqaId;
		this.seeingRatingId = seeingRatingId;
		this.hearingRatingId = hearingRatingId;
		this.communicatingRatingId = communicatingRatingId;
		this.walkingRatingId = walkingRatingId;
		this.rememberingRatingId = rememberingRatingId;
		this.selfcareRatingId = selfcareRatingId;
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

	public String getSocioeconomicStatusCode() {
		return socioeconomicStatusCode;
	}

	public void setSocioeconomicStatusCode(String socioeconomicStatusCode) {
		this.socioeconomicStatusCode = socioeconomicStatusCode;
	}

	public String getDisabilityStatusCode() {
		return disabilityStatusCode;
	}

	public void setDisabilityStatusCode(String disabilityStatusCode) {
		this.disabilityStatusCode = disabilityStatusCode;
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

	public String getPersonPostalAddrPostCode() {
		return personPostalAddrPostCode;
	}

	public void setPersonPostalAddrPostCode(String personPostalAddrPostCode) {
		this.personPostalAddrPostCode = personPostalAddrPostCode;
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

	public String getProviderEtqaId() {
		return providerEtqaId;
	}

	public void setProviderEtqaId(String providerEtqaId) {
		this.providerEtqaId = providerEtqaId;
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

	public String getPersonPreviousProviderEtqaId() {
		return personPreviousProviderEtqaId;
	}

	public void setPersonPreviousProviderEtqaId(String personPreviousProviderEtqaId) {
		this.personPreviousProviderEtqaId = personPreviousProviderEtqaId;
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

	public String getCommunicatingRatingId() {
		return communicatingRatingId;
	}

	public void setCommunicatingRatingId(String communicatingRatingId) {
		this.communicatingRatingId = communicatingRatingId;
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

	public String getSelfcareRatingId() {
		return selfcareRatingId;
	}

	public void setSelfcareRatingId(String selfcareRatingId) {
		this.selfcareRatingId = selfcareRatingId;
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