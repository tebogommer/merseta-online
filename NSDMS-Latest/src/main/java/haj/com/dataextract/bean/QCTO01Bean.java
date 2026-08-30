package haj.com.dataextract.bean;



import java.util.Date;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;


public class QCTO01Bean  implements IDataEntity {

	 /**
	  * SDP Code
	  * Required: True
	  */
	 @CSVAnnotation(length = 20)
     private String  sdpCode;
	 
	 /**
	  * National Id
	  * Required: True
	  * Either this field or the Learner Alternate ID- field must contain a value. 
	  */
	 @CSVAnnotation(length=15)
     private String nationalId;
	 
	 /**
	  * Learner Alternate ID
	  * Required: True
	  */
	 @CSVAnnotation(length=20)
     private String learnerAlternateID;

	 /**
	  * Alternative Id Type 
	  * Required: True
	  * Default if nationalId is supplied 533
	  * Allowed codes
	  * 527 = Passport Number or Foreign ID Number
	  * 533 = None
	  * 565 = Refugee Number
	  * 538 = Work Permit Number
	  * 540 = Birth Certificate number
	  */
	 @CSVAnnotation(length=3)
     private String alternativeIdType;
	 
	 /**
	  * Equity Code 
	  * Required: True
	  * Allowed codes
	  * BA = Black African
	  * BC = Coloured
	  * BI	= Indian/Asian
	  * Oth	= Other
	  * U = Unknown
	  * Wh = White
	  */
	 @CSVAnnotation(length=10)
     private String equityCode;
	 
	 /**
	  * Nationality Code 
	  * Required: True
	  * Allowed codes
	  * U	= Unspecified
	  * SA	= South Africa
	  * SDC	= SADC except SA (i.e. NAM to ZAI)
	  * NAM	= Namibia
	  * BOT	= Botswana
	  * ZIM	= Zimbabwe
	  * ANG	= Angola
	  * MOZ	= Mozambique 
	  * LES	= Lesotho
	  * SWA	= Swaziland
	  * MAL	= Malawi
	  * ZAM	= Zambia
	  * MAU	= Mauritius
	  * TAN	= Tanzania
	  * SEY	= Seychelles
	  * ZAI	= Zaire
	  * ROA	= Rest of Africa
	  * EUR	= European countries
	  * AIS	= Asian countries
	  * NOR	= North American
	  * COUNTRIES
	  * SOU	= Central and South American countries
	  * AUS	= Australia Oceania countries
	  * OOC	= Other and rest of Oceania
	  * NOT	= N/A: Institution
	  */
	 @CSVAnnotation(length=3)
     private String nationalityCode;

	 /**
	  * Home Language Code 
	  * Required: True
	  * Allowed codes
	  * Eng = English
	  * Afr = Afrikaans
	  * Oth = Other
	  * SASL = South African Sign Language
	  * Sep = sePedi [also known as Northern Sotho / Sesotho sa Lebowa]
	  * Ses = seSotho
	  * Set = seTswana
	  * Swa = siSwati
	  * Tsh = tshiVenda
	  * Xho = isiXhosa
	  * Xit = xiTsonga
	  * Zul = isiZulu
	  * Nde = isiNdebele
	  */
	 @CSVAnnotation(length=10)
     private String homeLanguageCode;

	/**
	 * Gender Code 
	 * Required: True
	 * Allowed codes
	 * M = Male
	 * F = Female 
	 * The Gender code must meet the gender indicator defined in the National ID number.
	 * A male ID number and Female gender code indicator, and vice versa, will result in an upload being discarded.  
	 */
	 @CSVAnnotation(length=1)
	 private String genderCode;
	 
	/**
	 * Citizen Resident Status Code 
	 * Required: True
	 * Allowed codes
	 * SA = South Africa
	 * O = Other
	 * D = Dual (SA plus other)
	 * PR = Permanent Resident
	 * U = Unknown	 
	 */
	 @CSVAnnotation(length=10)
     private String citizenResidentStatusCode;
	 
	 /**
	  * Socioeconomic Status Code 
	  * Required: True
	  * Allowed codes
	  * 01 = Employed
	  * 02 = Unemployed, looking for work
	  * 03 = Not working  not looking for work
	  * 04 = Home-maker (not working)
	  * 06 = Scholar/student (not working)
	  * 07 = Pensioner /retired (not working)
	  * 08 = Not working  disabled person
	  * 09 = Not working  not wishing to work
	  * 10 = Not working  Not elsewhere classified
	  * 97 = N/A: Aged <15
	  * 98 = N/A: Institution
	  * U = Unspecified
	  */
	 @CSVAnnotation(length=2)
     private String socioeconomicStatusCode;
	 
	 /**
	  * Disability Status Code  
	  * Required: True
	  * Allowed codes
	  * N = None
	  * 01 = Sight (even with glasses)
	  * 02 = Hearing (even with a hearing aid)
	  * 03 = Communication (talking, listening)
	  * 04 = Physical (moving, standing, grasping)
	  * 05 = Intellectual (difficulties in learning); retardation
	  * 06 = Emotional (behavioural or psychological)
	  * 07 = Multiple
	  * 09 = Disabled but unspecified
	  */
	 @CSVAnnotation(length=10)
     private String disabilityStatusCode;
	 
	 
	 /**
	  * Disability Rating 
	  * Required: True ?(Conditional)
	  * This field must contain data if code 01 to 09 is selected in the Disability Status Code field
	  * Allowed codes
	  * 01 = No difficulty
	  * 02 = Some difficulty
	  * 03 = A lot of difficulty
	  * 04 = Cannot do at all
	  * 06 = Cannot yet be determined
	  * 60 = May be part of multiple difficulties (TBC)
	  * 70 = May have difficulty (TBC)
	  * 80 = Former difficulty - none now
	  */
	 @CSVAnnotation(length=10)
     private String disabilityRating;
	 
	 /**
	  * Immigrant Status
	  * Required: True
	  * Allowed codes
	  * 01 = Immigrant
	  * 02 = Refugee
	  * 03 = SA Citizen
	  */
	 @CSVAnnotation(length=2)
     private String immigrantStatus;
	 
	 /**
	  * Learner Last Name 
	  * Required: False
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ'  ^  "  `
	  */
	 @CSVAnnotation(length=26)
     private String learnerLastName;
	 
	 /**
	  * Learner First Name 
	  * Required: True
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ'  ^  "  `
	  */
	 @CSVAnnotation(length=26)
     private String learnerFirstName;
	 
	 /**
	  * Learner Middle Name 
	  * Required: False
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ'  ^  "  `
	  */
	 @CSVAnnotation(length=26)
     private String learnerMiddleName;
	 
	 /**
	  * Learner Title
	  * Required: True
	  * Allowed codes
	  * Mr
	  * Mrs
	  * Ms
	  * Miss
	  * Dr
	  * Prof
	  */
	 @CSVAnnotation(length=10)
     private String learnerTitle;
	 
	 
	 /**
	  * Learner Birth Date
	  * Required: True
	  */
	 @CSVAnnotation(length = 8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	 private Date learnerBirthDate;
	 
	 /**
	  * Learner Home Address 1  
	  * Required: True
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890#&()/\:._',
	  */
	 @CSVAnnotation(length=50)
     private String learnerHomeAddress1;
	 
	 /**
	  * Learner Home Address 2  
	  * Required: True
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890#&()/\:._',
	  */
	 @CSVAnnotation(length=50)
     private String learnerHomeAddress2;
	 
	 /**
	  * Learner Home Address 3  
	  * Required: True
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890#&()/\:._',
	  */
	 @CSVAnnotation(length=50)
     private String learnerHomeAddress3;
	 
	 /**
	  * Learner Postal Address 1  
	  * Required: True
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890#&()/\:._',
	  */
	 @CSVAnnotation(length=50)
     private String learnerPostalAddress1;
	 
	 /**
	  * Learner Postal Address 2  
	  * Required: True
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890#&()/\:._',
	  */
	 @CSVAnnotation(length=50)
     private String learnerPostalAddress2;
	
	 /**
	  * Learner Postal Address 3  
	  * Required: Conditional
	  * The physical address of the provider may be used if the home address of the learner is not available. 
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ-1234567890#&()/\:._',
	  */
	 @CSVAnnotation(length=50)
     private String learnerPostalAddress3;
	 
	 /**
	  * Learner Postal Address Post Code 
	  * Required: True
	  * charSet: 1234567890
	  */
	 @CSVAnnotation(length=4)
	 private String learnerHomeAddressPostCode;

	 /**
	  * Learner Postal Address Post Code 
	  * Required: True
	  * charSet: 1234567890
	  */
	 @CSVAnnotation(length=4)
     private String learnerPostalAddressPostCode;
	 
	 /**
	  * Learner Phone Number
	  * Required: False
	  * charSet: 1234567890
	  */
	 @CSVAnnotation(length=20)
     private String learnerPhoneNumber;
	 
	 /**
	  * Learner Cell Phone Number
	  * Required: False
	  * charSet: 1234567890
	  */
	 @CSVAnnotation(length=20)
     private String learnerCellPhoneNumber;

	 /**
	  * Learner Fax Number
	  * Required: False
	  * charSet: 1234567890
	  */
	 @CSVAnnotation(length=20)
     private String learnerFaxNumber;
	 
	 /**
	  * Learner Email Address
	  * Required: False
	  * charSet: abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRTSUVWXYZ1234567890_.<>-@
	  */
	 @CSVAnnotation(length=50)
     private String learnerEmailAddress;
	 
	 /**
	  * Province Code 
	  * Required: True
	  * Allowed codes
	  * 1 = Western Cape
	  * 2 = Eastern Cape
	  * 3 = Northern Cape
	  * 4 = Free State
	  * 5 = Kwazulu Natal
	  * 6 = North West
	  * 7 = Gauteng
	  * 8 = Mpumalanga
	  * 9 = Limpopo
	  * N = SA National (i.e. in SA but province unspecified)
	  * X = Outside SA
	  */
	 @CSVAnnotation(length=2)
     private String provinceCode;
	 
	 /**
	  * STATSSA Area Code 
	  * Required: True
	  */
	 @CSVAnnotation(length=20)
     private String statssaAreaCode;
	 
	 /**
	  * POPI Act Agree
	  * Required: True
	  * Yes/No
	  */
	 @CSVAnnotation(length=3)
     private String popiActAgree;

	 /**
	  * POPI Act Date 
	  * Required: True
	  */
	 @CSVAnnotation(length = 8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	 private Date popiActDate;

	 /**
	  * Date Stamp 
	  * Required: True
	  */
	 @CSVAnnotation(length = 8, className = Date.class, datePattern=DataExtractService.QCTO_DATE_FORMAT)
	 private Date dateStamp;


	public QCTO01Bean(String sdpCode, String nationalId, String learnerAlternateID, String alternativeIdType,
			String equityCode, String nationalityCode, String homeLanguageCode, String genderCode,
			String citizenResidentStatusCode, String socioeconomicStatusCode, String disabilityStatusCode,
			String disabilityRating, String immigrantStatus, String learnerLastName, String learnerFirstName,
			String learnerMiddleName, String learnerTitle, Date learnerBirthDate, String learnerHomeAddress1,
			String learnerHomeAddress2, String learnerHomeAddress3, String learnerPostalAddress1,
			String learnerPostalAddress2, String learnerPostalAddress3, String learnerHomeAddressPostCode,
			String learnerPostalAddressPostCode, String learnerPhoneNumber, String learnerCellPhoneNumber,
			String learnerFaxNumber, String learnerEmailAddress, String provinceCode, String statssaAreaCode,
			String popiActAgree, Date popiActDate, Date dateStamp) {
		super();

		this.sdpCode = sdpCode;

		this.nationalId = nationalId;

		this.learnerAlternateID = learnerAlternateID;

		this.alternativeIdType = alternativeIdType;

		this.equityCode = equityCode;

		this.nationalityCode = nationalityCode;

		this.homeLanguageCode = homeLanguageCode;

		this.genderCode = genderCode;

		this.citizenResidentStatusCode = citizenResidentStatusCode;

		this.socioeconomicStatusCode = socioeconomicStatusCode;

		this.disabilityStatusCode = disabilityStatusCode;

		this.disabilityRating = disabilityRating;

		this.immigrantStatus = immigrantStatus;

		this.learnerLastName = learnerLastName;
		this.learnerFirstName = learnerFirstName;

		this.learnerMiddleName = learnerMiddleName;
		this.learnerTitle = learnerTitle;

		this.learnerBirthDate = learnerBirthDate;

		this.learnerHomeAddress1 = learnerHomeAddress1;

		this.learnerHomeAddress2 = learnerHomeAddress2;

		this.learnerHomeAddress3 = learnerHomeAddress3;
		this.learnerPostalAddress1 = learnerPostalAddress1;

		this.learnerPostalAddress2 = learnerPostalAddress2;

		this.learnerPostalAddress3 = learnerPostalAddress3;

		this.learnerPostalAddressPostCode = learnerPostalAddressPostCode;

		this.learnerHomeAddressPostCode = learnerHomeAddressPostCode;

		this.learnerPhoneNumber = learnerPhoneNumber;

		this.learnerCellPhoneNumber = learnerCellPhoneNumber;

		this.learnerFaxNumber = learnerFaxNumber;

		this.learnerEmailAddress = learnerEmailAddress;

		this.provinceCode = provinceCode;

		this.statssaAreaCode = statssaAreaCode;

		this.popiActAgree = popiActAgree;

		this.popiActDate = popiActDate;

		this.dateStamp = dateStamp;
	}

	public QCTO01Bean() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getSdpCode() {
		return sdpCode;
	}

	public void setSdpCode(String sdpCode) {
		this.sdpCode = sdpCode;
	}

	public String getNationalId() {
		return nationalId;
	}

	public void setNationalId(String nationalId) {
		this.nationalId = nationalId;
	}

	public String getLearnerAlternateID() {
		return learnerAlternateID;
	}

	public void setLearnerAlternateID(String learnerAlternateID) {
		this.learnerAlternateID = learnerAlternateID;
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

	public String getDisabilityRating() {
		return disabilityRating;
	}

	public void setDisabilityRating(String disabilityRating) {
		this.disabilityRating = disabilityRating;
	}

	public String getImmigrantStatus() {
		return immigrantStatus;
	}

	public void setImmigrantStatus(String immigrantStatus) {
		this.immigrantStatus = immigrantStatus;
	}

	public String getLearnerLastName() {
		return learnerLastName;
	}

	public void setLearnerLastName(String learnerLastName) {
		this.learnerLastName = learnerLastName;
	}

	public String getLearnerFirstName() {
		return learnerFirstName;
	}

	public void setLearnerFirstName(String learnerFirstName) {
		this.learnerFirstName = learnerFirstName;
	}

	public String getLearnerMiddleName() {
		return learnerMiddleName;
	}

	public void setLearnerMiddleName(String learnerMiddleName) {
		this.learnerMiddleName = learnerMiddleName;
	}

	public String getLearnerTitle() {
		return learnerTitle;
	}

	public void setLearnerTitle(String learnerTitle) {
		this.learnerTitle = learnerTitle;
	}

	public Date getLearnerBirthDate() {
		return learnerBirthDate;
	}

	public void setLearnerBirthDate(Date learnerBirthDate) {
		this.learnerBirthDate = learnerBirthDate;
	}

	public String getLearnerHomeAddress1() {
		return learnerHomeAddress1;
	}

	public void setLearnerHomeAddress1(String learnerHomeAddress1) {
		this.learnerHomeAddress1 = learnerHomeAddress1;
	}

	public String getLearnerHomeAddress2() {
		return learnerHomeAddress2;
	}

	public void setLearnerHomeAddress2(String learnerHomeAddress2) {
		this.learnerHomeAddress2 = learnerHomeAddress2;
	}

	public String getLearnerHomeAddress3() {
		return learnerHomeAddress3;
	}

	public void setLearnerHomeAddress3(String learnerHomeAddress3) {
		this.learnerHomeAddress3 = learnerHomeAddress3;
	}

	public String getLearnerPostalAddress1() {
		return learnerPostalAddress1;
	}

	public void setLearnerPostalAddress1(String learnerPostalAddress1) {
		this.learnerPostalAddress1 = learnerPostalAddress1;
	}

	public String getLearnerPostalAddress2() {
		return learnerPostalAddress2;
	}

	public void setLearnerPostalAddress2(String learnerPostalAddress2) {
		this.learnerPostalAddress2 = learnerPostalAddress2;
	}

	public String getLearnerPostalAddress3() {
		return learnerPostalAddress3;
	}

	public void setLearnerPostalAddress3(String learnerPostalAddress3) {
		this.learnerPostalAddress3 = learnerPostalAddress3;
	}

	public String getLearnerPostalAddressPostCode() {
		return learnerPostalAddressPostCode;
	}

	public void setLearnerPostalAddressPostCode(String learnerPostalAddressPostCode) {
		this.learnerPostalAddressPostCode = learnerPostalAddressPostCode;
	}

	public String getLearnerPhoneNumber() {
		return learnerPhoneNumber;
	}

	public void setLearnerPhoneNumber(String learnerPhoneNumber) {
		this.learnerPhoneNumber = learnerPhoneNumber;
	}

	public String getLearnerCellPhoneNumber() {
		return learnerCellPhoneNumber;
	}

	public void setLearnerCellPhoneNumber(String learnerCellPhoneNumber) {
		this.learnerCellPhoneNumber = learnerCellPhoneNumber;
	}

	public String getLearnerFaxNumber() {
		return learnerFaxNumber;
	}

	public void setLearnerFaxNumber(String learnerFaxNumber) {
		this.learnerFaxNumber = learnerFaxNumber;
	}

	public String getLearnerEmailAddress() {
		return learnerEmailAddress;
	}

	public void setLearnerEmailAddress(String learnerEmailAddress) {
		this.learnerEmailAddress = learnerEmailAddress;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getStatssaAreaCode() {
		return statssaAreaCode;
	}

	public void setStatssaAreaCode(String statssaAreaCode) {
		this.statssaAreaCode = statssaAreaCode;
	}

	public String getPopiActAgree() {
		return popiActAgree;
	}

	public void setPopiActAgree(String popiActAgree) {
		this.popiActAgree = popiActAgree;
	}

	public Date getPopiActDate() {
		return popiActDate;
	}

	public void setPopiActDate(Date popiActDate) {
		this.popiActDate = popiActDate;
	}

	public Date getDateStamp() {
		return dateStamp;
	}

	public void setDateStamp(Date dateStamp) {
		this.dateStamp = dateStamp;
	}

	public String getLearnerHomeAddressPostCode() {
		return learnerHomeAddressPostCode;
	}

	public void setLearnerHomeAddressPostCode(String learnerHomeAddressPostCode) {
		this.learnerHomeAddressPostCode = learnerHomeAddressPostCode;
	}



}


