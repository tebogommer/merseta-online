package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

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
import haj.com.entity.TrainingProviderApplication;
import haj.com.framework.AbstractLookup;
import haj.com.service.lookup.HomeAffairsService;

/**
 * TO BE DELETED
 * 
 * Excel name: Skills Programme.xlsx Tab Name: Skills Programme
 */
@Entity
@Table(name = "legacy_skills_programme")
//site info
@LegacyReportingAnnotation(name = "Total Apprenticeship Linked to Site", query = "select count(o) from LegacySkillsProgramme o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Apprenticeship not Linked to Site", query = "select count(o) from LegacySkillsProgramme o where o.legacyOrganisationSites = false", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySkillsProgramme o where o.legacyOrganisationSites = false", key = "Apprenticeship not Linked Site", returnType = LegacySkillsProgramme.class, singleResult = false)
//id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacySkillsProgramme o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacySkillsProgramme o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacySkillsProgramme o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacySkillsProgramme o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySkillsProgramme o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacySkillsProgramme.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacySkillsProgramme o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacySkillsProgramme.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacySkillsProgramme o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacySkillsProgramme.class, singleResult = false)
//qualification info
@LegacyReportingAnnotation(name = "Total Linked Skills Programs", query = "select count(o) from LegacySkillsProgramme o where o.skillsProgram is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Skills Programs", query = "select count(o) from LegacySkillsProgramme o where o.skillsProgram is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySkillsProgramme o where o.skillsProgram is null", key = "Non Linked Skills Programs", returnType = LegacySkillsProgramme.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacySkillsProgramme.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Home Affairs", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", returnType = LegacySkillsProgramme.class, singleResult = false)
//Skills Programme Obtained
@LegacyReportingAnnotation(name = "Total Skills Programme Obtained Status on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Skills Programme Obtained Status not on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Skills Programme Obtained Status not on Home Affairs", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and  o.appearsOnHomeAffairsData = false", returnType = LegacySkillsProgramme.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacySkillsProgramme.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySkillsProgramme.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySkillsProgramme.class, singleResult = false)
//Skills Programme Obtained
@LegacyReportingAnnotation(name = "Total Skills Programme Obtained Status on Person File", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Skills Programme Obtained Status not on Person File", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Skills Programme Obtained Status not on Person File", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySkillsProgramme.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySkillsProgramme.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySkillsProgramme.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File and Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File and Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File and Home Affairs", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySkillsProgramme.class, singleResult = false)
//Skills Programme Obtained
@LegacyReportingAnnotation(name = "Total Skills Programme Obtained Status on Person File and Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Skills Programme Obtained Status not on Person File and Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Skills Programme Obtained Status not on Person File and Home Affairs", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Skills Programme Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySkillsProgramme.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacySkillsProgramme o where o.learnerLPStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySkillsProgramme.class, singleResult = false)
public class LegacySkillsProgramme extends AbstractLookup {

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
	 * 500 or so characters @Column(name = "dttc_id" , length = 500)
	 */

	/*
	 * PN: 1 pkiLearnerLPID
	 */
	@CSVAnnotation(name = "pkiLearnerLPID", className = String.class)
	@Column(name = "pki_learner_lp_id")
	private String pkiLearnerLPID;

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
	 * PN: 3 AlternateId
	 */
	@CSVAnnotation(name = "AlternateID", className = String.class)
	@Column(name = "alternate_id")
	private String alternateId;

	/*
	 * PN: 4 Firstname
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name")
	private String firstName;

	/*
	 * PN: 5 MiddleNames
	 */
	@CSVAnnotation(name = "MiddleNames", className = String.class)
	@Column(name = "middle_names")
	private String middleNames;

	/*
	 * PN: 6 Surname
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname")
	private String surname;

	/*
	 * PN: 7 sRefNo
	 */
	@CSVAnnotation(name = "sRefNo", className = String.class)
	@Column(name = "s_ref_no")
	private String sRefNo;

	/*
	 * PN: 8 dtStartDate
	 */
	@CSVAnnotation(name = "dtStartDate", className = String.class)
	@Column(name = "dt_start_date")
	private String dtStartDate;

	/*
	 * PN: 9 dtEndDate
	 */
	@CSVAnnotation(name = "dtEndDate", className = String.class)
	@Column(name = "dt_end_date")
	private String dtEndDate;

	/*
	 * PN: 10 ProviderSDL
	 */
	@CSVAnnotation(name = "ProviderSDL", className = String.class, lookupField = "legacyProviderExists")
	@Column(name = "provider_sdl")
	private String providerSDL;

	@Column(name = "legacy_provider_exists")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyProviderSDL", paramClass = String.class)
	private Boolean legacyProviderExists;

	/*
	 * PN: 11 ProviderLegalName
	 */
	@CSVAnnotation(name = "ProviderLegalName", className = String.class)
	@Column(name = "provider_legal_name")
	private String providerLegalName;

	/*
	 * PN: 12 ProviderTradeName
	 */
	@CSVAnnotation(name = "ProviderTradeName", className = String.class)
	@Column(name = "provider_trade_name")
	private String providerTradeName;

	/*
	 * PN: 13 LearnerLPStatusDesc
	 */
	@CSVAnnotation(name = "LearnerLPStatusDesc", className = String.class)
	@Column(name = "learner_lp_status_desc")
	private String learnerLPStatusDesc;

	/*
	 * PN: 14 EmployerSDL
	 */
	@CSVAnnotation(name = "EmployerSDL", className = String.class, lookupField = "legacyOrganisationSites")
	@Column(name = "employer_sdl")
	private String employerSDL;

	@Column(name = "legacy_organisation_sites")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkCountByRefNumber", paramClass = String.class)
	private Boolean legacyOrganisationSites;

	/*
	 * PN: 15 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 16 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 17 dtLastDateChanged
	 */
	@CSVAnnotation(name = "dtLastDateChanged", className = String.class)
	@Column(name = "dt_last_date_changed")
	private String dtLastDateChanged;

	/*
	 * PN: 18 dtEffectiveDate
	 */
	@CSVAnnotation(name = "dtEffectiveDate", className = String.class)
	@Column(name = "dt_effective_date")
	private String dtEffectiveDate;

	/*
	 * PN: 19 ProjectType
	 */
	@CSVAnnotation(name = "ProjectType", className = String.class)
	@Column(name = "project_type")
	private String projectType;

	/*
	 * PN: 20 sProgrammeCode
	 */
	@CSVAnnotation(name = "sProgrammeCode", className = String.class, lookupField = "skillsProgram")
	@Column(name = "s_programme_code")
	private String sProgrammeCode;

	/*
	 * PN: 21 sProgrammeDesc
	 */
	@CSVAnnotation(name = "sProgrammeDesc", className = String.class)
	@Column(name = "s_programme_desc")
	private String sProgrammeDesc;

	/*
	 * PN: 22 SocioEcoStatusDesc
	 */
	@CSVAnnotation(name = "SocioEcoStatusDesc", className = String.class)
	@Column(name = "socio_eco_status_desc")
	private String socioEcoStatusDesc;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findSkillsProgram", paramClass = String.class)
	private SkillsProgram skillsProgram;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_set_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findSkillsSet", paramClass = String.class)
	private SkillsSet skillsSet;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;
	
	@Transient
	private List<LegacySkillsProgrammeAssessments>legacySkillsProgrammeAssessmentsList ;
	
	@Column(name = "created_company_learner")
	private Boolean createdCompanyLearner;
	
	@Column(name = "accreditation_number", length = 100, nullable = true)
	private String accreditationNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer_id", nullable = true)
	private Company employer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;

	public LegacySkillsProgramme() {
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
		LegacySkillsProgramme other = (LegacySkillsProgramme) obj;
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

	public String getPkiLearnerLPID() {
		return pkiLearnerLPID;
	}

	public void setPkiLearnerLPID(String pkiLearnerLPID) {
		this.pkiLearnerLPID = pkiLearnerLPID;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getAlternateId() {
		return alternateId;
	}

	public void setAlternateId(String alternateId) {
		this.alternateId = alternateId;
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

	public String getsRefNo() {
		return sRefNo;
	}

	public void setsRefNo(String sRefNo) {
		this.sRefNo = sRefNo;
	}

	public String getDtStartDate() {
		return dtStartDate;
	}

	public void setDtStartDate(String dtStartDate) {
		this.dtStartDate = dtStartDate;
	}

	public String getDtEndDate() {
		return dtEndDate;
	}

	public void setDtEndDate(String dtEndDate) {
		this.dtEndDate = dtEndDate;
	}

	public String getProviderSDL() {
		return providerSDL;
	}

	public void setProviderSDL(String providerSDL) {
		this.providerSDL = providerSDL;
	}

	public String getProviderLegalName() {
		return providerLegalName;
	}

	public void setProviderLegalName(String providerLegalName) {
		this.providerLegalName = providerLegalName;
	}

	public String getProviderTradeName() {
		return providerTradeName;
	}

	public void setProviderTradeName(String providerTradeName) {
		this.providerTradeName = providerTradeName;
	}

	public String getLearnerLPStatusDesc() {
		return learnerLPStatusDesc;
	}

	public void setLearnerLPStatusDesc(String learnerLPStatusDesc) {
		this.learnerLPStatusDesc = learnerLPStatusDesc;
	}

	public String getEmployerSDL() {
		return employerSDL;
	}

	public void setEmployerSDL(String employerSDL) {
		this.employerSDL = employerSDL;
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

	public String getDtLastDateChanged() {
		return dtLastDateChanged;
	}

	public void setDtLastDateChanged(String dtLastDateChanged) {
		this.dtLastDateChanged = dtLastDateChanged;
	}

	public String getDtEffectiveDate() {
		return dtEffectiveDate;
	}

	public void setDtEffectiveDate(String dtEffectiveDate) {
		this.dtEffectiveDate = dtEffectiveDate;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getsProgrammeCode() {
		return sProgrammeCode;
	}

	public void setsProgrammeCode(String sProgrammeCode) {
		this.sProgrammeCode = sProgrammeCode;
	}

	public String getsProgrammeDesc() {
		return sProgrammeDesc;
	}

	public void setsProgrammeDesc(String sProgrammeDesc) {
		this.sProgrammeDesc = sProgrammeDesc;
	}

	public String getSocioEcoStatusDesc() {
		return socioEcoStatusDesc;
	}

	public void setSocioEcoStatusDesc(String socioEcoStatusDesc) {
		this.socioEcoStatusDesc = socioEcoStatusDesc;
	}

	public String getIdNoTransient() {
		return idNoTransient;
	}

	public void setIdNoTransient(String idNoTransient) {
		this.idNoTransient = idNoTransient;
	}

	public Boolean getLegacyProviderExists() {
		return legacyProviderExists;
	}

	public void setLegacyProviderExists(Boolean legacyProviderExists) {
		this.legacyProviderExists = legacyProviderExists;
	}

	public Boolean getLegacyOrganisationSites() {
		return legacyOrganisationSites;
	}

	public void setLegacyOrganisationSites(Boolean legacyOrganisationSites) {
		this.legacyOrganisationSites = legacyOrganisationSites;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public List<LegacySkillsProgrammeAssessments> getLegacySkillsProgrammeAssessmentsList() {
		return legacySkillsProgrammeAssessmentsList;
	}

	public void setLegacySkillsProgrammeAssessmentsList(
			List<LegacySkillsProgrammeAssessments> legacySkillsProgrammeAssessmentsList) {
		this.legacySkillsProgrammeAssessmentsList = legacySkillsProgrammeAssessmentsList;
	}

	public Boolean getCreatedCompanyLearner() {
		return createdCompanyLearner;
	}

	public void setCreatedCompanyLearner(Boolean createdCompanyLearner) {
		this.createdCompanyLearner = createdCompanyLearner;
	}

	public String getAccreditationNumber() {
		return accreditationNumber;
	}

	public void setAccreditationNumber(String accreditationNumber) {
		this.accreditationNumber = accreditationNumber;
	}

	public Company getEmployer() {
		return employer;
	}

	public TrainingProviderApplication getTrainingProviderApplication() {
		return trainingProviderApplication;
	}

	public void setEmployer(Company employer) {
		this.employer = employer;
	}

	public void setTrainingProviderApplication(TrainingProviderApplication trainingProviderApplication) {
		this.trainingProviderApplication = trainingProviderApplication;
	}

	public SkillsSet getSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(SkillsSet skillsSet) {
		this.skillsSet = skillsSet;
	}
}
