package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Index;
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
 * Excel name: Unit Standard.xlsx Tab Name: Unit Standard
 */
@Entity
@Table(name = "legacy_unit_standard", indexes = {
		@Index(name = "providerSdl", columnList = "provider_sdl")
})
//site info
@LegacyReportingAnnotation(name = "Total Apprenticeship Linked to Site", query = "select count(o) from LegacyUnitStandard o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Apprenticeship not Linked to Site", query = "select count(o) from LegacyUnitStandard o where o.legacyOrganisationSites = false", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandard o where o.legacyOrganisationSites = false", key = "Apprenticeship not Linked Site", returnType = LegacyUnitStandard.class, singleResult = false)
//id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacyUnitStandard o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacyUnitStandard o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacyUnitStandard o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacyUnitStandard o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandard o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacyUnitStandard.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandard o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyUnitStandard.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandard o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacyUnitStandard.class, singleResult = false)
//qualification info
@LegacyReportingAnnotation(name = "Total Linked Unit Standards", query = "select count(o) from LegacyUnitStandard o where o.unitStandard is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Unit Standards", query = "select count(o) from LegacyUnitStandard o where o.unitStandard is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyUnitStandard o where o.unitStandard is null", key = "Non Linked Unit Standards", returnType = LegacyUnitStandard.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandard.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Home Affairs", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandard.class, singleResult = false)
//Unit Standard Obtained
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status not on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Unit Standard Obtained Status not on Home Affairs", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandard.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandard.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyUnitStandard.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyUnitStandard.class, singleResult = false)
//Unit Standard Obtained
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status on Person File", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status not on Person File", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Unit Standard Obtained Status not on Person File", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyUnitStandard.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyUnitStandard.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandard.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File and Home Affairs", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandard.class, singleResult = false)
//Unit Standard Obtained
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Unit Standard Obtained Status not on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Unit Standard Obtained Status not on Person File and Home Affairs", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Unit Standard Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandard.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacyUnitStandard o where o.learnerUSStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyUnitStandard.class, singleResult = false)
public class LegacyUnitStandard extends AbstractLookup {

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
	 * PN: 1 iLearnerUnitStdID
	 */
	@CSVAnnotation(name = "iLearnerUnitStdID", className = String.class)
	@Column(name = "i_learner_unit_std_id")
	private String iLearnerUnitStdID;

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
	 * PN: 7 UnitStdCode
	 */
	@CSVAnnotation(name = "UnitStdCode", className = String.class, lookupField = "unitStandard")
	@Column(name = "unit_std_code")
	private String unitStdCode;

	/*
	 * PN: 8 UnitStdDesc
	 */
	@CSVAnnotation(name = "UnitStdDesc", className = String.class)
	@Column(name = "unit_std_desc", columnDefinition = "LONGTEXT")
	private String unitStdDesc;

	/*
	 * PN: 9 dtStartDate
	 */
	@CSVAnnotation(name = "dtStartDate", className = String.class)
	@Column(name = "dt_start_date")
	private String dtStartDate;

	/*
	 * PN: 10 dtEndDate
	 */
	@CSVAnnotation(name = "dtEndDate", className = String.class)
	@Column(name = "dt_end_date")
	private String dtEndDate;

	/*
	 * PN: 11 dtEffectiveDate
	 */
	@CSVAnnotation(name = "dtEffectiveDate", className = String.class)
	@Column(name = "dt_effective_date")
	private String dtEffectiveDate;

	/*
	 * PN: 12 dtLastDateChanged
	 */
	@CSVAnnotation(name = "dtLastDateChanged", className = String.class)
	@Column(name = "dt_last_date_changed")
	private String dtLastDateChanged;

	/*
	 * PN: 13 LearnerUSStatusDesc
	 */
	@CSVAnnotation(name = "LearnerUSStatusDesc", className = String.class)
	@Column(name = "learner_us_status_desc", columnDefinition = "LONGTEXT")
	private String learnerUSStatusDesc;

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
	 * PN: 17 ProviderSDL
	 */
	@CSVAnnotation(name = "ProviderSDL", className = String.class, lookupField = "legacyProviderExists")
	@Column(name = "provider_sdl")
	private String providerSDL;

	@Column(name = "legacy_provider_exists")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyProviderSDL", paramClass = String.class)
	private Boolean legacyProviderExists;

	/*
	 * PN: 18 ProviderLegalname
	 */
	@CSVAnnotation(name = "ProviderLegalname", className = String.class)
	@Column(name = "provider_legal_name")
	private String providerLegalname;

	/*
	 * PN: 19 ProviderTradeName
	 */
	@CSVAnnotation(name = "ProviderTradeName", className = String.class)
	@Column(name = "provider_trade_name")
	private String providerTradeName;

	/*
	 * PN: 20 ProjectType
	 */
	@CSVAnnotation(name = "ProjectType", className = String.class)
	@Column(name = "project_type")
	private String projectType;

	/*
	 * PN: 21 iSponsorshipID
	 */
	@CSVAnnotation(name = "iSponsorshipID", className = String.class)
	@Column(name = "i_sponsorship_id")
	private String iSponsorshipID;

	/*
	 * PN: 22 SocioEcoStatusDesc
	 */
	@CSVAnnotation(name = "SocioEcoStatusDesc", className = String.class)
	@Column(name = "socio_eco_status_desc", columnDefinition = "LONGTEXT")
	private String socioEcoStatusDesc;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "unit_standard_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findUnitStandards", paramClass = String.class)
	private UnitStandards unitStandard;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;
	
	@Column(name = "created_company_learner")
	private Boolean createdCompanyLearner;
	
	@Column(name = "legacy_provider_accreditation_flat_id", nullable = true)
	private Long legacyProviderAccreditationFlatId;
	
	@Column(name = "legacy_provider_unit_standard_flat_id", nullable = true)
	private Long legacyProviderAccreditationUnitStandardFlatId;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	private Qualification qualification;
	
	@Column(name = "accreditation_number", length = 100, nullable = true)
	private String accreditationNumber;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer_id", nullable = true)
	private Company employer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "training_provider_application_id", nullable = true)
	private TrainingProviderApplication trainingProviderApplication;
	
	@Transient
	private List<LegacyUnitStandardAssessment>legacyUnitStandardAssessmentList ;

	public LegacyUnitStandard() {
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
		LegacyUnitStandard other = (LegacyUnitStandard) obj;
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

	public String getiLearnerUnitStdID() {
		return iLearnerUnitStdID;
	}

	public void setiLearnerUnitStdID(String iLearnerUnitStdID) {
		this.iLearnerUnitStdID = iLearnerUnitStdID;
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

	public String getUnitStdCode() {
		return unitStdCode;
	}

	public void setUnitStdCode(String unitStdCode) {
		this.unitStdCode = unitStdCode;
	}

	public String getUnitStdDesc() {
		return unitStdDesc;
	}

	public void setUnitStdDesc(String unitStdDesc) {
		this.unitStdDesc = unitStdDesc;
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

	public String getDtEffectiveDate() {
		return dtEffectiveDate;
	}

	public void setDtEffectiveDate(String dtEffectiveDate) {
		this.dtEffectiveDate = dtEffectiveDate;
	}

	public String getDtLastDateChanged() {
		return dtLastDateChanged;
	}

	public void setDtLastDateChanged(String dtLastDateChanged) {
		this.dtLastDateChanged = dtLastDateChanged;
	}

	public String getLearnerUSStatusDesc() {
		return learnerUSStatusDesc;
	}

	public void setLearnerUSStatusDesc(String learnerUSStatusDesc) {
		this.learnerUSStatusDesc = learnerUSStatusDesc;
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

	public String getProviderSDL() {
		return providerSDL;
	}

	public void setProviderSDL(String providerSDL) {
		this.providerSDL = providerSDL;
	}

	public String getProviderLegalname() {
		return providerLegalname;
	}

	public void setProviderLegalname(String providerLegalname) {
		this.providerLegalname = providerLegalname;
	}

	public String getProviderTradeName() {
		return providerTradeName;
	}

	public void setProviderTradeName(String providerTradeName) {
		this.providerTradeName = providerTradeName;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getiSponsorshipID() {
		return iSponsorshipID;
	}

	public void setiSponsorshipID(String iSponsorshipID) {
		this.iSponsorshipID = iSponsorshipID;
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

	public Boolean getLegacyOrganisationSites() {
		return legacyOrganisationSites;
	}

	public void setLegacyOrganisationSites(Boolean legacyOrganisationSites) {
		this.legacyOrganisationSites = legacyOrganisationSites;
	}

	public Boolean getLegacyProviderExists() {
		return legacyProviderExists;
	}

	public void setLegacyProviderExists(Boolean legacyProviderExists) {
		this.legacyProviderExists = legacyProviderExists;
	}

	public UnitStandards getUnitStandard() {
		return unitStandard;
	}

	public void setUnitStandard(UnitStandards unitStandard) {
		this.unitStandard = unitStandard;
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

	public List<LegacyUnitStandardAssessment> getLegacyUnitStandardAssessmentList() {
		return legacyUnitStandardAssessmentList;
	}

	public void setLegacyUnitStandardAssessmentList(List<LegacyUnitStandardAssessment> legacyUnitStandardAssessmentList) {
		this.legacyUnitStandardAssessmentList = legacyUnitStandardAssessmentList;
	}

	public Boolean getCreatedCompanyLearner() {
		return createdCompanyLearner;
	}

	public void setCreatedCompanyLearner(Boolean createdCompanyLearner) {
		this.createdCompanyLearner = createdCompanyLearner;
	}

	public Long getLegacyProviderAccreditationFlatId() {
		return legacyProviderAccreditationFlatId;
	}

	public void setLegacyProviderAccreditationFlatId(Long legacyProviderAccreditationFlatId) {
		this.legacyProviderAccreditationFlatId = legacyProviderAccreditationFlatId;
	}

	public Long getLegacyProviderAccreditationUnitStandardFlatId() {
		return legacyProviderAccreditationUnitStandardFlatId;
	}

	public void setLegacyProviderAccreditationUnitStandardFlatId(Long legacyProviderAccreditationUnitStandardFlatId) {
		this.legacyProviderAccreditationUnitStandardFlatId = legacyProviderAccreditationUnitStandardFlatId;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
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
}
