package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.Company;
import haj.com.entity.OfoCodes;
import haj.com.framework.AbstractLookup;
import haj.com.service.lookup.HomeAffairsService;

/**
 * Excel name: Bursary.xlsx Tab Name: Bursary
 */
@Entity
@Table(name = "legacy_bursary")
// site info
@LegacyReportingAnnotation(name = "Total Bursary Linked to Site", query = "select count(o) from LegacyBursary o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Bursary not Linked to Site", query = "select count(o) from LegacyBursary o where o.legacyOrganisationSites = false", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyBursary o where o.legacyOrganisationSites = false", key = "Bursary not Linked Site", returnType = LegacyBursary.class, singleResult = false)
// id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacyBursary o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacyBursary o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyBursary o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyBursary o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacyBursary o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacyBursary o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyBursary o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacyBursary.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyBursary o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyBursary.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyBursary o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacyBursary.class, singleResult = false)
// qualification info
@LegacyReportingAnnotation(name = "Total Linked Qualification", query = "select count(o) from LegacyBursary o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Qualification", query = "select count(o) from LegacyBursary o where o.qualification is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyBursary o where o.qualification is null", key = "Non Linked Qualifications", returnType = LegacyBursary.class, singleResult = false)
// ofoCodes info
@LegacyReportingAnnotation(name = "Total Linked Ofo Codes", query = "select count(o) from LegacyBursary o where o.ofoCodes is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Ofo Codes", query = "select count(o) from LegacyBursary o where o.ofoCodes is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyBursary o where o.ofoCodes is null", key = "Non Linked Ofo Codes", returnType = LegacyBursary.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacyBursary o where o.description = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacyBursary.class, singleResult = false)
//Completed
@LegacyReportingAnnotation(name = "Total Completed Status on Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Completed' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Completed Status not on Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Completed' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Completed Status not on Home Affairs", query = "select o from LegacyBursary o where o.description = 'Completed' and  o.appearsOnHomeAffairsData = false", returnType = LegacyBursary.class, singleResult = false)
//NULL
@LegacyReportingAnnotation(name = "Total NULL Status on Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'NULL' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total NULL Status not on Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'NULL' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "NULL Status not on Home Affairs", query = "select o from LegacyBursary o where o.description = 'NULL' and  o.appearsOnHomeAffairsData = false", returnType = LegacyBursary.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacyBursary o where o.description = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacyBursary.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacyBursary o where o.description = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacyBursary o where o.description = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacyBursary o where o.description = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyBursary.class, singleResult = false)
//Completed
@LegacyReportingAnnotation(name = "Total Completed Status on Person File", query = "select count(o) from LegacyBursary o where o.description = 'Completed' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Completed Status not on Person File", query = "select count(o) from LegacyBursary o where o.description = 'Completed' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Completed Status not on Person File", query = "select o from LegacyBursary o where o.description = 'Completed' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyBursary.class, singleResult = false)
//NULL
@LegacyReportingAnnotation(name = "Total NULL Status on Person File", query = "select count(o) from LegacyBursary o where o.description = 'NULL' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total NULL Status not on Person File", query = "select count(o) from LegacyBursary o where o.description = 'NULL' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "NULL Status not on Person File", query = "select o from LegacyBursary o where o.description = 'NULL' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyBursary.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacyBursary o where o.description = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacyBursary o where o.description = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacyBursary o where o.description = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyBursary.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacyBursary o where o.description = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyBursary.class, singleResult = false)
//Completed
@LegacyReportingAnnotation(name = "Total Completed Status on Person File and Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Completed' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Completed Status not on Person File and Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Completed' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Completed Status not on Person File and Home Affairs", query = "select o from LegacyBursary o where o.description = 'Completed' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyBursary.class, singleResult = false)
//NULL
@LegacyReportingAnnotation(name = "Total NULL Status on Person File and Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'NULL' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total NULL Status not on Person File and Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'NULL' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "NULL Status not on Person File and Home Affairs", query = "select o from LegacyBursary o where o.description = 'NULL' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyBursary.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacyBursary o where o.description = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacyBursary o where o.description = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyBursary.class, singleResult = false)
public class LegacyBursary extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	// @CSVAnnotation(name = "Name of row on excel", className = String.class)
	// @Column(name = "keep to standard")
	// private String keep to standard;

	/*
	 * Filed Sizes: Book: @Column(name = "dttc_id" , columnDefinition = "LONGTEXT")
	 * 500 or so chataers @Column(name = "dttc_id" , length = 500)
	 */

	/*
	 * PN: 1 ID
	 */
	@CSVAnnotation(name = "ID", className = String.class)
	@Column(name = "id2")
	private String idTwo;

	/*
	 * PN: 2 IDNo
	 */
	@CSVAnnotation(name = "IDNo", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "id_no")
	private String idNo;

	@CSVAnnotation(name = "IDNo", className = String.class, lookupField = "validRsaIdNumber")
	@Transient
	private String idNoTransient;

	/*
	 * PN: 3 Firstname
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name")
	private String firstName;

	/*
	 * PN: 4 MiddleNames
	 */
	@CSVAnnotation(name = "MiddleNames", className = String.class)
	@Column(name = "middle_names")
	private String middleNames;

	/*
	 * PN: 5 Surname
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname")
	private String surname;

	/*
	 * PN: 6 StartDate
	 */
	@CSVAnnotation(name = "StartDate", className = String.class)
	@Column(name = "start_date")
	private String startDate;

	/*
	 * PN: 7 EndDate
	 */
	@CSVAnnotation(name = "EndDate", className = String.class)
	@Column(name = "end_date")
	private String endDate;

	/*
	 * PN: 8 LearningArea
	 */
	@CSVAnnotation(name = "LearningArea", className = String.class)
	@Column(name = "learning_area")
	private String learningArea;

	/*
	 * PN: 9 ApplicationDate
	 */
	@CSVAnnotation(name = "ApplicationDate", className = String.class)
	@Column(name = "application_date")
	private String applicationDate;

	/*
	 * PN: 10 RegistrationDate
	 */
	@CSVAnnotation(name = "RegistrationDate", className = String.class)
	@Column(name = "registration_date")
	private String registrationDate;

	/*
	 * PN: 11 CompletionDate
	 */
	@CSVAnnotation(name = "CompletionDate", className = String.class)
	@Column(name = "completion_date")
	private String completionDate;

	/*
	 * PN: 12 OFOCode
	 */
	@CSVAnnotation(name = "OFOCode", className = String.class)
	@Column(name = "ofo_code")
	private String ofoCode;

	/*
	 * PN: 13 SkillPriority
	 */
	@CSVAnnotation(name = "SkillPriority", className = String.class)
	@Column(name = "skill_priority")
	private String skillPriority;

	/*
	 * PN: 14 SkillPriority
	 */
	@CSVAnnotation(name = "Description", className = String.class)
	@Column(name = "description")
	private String description;

	/*
	 * PN: 15 EmployerSDL
	 */
	@CSVAnnotation(name = "EmployerSDL", className = String.class, lookupField = "legacyOrganisationSites")
	@Column(name = "employer_sdl")
	private String employerSdl;

	@Column(name = "legacy_organisation_sites")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLegacyOrganisationSites", paramClass = String.class)
	private Boolean legacyOrganisationSites;

	/*
	 * PN: 16 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "oprganisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 17 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 18 Provider
	 */
	@CSVAnnotation(name = "Provider", className = String.class)
	@Column(name = "provider")
	private String provider;

	/*
	 * PN: 19 OtherProvider
	 */
	@CSVAnnotation(name = "OtherProvider", className = String.class)
	@Column(name = "other_provider")
	private String otherProvider;

	/*
	 * PN: 20 ProviderType
	 */
	@CSVAnnotation(name = "ProviderType", className = String.class)
	@Column(name = "provider_type")
	private String providerType;

	/*
	 * PN: 21 NQFLevel
	 */
	@CSVAnnotation(name = "NQFLevel", className = String.class)
	@Column(name = "nqf_level")
	private String nqfLevel;

	/*
	 * PN: 22 FundingStatus
	 */
	@CSVAnnotation(name = "FundingStatus", className = String.class)
	@Column(name = "funding_status")
	private String fundingStatus;

	/*
	 * PN: 23 DGaTg
	 */
	@CSVAnnotation(name = "DGaTg", className = String.class)
	@Column(name = "dg_a_tg")
	private String dgaTg;

	/*
	 * PN: 24 Amount
	 */
	@CSVAnnotation(name = "Amount", className = String.class)
	@Column(name = "amount")
	private String amount;

	/*
	 * PN: 25 DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_created")
	private String dateCreated;

	/*
	 * PN: 26 DateUpdated
	 */
	@CSVAnnotation(name = "DateUpdated", className = String.class)
	@Column(name = "date_updated")
	private String dateUpdated;

	/*
	 * PN: 27 SocioEcoStatusDesc
	 */
	@CSVAnnotation(name = "SocioEcoStatusDesc", className = String.class)
	@Column(name = "socio_eco_status_desc")
	private String socioEcoStatusDesc;

	/*
	 * PN: 28 PartnershipSDL
	 */
	@CSVAnnotation(name = "PartnershipSDL", className = String.class)
	@Column(name = "partnership_sdl")
	private String partnershipSdl;

	/*
	 * PN: 29 partnerLegalName
	 */
	@CSVAnnotation(name = "partnerLegalName", className = String.class)
	@Column(name = "partner_legal_name")
	private String partnerLegalName;

	/*
	 * PN: 30 partnerTradeName
	 */
	@CSVAnnotation(name = "partnerTradeName", className = String.class)
	@Column(name = "partner_trade_name")
	private String partnerTradeName;

	/*
	 * PN: 31 OFOCodeID
	 */
	@CSVAnnotation(name = "OFOCode", className = String.class, lookupField = "ofoCodes")
	@Column(name = "ofo_code_id")
	private String ofoCodeId;

	/*
	 * PN: 32 SAQA ID
	 */
	@CSVAnnotation(name = "SAQA_ID", className = String.class, lookupField = "qualification")
	@Column(name = "saqa_id")
	private String saqaId;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findQualification", paramClass = String.class)
	private Qualification qualification;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findOfoCodeAndSpecialisation", paramClass = String.class)
	private OfoCodes ofoCodes;
	
	@Column(name = "created_company_learner")
	private Boolean createdCompanyLearner;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer_id", nullable = true)
	private Company employer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;

	public LegacyBursary() {
		super();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		LegacyBursary other = (LegacyBursary) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		return true;
	}

	/* Getters and setters */

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

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
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

	public String getLearningArea() {
		return learningArea;
	}

	public void setLearningArea(String learningArea) {
		this.learningArea = learningArea;
	}

	public String getApplicationDate() {
		return applicationDate;
	}

	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getCompletionDate() {
		return completionDate;
	}

	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getSkillPriority() {
		return skillPriority;
	}

	public void setSkillPriority(String skillPriority) {
		this.skillPriority = skillPriority;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getOrganisationNameLegal() {
		return organisationNameLegal;
	}

	public void setOrganisationNameLegal(String organisationNameLegal) {
		this.organisationNameLegal = organisationNameLegal;
	}

	public String getOrganisationNameTrade() {
		return organisationNameTrade;
	}

	public void setOrganisationNameTrade(String organisationNameTrade) {
		this.organisationNameTrade = organisationNameTrade;
	}

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public String getOtherProvider() {
		return otherProvider;
	}

	public void setOtherProvider(String otherProvider) {
		this.otherProvider = otherProvider;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getNqfLevel() {
		return nqfLevel;
	}

	public void setNqfLevel(String nqfLevel) {
		this.nqfLevel = nqfLevel;
	}

	public String getFundingStatus() {
		return fundingStatus;
	}

	public void setFundingStatus(String fundingStatus) {
		this.fundingStatus = fundingStatus;
	}

	public String getDgaTg() {
		return dgaTg;
	}

	public void setDgaTg(String dgaTg) {
		this.dgaTg = dgaTg;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateUpdated() {
		return dateUpdated;
	}

	public void setDateUpdated(String dateUpdated) {
		this.dateUpdated = dateUpdated;
	}

	public String getSocioEcoStatusDesc() {
		return socioEcoStatusDesc;
	}

	public void setSocioEcoStatusDesc(String socioEcoStatusDesc) {
		this.socioEcoStatusDesc = socioEcoStatusDesc;
	}

	public String getPartnerLegalName() {
		return partnerLegalName;
	}

	public void setPartnerLegalName(String partnerLegalName) {
		this.partnerLegalName = partnerLegalName;
	}

	public String getPartnerTradeName() {
		return partnerTradeName;
	}

	public void setPartnerTradeName(String partnerTradeName) {
		this.partnerTradeName = partnerTradeName;
	}

	public String getIdTwo() {
		return idTwo;
	}

	public void setIdTwo(String idTwo) {
		this.idTwo = idTwo;
	}

	public String getEmployerSdl() {
		return employerSdl;
	}

	public void setEmployerSdl(String employerSdl) {
		this.employerSdl = employerSdl;
	}

	public String getPartnershipSdl() {
		return partnershipSdl;
	}

	public void setPartnershipSdl(String partnershipSdl) {
		this.partnershipSdl = partnershipSdl;
	}

	public String getOfoCodeId() {
		return ofoCodeId;
	}

	public void setOfoCodeId(String ofoCodeId) {
		this.ofoCodeId = ofoCodeId;
	}

	public String getSaqaId() {
		return saqaId;
	}

	public void setSaqaId(String saqaId) {
		this.saqaId = saqaId;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public String getIdNoTransient() {
		return idNoTransient;
	}

	public void setIdNoTransient(String idNoTransient) {
		this.idNoTransient = idNoTransient;
	}

	public Boolean getLegacyOrganisationSites() {
		return legacyOrganisationSites;
	}

	public void setLegacyOrganisationSites(Boolean legacyOrganisationSites) {
		this.legacyOrganisationSites = legacyOrganisationSites;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public Boolean getCreatedCompanyLearner() {
		return createdCompanyLearner;
	}

	public void setCreatedCompanyLearner(Boolean createdCompanyLearner) {
		this.createdCompanyLearner = createdCompanyLearner;
	}

	public Company getEmployer() {
		return employer;
	}

	public Company getCompany() {
		return company;
	}

	public void setEmployer(Company employer) {
		this.employer = employer;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
