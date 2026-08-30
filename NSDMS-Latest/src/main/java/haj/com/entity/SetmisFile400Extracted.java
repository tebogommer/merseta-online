package haj.com.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.IDataEntity;
import haj.com.service.DataExtractService;

@Entity
@Table(name = "setmis_file_400_extracted")
public class SetmisFile400Extracted implements IDataEntity {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date", length = 19)
	private Date createDate;

	@Column(name = "line_number")
	private String lineNumber;
	
	@CSVAnnotation(length = 15)
	@Column(name = "nationalid")
	private String nationalId;
	
	@CSVAnnotation(length = 20)
	@Column(name = "personalternateid")
	private String personAlternateId;
	
	@CSVAnnotation(length = 3)
	@Column(name = "alternativeidtype")
	private String alternativeIdType;
	
	@CSVAnnotation(length = 10)
	@Column(name = "equitycode")
	private String equityCode;
	
	@CSVAnnotation(length = 3)
	@Column(name = "nationalitycode")
	private String nationalityCode;
	
	@CSVAnnotation(length = 10)
	@Column(name = "homelanguagecode")
	private String homeLanguageCode;
	
	@CSVAnnotation(length = 1)
	@Column(name = "gendercode")
	private String genderCode;
	
	@CSVAnnotation(length = 10)
	@Column(name = "citizenresidentstatuscode")
	private String citizenResidentStatusCode;
	
	@CSVAnnotation(length = 2)
	@Column(name = "filler01")
	private String filler01;
	
	@CSVAnnotation(length = 10)
	@Column(name = "filler02")
	private String filler02;
	
	@CSVAnnotation(length = 45)
	@Column(name = "personlastname")
	private String personLastName;
	
	@CSVAnnotation(length = 26)
	@Column(name = "personfirstname")
	private String personFirstName;
	
	@CSVAnnotation(length = 50)
	@Column(name = "personmiddlename")
	private String personMiddleName;
	
	@CSVAnnotation(length = 10)
	@Column(name = "persontitle")
	private String personTitle;
	
	@CSVAnnotation(length = 8)
	@Column(name = "personbirthdatestring")
	private String personBirthDateString;
//	private Date personBirthDate;
	
	@CSVAnnotation(length = 50)
	@Column(name = "personhomeaddress1")
	private String personHomeAddress1;
	
	@CSVAnnotation(length = 50)
	@Column(name = "personhomeaddress2")
	private String personHomeAddress2;
	
	@CSVAnnotation(length = 50)
	@Column(name = "personhomeaddress3")
	private String personHomeAddress3;
	
	@CSVAnnotation(length = 50)
	@Column(name = "personpostaladdress1")
	private String personPostalAddress1;
	
	@CSVAnnotation(length = 50)
	@Column(name = "personpostaladdress2")
	private String personPostalAddress2;
	
	@CSVAnnotation(length = 50)
	@Column(name = "personpostaladdress3")
	private String personPostalAddress3;
	
	@CSVAnnotation(length = 4)
	@Column(name = "personhomeaddrpostalcode")
	private String personHomeAddrPostalCode;
	
	@CSVAnnotation(length = 4)
	@Column(name = "personpostaladdrpostalcode")
	private String personPostalAddrPostalCode;
	
	@CSVAnnotation(length = 20)
	@Column(name = "personphonenumber")
	private String personPhoneNumber;
	
	@CSVAnnotation(length = 20)
	@Column(name = "personcellphonenumber")
	private String personCellPhoneNumber;
	
	@CSVAnnotation(length = 20)
	@Column(name = "personfaxnumber")
	private String personFaxNumber;
	
	@CSVAnnotation(length = 50)
	@Column(name = "personemailaddress")
	private String personEmailAddress;
	
	@CSVAnnotation(length = 2)
	@Column(name = "provincecode")
	private String provinceCode;
	
	@CSVAnnotation(length = 20)
	@Column(name = "providercode")
	private String providerCode;
	
	@CSVAnnotation(length = 10)
	@Column(name = "provideretqeid")
	private String providerETQEId;
	
	@CSVAnnotation(length = 45)
	@Column(name = "personpreviouslastname")
	private String personPreviousLastname;
	
	@CSVAnnotation(length = 20)
	@Column(name = "personpreviousalternateid")
	private String personPreviousAlternateId;
	
	@CSVAnnotation(length = 3)
	@Column(name = "personpreviousalternativeidtype")
	private String personPreviousAlternativeIdType;
	
	@CSVAnnotation(length = 20)
	@Column(name = "personpreviousprovidercode")
	private String personPreviousProviderCode;
	
	@CSVAnnotation(length = 10)
	@Column(name = "personpreviousprovideretqeid")
	private String personPreviousProviderETQEId;
	
	@CSVAnnotation(length = 2)
	@Column(name = "seeingratingid")
	private String seeingRatingId;
	
	@CSVAnnotation(length = 2)
	@Column(name = "hearingratingid")
	private String hearingRatingId;
	
	@CSVAnnotation(length = 2)
	@Column(name = "walkingratingid")
	private String walkingRatingId;

	@CSVAnnotation(length = 2)
	@Column(name = "rememberingratingid")
	private String rememberingRatingId;
	
	@CSVAnnotation(length = 2)
	@Column(name = "communicationratingid")
	private String communicationRatingId;
	
	@CSVAnnotation(length = 2)
	@Column(name = "selfcareratingid")
	private String selfcareRatingId;
	
	@CSVAnnotation(length = 20)
	@Column(name = "lastschoolemisno")
	private String lastSchoolEMISNo;
	
	@CSVAnnotation(length = 4)
	@Column(name = "lastschoolyearstring")
	private String lastSchoolYearString;
//	private Date lastSchoolYear;
	
	@CSVAnnotation(length = 20)
	@Column(name = "statssaareacode")
	private String sTATSSAAreaCode;
	
	@CSVAnnotation(length = 2)
	@Column(name = "popiactstatusid")
	private String pOPIActStatusID;
	
	@CSVAnnotation(length = 8)
	@Column(name = "popiactstatusdatestring")
	private String pOPIActStatusDateString;
//	private Date pOPIActStatusDate;
	
	@CSVAnnotation(length = 8)
	@Column(name = "datestampstring")
	private String dateStampString;
//	private Date dateStamp;

	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetmisFile400Extracted other = (SetmisFile400Extracted) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	/**
	 * Gets the id.
	 *
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * Sets the id.
	 *
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * Gets the creates the date.
	 *
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}

	/**
	 * Sets the creates the date.
	 *
	 * @param createDate
	 *            the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getLineNumber() {
		return lineNumber;
	}

	public void setLineNumber(String lineNumber) {
		this.lineNumber = lineNumber;
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

	public String getPersonBirthDateString() {
		return personBirthDateString;
	}

	public void setPersonBirthDateString(String personBirthDateString) {
		this.personBirthDateString = personBirthDateString;
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

	public String getLastSchoolYearString() {
		return lastSchoolYearString;
	}

	public void setLastSchoolYearString(String lastSchoolYearString) {
		this.lastSchoolYearString = lastSchoolYearString;
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

	public String getpOPIActStatusDateString() {
		return pOPIActStatusDateString;
	}

	public void setpOPIActStatusDateString(String pOPIActStatusDateString) {
		this.pOPIActStatusDateString = pOPIActStatusDateString;
	}

	public String getDateStampString() {
		return dateStampString;
	}

	public void setDateStampString(String dateStampString) {
		this.dateStampString = dateStampString;
	}
}