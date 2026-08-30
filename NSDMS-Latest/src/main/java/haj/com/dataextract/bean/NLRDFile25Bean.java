package haj.com.dataextract.bean;



import java.math.BigInteger;
import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class NLRDFile25Bean  implements IDataEntity {
	
	
	/** 
	 * NLRD Files spec as at 18 May 2018
	 */
	
	/** 
	 * File 25 - Person Information   
	 */
	
	/**	
	 * National_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 15)
	private String nationalId;
	 
	/**	
	 * Person_Alternate_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 20)
	private String personAlternateId;
	
	/**	
	 * Alternative_Id_Type	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 3)
	private BigInteger alternativeIdType;
	
	/**
	 * Equity_Code Required: Y
	 */
	@CSVAnnotation(length = 10)
	private String equityCode;
	
	/**
	 * 	Nationality_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 3)
	private String nationalityCode;

	/**	
	 * Home_Language_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String homeLanguageCode;
	 
	/**	
	 * Gender_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 1)
	private String genderCode;
	 
	/**	
	 * Citizen_Resident_Status_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String citizenResidentStatusCode;
	 
	/**	
	 * Socioeconomic_Status_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 2)
	private String socioeconomicStatusCode;
	 
	/**	
	 * Disability_Status_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 10)
	private String disabilityStatusCode;
	 
	/**	
	 * Person_Last_Name	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 45)
	private String personLastName;
	 
	/**	
	 * Person_First_Name	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 26)
	private String personFirstName;
	 
	/**	
	 * Person_Middle_Name	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 50)
	private String personMiddleName;
	 
	/**	
	 * Person_Title	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 10)
	private String personTitle;
	 
	/**	
	 * Person_Birth_Date	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date personBirthDate;
	 
	/**
	 * Person_Home_Address_1 Required: N
	 */
	@CSVAnnotation(length = 50)
	private String personHomeAddress1;
	 
	/**	
	 * Person_Home_Address_2	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 50)
	private String personHomeAddress2;
	
	/**	
	 * Person_Home_Address_3	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 50)
	private String personHomeAddress3;
	 
	/**	
	 * Person_Postal_Address_1	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 50)
	private String personPostalAddress1;
	 
	/**	
	 * Person_Postal_Address_2	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 50)
	private String personPostalAddress2;
	 
	/**	
	 * Person_Postal_Address_3	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 50)
	private String personPostalAddress3;
	 
	/**	
	 * Person_Home_Addr_Postal_Code	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 4)
	private String personHomeAddrPostalCode;
	 
	/**	
	 * Person_Postal_Addr_Post_Code	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 4)
	private String personPostalAddrPostCode;
	 
	/**	
	 * Person_Phone_Number	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 20)
	private String personPhoneNumber;
	 
	/**	
	 * Person_Cell_Phone_Number	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 20)
	private String personCellPhoneNumber;
	 
	/**	
	 * Person_Fax_Number	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 20)
	private String personFaxNumber;
	 
	/**	
	 * Person_Email_Address	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 50)
	private String personEmailAddress;
	 
	/**	
	 * Province_Code	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 2)
	private String provinceCode;
	 
	/**	
	 * Provider_Code	
	 * Required:	C	
	 */	
	@CSVAnnotation(length = 20)
	private String providerCode;
	 
	/**	
	 * Provider_Etqa_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 10)
	private String providerEtqaId;
	 
	/**	
	 * Person_Previous_Lastname	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 45)
	private String personPreviousLastname;
	 
	/**	
	 * Person_Previous_Alternate_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 20)
	private String personPreviousAlternateId;
	 
	/**
	 * Person_Previous_Alternative_Id_Type	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 3)
	private String personPreviousAlternativeIdType;
	 
	/**	
	 * Person_Previous_Provider_Code	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 20)
	private String personPreviousProviderCode;
	 
	/**	
	 * Person_Previous_Provider_Etqa_Id	
	 * Required:	C	
	 */	 
	@CSVAnnotation(length = 10)
	private String personPreviousProviderEtqaId;
	 
	/**	
	 * Seeing_Rating_Id	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 2)
	private String seeingRatingId;
	 
	/**	
	 * Hearing_Rating_Id	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 2)
	private String hearingRatingId;
	 
	/**	
	 * Communicating_Rating_Id	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 2)
	private String communicatingRatingId;
	 
	/**	
	 * Walking_Rating_Id	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 2)
	private String walkingRatingId;
	 
	/**
	 * Remembering_Rating_Id	
	 * Required:	N	
	 */	 
	@CSVAnnotation(length = 2)
	private String rememberingRatingId;
	 

	/**	
	 * Selfcare_Rating_Id	
	 * Required:	N	
	 */	
	@CSVAnnotation(length = 2)
	private String selfcareRatingId;
	 
	/**	
	 * Date_Stamp	
	 * Required:	Y	
	 */	 
	@CSVAnnotation(length = 8, className = Date.class, datePattern = DataExtractService.QCTO_DATE_FORMAT)
	private Date dateStamp;
	 


		public NLRDFile25Bean() {
			super();
			// TODO Auto-generated constructor stub
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

		public BigInteger getAlternativeIdType() {
			return alternativeIdType;
		}

		public void setAlternativeIdType(BigInteger alternativeIdType) {
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


}


