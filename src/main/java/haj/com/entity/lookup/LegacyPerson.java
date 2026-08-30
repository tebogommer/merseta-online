package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.framework.AbstractLookup;

/**
 * Excel name: Person.xlsx
 * Tab Name: Person
 *
 * @author Techfinium
 */
@Entity
@Table(
		  name = "legacy_person"		
		, indexes = {
			@Index(name = "personId", columnList = "id_no"),
			@Index(name = "personAlternativeId", columnList = "alternate_id_no")
})

// Valid RSA ID Number - validIdNumber
@LegacyReportingAnnotation(name = "Total Valid RSA ID Numbers", query = "select count(o) from LegacyPerson o where o.validIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid RSA ID Numbers", query = "select count(o) from LegacyPerson o where (o.validIdNumber = false or o.validIdNumber is null)", key = "count")
// Appears on home affairs files - appearsOnHomeAffairsData
@LegacyReportingAnnotation(name = "Total RSA ID Numbers on Home Affairs", query = "select count(o) from LegacyPerson o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total RSA ID Numbers not on Home Affairs", query = "select count(o) from LegacyPerson o where (o.appearsOnHomeAffairsData = false or o.appearsOnHomeAffairsData is null)", key = "count")
// Person Information Match on - SARS fullNamesMatchesOnHomeAffairsData
@LegacyReportingAnnotation(name = "Total Person Details Matching Home Affairs", query = "select count(o) from LegacyPerson o where o.fullNamesMatchesOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Person Details Not Matching Home Affairs", query = "select count(o) from LegacyPerson o where o.fullNamesMatchesOnHomeAffairsData = false", key = "count")
// tables
@LegacyReportingAnnotation(name = "Total Invalid RSA ID Numbers", query = "select o from LegacyPerson o where o.validIdNumber = false", key = "Invalid RSA ID Numbers", returnType = LegacyPerson.class, singleResult = false)
@LegacyReportingAnnotation(name = "Total RSA ID Numbers not on Home Affairs", query = "select o from LegacyPerson o where o.appearsOnHomeAffairsData = false", key = "RSA ID Numbers not on Home Affairs", returnType = LegacyPerson.class, singleResult = false)
@LegacyReportingAnnotation(name = "Total Person Details Not Matching Home Affairs", query = "select o from LegacyPerson o where o.fullNamesMatchesOnHomeAffairsData = false", key = "Person Details Not Matching", returnType = LegacyPerson.class, singleResult = false)

public class LegacyPerson extends AbstractLookup {
	/*
	 * Appears on home affairs files - appearsOnHomeAffairsData
	 */
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Accreditation. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * PN: 1
	 * PersonId 
	 */
	@CSVAnnotation(name = "PersonId", className = String.class)
	@Column(name = "person_id"  )
	private String personId;
	
	/* 
	 * PN: 2
	 * TitleDesc
	 */
	@CSVAnnotation(name = "TitleDesc", className = String.class)
	@Column(name = "title_desc"  )
	private String titleDesc;
	
	
	/*
	 * PN: 3
	 * Firstname 
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name"  )
	private String firstName;
	
	/* 
	 * PN: 4
	 * MiddleNames
	 */
	@CSVAnnotation(name = "MiddleNames", className = String.class)
	@Column(name = "middle_names"  )
	private String middleNames;
	
	
	/*
	 * PN: 5
	 * Surname 
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname"  )
	private String surname;
	
	/* 
	 * PN: 6
	 * MaidenName
	 */
	@CSVAnnotation(name = "MaidenName", className = String.class)
	@Column(name = "maiden_name"  )
	private String maidenName;
	
	
	/*
	 * PN: 7
	 * Initials 
	 */
	@CSVAnnotation(name = "Initials", className = String.class)
	@Column(name = "initials"  )
	private String initials;
	
	/* 
	 * PN: 8
	 * IDNo
	 */
	@CSVAnnotation(name = "IDNo", className = String.class)
	@Column(name = "id_no"  )
	private String idNo;
	
	/*
	 * PN: 9
	 * AlternateIDNo 
	 */
	@CSVAnnotation(name = "AlternateIDNo", className = String.class)
	@Column(name = "alternate_id_no"  )
	private String alternateIDNo;
	
	/* 
	 * PN: 10
	 * AlternateIDType
	 */
	@CSVAnnotation(name = "AlternateIDType", className = String.class)
	@Column(name = "alternate_id_type"  )
	private String alternateIDType;
	
	
	/*
	 * PN: 11
	 * DateOfBirth 
	 */
	@CSVAnnotation(name = "DateOfBirth", className = String.class)
	@Column(name = "date_of_birth"  )
	private String dateOfBirth;
	
	/* 
	 * PN: 12
	 * GenderDesc
	 */
	@CSVAnnotation(name = "GenderDesc", className = String.class)
	@Column(name = "gender_desc"  )
	private String genderDesc;
	
	
	/*
	 * PN: 13
	 * EquityDesc 
	 */
	@CSVAnnotation(name = "EquityDesc", className = String.class)
	@Column(name = "equity_desc"  )
	private String equityDesc;
	
	/* 
	 * PN: 14
	 * NationalityDesc
	 */
	@CSVAnnotation(name = "NationalityDesc", className = String.class)
	@Column(name = "nationality_desc"  )
	private String nationalityDesc;
	
	/* 
	 * PN: 15
	 * LangDesc
	 */
	@CSVAnnotation(name = "LangDesc", className = String.class)
	@Column(name = "lang_desc"  )
	private String langDesc;
	
	/* 
	 * PN: 16
	 * CitizenStatusDesc
	 */
	@CSVAnnotation(name = "CitizenStatusDesc", className = String.class)
	@Column(name = "citizen_status_desc"  )
	private String citizenStatusDesc;
	
	/* 
	 * PN: 17
	 * SocioEcoStatusDesc
	 */
	@CSVAnnotation(name = "SocioEcoStatusDesc", className = String.class)
	@Column(name = "socio_ecoStatus_desc"  )
	private String socioEcoStatusDesc;
	
	/* 
	 * PN: 18
	 * DisabilityDesc
	 */
	@CSVAnnotation(name = "DisabilityDesc", className = String.class)
	@Column(name = "disability_desc"  )
	private String disabilityDesc;
	
	/* 
	 * PN: 19
	 * GeographicAreaDesc
	 */
	@CSVAnnotation(name = "GeographicAreaDesc", className = String.class)
	@Column(name = "geographic_area_desc"  )
	private String geographicAreaDesc;
	
	/* 
	 * PN: 20
	 * PhoneNoHome
	 */
	@CSVAnnotation(name = "PhoneNoHome", className = String.class)
	@Column(name = "phone_no_home"  )
	private String phoneNoHome;
	
	
	/* 
	 * PN: 21
	 * PhoneNoBusiness
	 */
	@CSVAnnotation(name = "PhoneNoBusiness", className = String.class)
	@Column(name = "phone_no_business"  )
	private String phoneNoBusiness;
	
	/* 
	 * PN: 22
	 * AlternatePhoneNo
	 */
	@CSVAnnotation(name = "CelAlternatePhoneNolNo", className = String.class)
	@Column(name = "alternate_phone_no"  )
	private String alternatePhoneNo;
	
	/* 
	 * PN: 23
	 * CellNo
	 */
	@CSVAnnotation(name = "CellNo", className = String.class)
	@Column(name = "cell_no"  )
	private String cellNo;
	
	/* 
	 * PN: 24
	 * FaxNo
	 */
	@CSVAnnotation(name = "FaxNo", className = String.class)
	@Column(name = "fax_no"  )
	private String faxNo;
	
	/* 
	 * PN: 25
	 * EMail
	 */
	@CSVAnnotation(name = "EMail", className = String.class)
	@Column(name = "email"  )
	private String email;
	
	/* 
	 * PN: 26
	 * EthnicGroup
	 */
	@CSVAnnotation(name = "EthnicGroup", className = String.class)
	@Column(name = "ethnic_group"  )
	private String ethnicGroup;
	
	/* 
	 * PN: 27
	 * PersonStatusEffectiveDate
	 */
	@CSVAnnotation(name = "PersonStatusEffectiveDate", className = String.class)
	@Column(name = "person_status_effective_date"  )
	private String personStatusEffectiveDate;
	
	/* 
	 * PN: 28
	 * DateModified
	 */
	@CSVAnnotation(name = "DateModified", className = String.class)
	@Column(name = "date_modified"  )
	private String dateModified;
	
	/* 
	 * PN: 29
	 * DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_created"  )
	private String dateCreated;
	
	/* 
	 * PN: 30
	 * lkpHighestEducationDesc
	 */
	@CSVAnnotation(name = "lkpHighestEducationDesc", className = String.class)
	@Column(name = "lkp_highest_education_desc"  )
	private String lkpHighestEducationDesc;
	
	/* 
	 * PN: 31
	 * MagisterialDistrict
	 */
	@CSVAnnotation(name = "MagisterialDistrict", className = String.class)
	@Column(name = "magisterial_district"  )
	private String magisterialDistrict;
	
	/* 
	 * PN: 32
	 * MaritalStatus
	 */
	@CSVAnnotation(name = "MaritalStatus", className = String.class)
	@Column(name = "marital_status"  )
	private String maritalStatus;
	
	/* 
	 * PN: 33
	 * IsDisability
	 */
	@CSVAnnotation(name = "IsDisability", className = String.class)
	@Column(name = "is_disability"  )
	private String isDisability;
	
	/* 
	 * PN: 34
	 * DisabilityStatusOtherDescription
	 */
	@CSVAnnotation(name = "DisabilityStatusOtherDescription", className = String.class)
	@Column(name = "disability_status_other_description"  )
	private String disabilityStatusOtherDescription;
	
	/* 
	 * PN: 35
	 * SeeingRatingID
	 */
	@CSVAnnotation(name = "SeeingRatingID", className = String.class)
	@Column(name = "seeing_rating_id"  )
	private String seeingRatingID;
	
	/* 
	 * PN: 36
	 * HearingRatingID
	 */
	@CSVAnnotation(name = "HearingRatingID", className = String.class)
	@Column(name = "hearing_rating_id"  )
	private String hearingRatingID;
	
	/* 
	 * PN: 37
	 * CommunicatingRatingID
	 */
	@CSVAnnotation(name = "CommunicatingRatingID", className = String.class)
	@Column(name = "communicating_rating_id"  )
	private String communicatingRatingID;
	
	/* 
	 * PN: 38
	 * WalkingRatingID
	 */
	@CSVAnnotation(name = "WalkingRatingID", className = String.class)
	@Column(name = "walking_rating_id"  )
	private String walkingRatingid;
	
	/* 
	 * PN: 39
	 * RememberingRatingID
	 */
	@CSVAnnotation(name = "RememberingRatingID", className = String.class)
	@Column(name = "remembering_rating_id"  )
	private String rememberingRatingID;
	
	/* 
	 * PN: 40
	 * SETAStatus
	 */
	@CSVAnnotation(name = "SelfcareRatingID", className = String.class)
	@Column(name = "selfcare_rating_id"  )
	private String selfcareRatingID;
	
	@Column(name = "valid_id_number")
	private Boolean validIdNumber;
	
	@Column(name = "valid_alternate_id_no")
	private Boolean validAlternateIdNo;
	
	@Column(name = "appears_on_home_affairs_data")
	private Boolean appearsOnHomeAffairsData;
	
	@Column(name = "full_names_matches_on_home_affairs_data")
	private Boolean fullNamesMatchesOnHomeAffairsData;
	
	@Column(name = "processed")
	private Boolean processed;
	
	/**
	 * Instantiates a new Accreditation.
	 */
	public LegacyPerson() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		LegacyPerson other = (LegacyPerson) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPersonId() {
		return personId;
	}

	public void setPersonId(String personId) {
		this.personId = personId;
	}

	public String getTitleDesc() {
		return titleDesc;
	}

	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleNames() {
		return middleNames;
	}

	public void setMiddleNames(String middleNames) {
		this.middleNames = middleNames;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getMaidenName() {
		return maidenName;
	}

	public void setMaidenName(String maidenName) {
		this.maidenName = maidenName;
	}

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAlternateIDNo() {
		return alternateIDNo;
	}

	public void setAlternateIDNo(String alternateIDNo) {
		this.alternateIDNo = alternateIDNo;
	}

	public String getAlternateIDType() {
		return alternateIDType;
	}

	public void setAlternateIDType(String alternateIDType) {
		this.alternateIDType = alternateIDType;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getGenderDesc() {
		return genderDesc;
	}

	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}

	public String getEquityDesc() {
		return equityDesc;
	}

	public void setEquityDesc(String equityDesc) {
		this.equityDesc = equityDesc;
	}

	public String getNationalityDesc() {
		return nationalityDesc;
	}

	public void setNationalityDesc(String nationalityDesc) {
		this.nationalityDesc = nationalityDesc;
	}

	public String getLangDesc() {
		return langDesc;
	}

	public void setLangDesc(String langDesc) {
		this.langDesc = langDesc;
	}

	public String getCitizenStatusDesc() {
		return citizenStatusDesc;
	}

	public void setCitizenStatusDesc(String citizenStatusDesc) {
		this.citizenStatusDesc = citizenStatusDesc;
	}

	public String getSocioEcoStatusDesc() {
		return socioEcoStatusDesc;
	}

	public void setSocioEcoStatusDesc(String socioEcoStatusDesc) {
		this.socioEcoStatusDesc = socioEcoStatusDesc;
	}

	public String getDisabilityDesc() {
		return disabilityDesc;
	}

	public void setDisabilityDesc(String disabilityDesc) {
		this.disabilityDesc = disabilityDesc;
	}

	public String getGeographicAreaDesc() {
		return geographicAreaDesc;
	}

	public void setGeographicAreaDesc(String geographicAreaDesc) {
		this.geographicAreaDesc = geographicAreaDesc;
	}

	public String getPhoneNoHome() {
		return phoneNoHome;
	}

	public void setPhoneNoHome(String phoneNoHome) {
		this.phoneNoHome = phoneNoHome;
	}

	public String getPhoneNoBusiness() {
		return phoneNoBusiness;
	}

	public void setPhoneNoBusiness(String phoneNoBusiness) {
		this.phoneNoBusiness = phoneNoBusiness;
	}

	public String getAlternatePhoneNo() {
		return alternatePhoneNo;
	}

	public void setAlternatePhoneNo(String alternatePhoneNo) {
		this.alternatePhoneNo = alternatePhoneNo;
	}

	public String getCellNo() {
		return cellNo;
	}

	public void setCellNo(String cellNo) {
		this.cellNo = cellNo;
	}

	public String getFaxNo() {
		return faxNo;
	}

	public void setFaxNo(String faxNo) {
		this.faxNo = faxNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEthnicGroup() {
		return ethnicGroup;
	}

	public void setEthnicGroup(String ethnicGroup) {
		this.ethnicGroup = ethnicGroup;
	}

	public String getPersonStatusEffectiveDate() {
		return personStatusEffectiveDate;
	}

	public void setPersonStatusEffectiveDate(String personStatusEffectiveDate) {
		this.personStatusEffectiveDate = personStatusEffectiveDate;
	}

	public String getDateModified() {
		return dateModified;
	}

	public void setDateModified(String dateModified) {
		this.dateModified = dateModified;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLkpHighestEducationDesc() {
		return lkpHighestEducationDesc;
	}

	public void setLkpHighestEducationDesc(String lkpHighestEducationDesc) {
		this.lkpHighestEducationDesc = lkpHighestEducationDesc;
	}

	public String getMagisterialDistrict() {
		return magisterialDistrict;
	}

	public void setMagisterialDistrict(String magisterialDistrict) {
		this.magisterialDistrict = magisterialDistrict;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getIsDisability() {
		return isDisability;
	}

	public void setIsDisability(String isDisability) {
		this.isDisability = isDisability;
	}

	public String getDisabilityStatusOtherDescription() {
		return disabilityStatusOtherDescription;
	}

	public void setDisabilityStatusOtherDescription(String disabilityStatusOtherDescription) {
		this.disabilityStatusOtherDescription = disabilityStatusOtherDescription;
	}

	public String getSeeingRatingID() {
		return seeingRatingID;
	}

	public void setSeeingRatingID(String seeingRatingID) {
		this.seeingRatingID = seeingRatingID;
	}

	public String getHearingRatingID() {
		return hearingRatingID;
	}

	public void setHearingRatingID(String hearingRatingID) {
		this.hearingRatingID = hearingRatingID;
	}

	public String getCommunicatingRatingID() {
		return communicatingRatingID;
	}

	public void setCommunicatingRatingID(String communicatingRatingID) {
		this.communicatingRatingID = communicatingRatingID;
	}

	public String getWalkingRatingid() {
		return walkingRatingid;
	}

	public void setWalkingRatingid(String walkingRatingid) {
		this.walkingRatingid = walkingRatingid;
	}

	public String getRememberingRatingID() {
		return rememberingRatingID;
	}

	public void setRememberingRatingID(String rememberingRatingID) {
		this.rememberingRatingID = rememberingRatingID;
	}

	public String getSelfcareRatingID() {
		return selfcareRatingID;
	}

	public void setSelfcareRatingID(String selfcareRatingID) {
		this.selfcareRatingID = selfcareRatingID;
	}

	public Boolean getValidAlternateIdNo() {
		return validAlternateIdNo;
	}

	public void setValidAlternateIdNo(Boolean validAlternateIdNo) {
		this.validAlternateIdNo = validAlternateIdNo;
	}

	public Boolean getValidIdNumber() {
		return validIdNumber;
	}

	public void setValidIdNumber(Boolean validIdNumber) {
		this.validIdNumber = validIdNumber;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Boolean getFullNamesMatchesOnHomeAffairsData() {
		return fullNamesMatchesOnHomeAffairsData;
	}

	public void setFullNamesMatchesOnHomeAffairsData(Boolean fullNamesMatchesOnHomeAffairsData) {
		this.fullNamesMatchesOnHomeAffairsData = fullNamesMatchesOnHomeAffairsData;
	}


	

}
