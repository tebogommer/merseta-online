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
 * Excel name: Learnership.xlsx Tab Name: Learnership
 */
@Entity
@Table(name = "legacy_learnership")

//site info
@LegacyReportingAnnotation(name = "Total Apprenticeship Linked to Site", query = "select count(o) from LegacyLearnership o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Apprenticeship not Linked to Site", query = "select count(o) from LegacyLearnership o where o.legacyOrganisationSites = false", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyLearnership o where o.legacyOrganisationSites = false", key = "Apprenticeship not Linked Site", returnType = LegacyLearnership.class, singleResult = false)
//id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacyLearnership o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacyLearnership o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyLearnership o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyLearnership o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacyLearnership o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacyLearnership o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyLearnership o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacyLearnership.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyLearnership o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyLearnership.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyLearnership o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacyLearnership.class, singleResult = false)
//qualification info
@LegacyReportingAnnotation(name = "Total Linked Learnership", query = "select count(o) from LegacyLearnership o where o.learnership is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Learnership", query = "select count(o) from LegacyLearnership o where o.learnership is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyLearnership o where o.learnership is null", key = "Non Linked Learnership", returnType = LegacyLearnership.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnership.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Home Affairs", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnership.class, singleResult = false)
//Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Home Affairs", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnership.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnership.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyLearnership.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyLearnership.class, singleResult = false)
//Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyLearnership.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyLearnership.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnership.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File and Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File and Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File and Home Affairs", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnership.class, singleResult = false)
//Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File and Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File and Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File and Home Affairs", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnership.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacyLearnership o where o.agreementStatusDesc = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyLearnership.class, singleResult = false)
public class LegacyLearnership extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

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
	 * PN: 1 AgreementId
	 */
	@CSVAnnotation(name = "AgreementId", className = String.class)
	@Column(name = "agreement_id")
	private String agreementId;

	/*
	 * PN: 2 IDNo
	 */
	@CSVAnnotation(name = "IDNo", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "id_no")
	private String idNo;

	@CSVAnnotation(name = "IDNo", className = String.class, lookupField = "validRsaIdNumber")
	@Transient
	private String idNoTransient;

	@Column(name = "appears_on_home_affairs_data")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "findByDhaIdNumberMore", paramClass = String.class)
	private Boolean appearsOnHomeAffairsData;

	@Column(name = "valid_rsa_id_number")
	@CSVLookupAnnotation(className = HomeAffairsService.class, method = "checkRsaId", paramClass = String.class)
	private Boolean validRsaIdNumber;

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
	 * PN: 6 LearnershipCode
	 */
	@CSVAnnotation(name = "LearnershipCode", className = String.class, lookupField = "learnership")
	@Column(name = "learnership_code")
	private String learnershipCode;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "learnership_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLearnership", paramClass = String.class)
	private Learnership learnership;

	/*
	 * PN: 7 LearnershipTitle
	 */
	@CSVAnnotation(name = "LearnershipTitle", className = String.class)
	@Column(name = "learnership_title", length = 500)
	private String learnershipTitle;

	/*
	 * PN: 8 LshipCode
	 */
	@CSVAnnotation(name = "LshipCode", className = String.class)
	@Column(name = "l_ship_code")
	private String lShipCode;

	/*
	 * PN: 9 AgreementRefNo
	 */
	@CSVAnnotation(name = "AgreementRefNo", className = String.class)
	@Column(name = "agreement_ref_no")
	private String agreementRefNo;

	/*
	 * PN: 10 AgreementStartDate
	 */
	@CSVAnnotation(name = "AgreementStartDate", className = String.class)
	@Column(name = "agreement_start_date")
	private String agreementStartDate;

	/*
	 * PN: 11 AgreementEndDate
	 */
	@CSVAnnotation(name = "AgreementEndDate", className = String.class)
	@Column(name = "agreement_end_date")
	private String agreementEndDate;

	/*
	 * PN: 12 AgreementStatusDesc
	 */
	@CSVAnnotation(name = "AgreementStatusDesc", className = String.class)
	@Column(name = "agreement_status_desc")
	private String agreementStatusDesc;

	/*
	 * PN: 13 AgreementStatusEffectiveDate
	 */
	@CSVAnnotation(name = "AgreementStatusEffectiveDate", className = String.class)
	@Column(name = "agreement_status_effective_date")
	private String agreementStatusEffectiveDate;

	/*
	 * PN: 14 LastDateChanged
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed")
	private String lastDateChanged;

	/*
	 * PN: 15 Created
	 */
	@CSVAnnotation(name = "Created", className = String.class)
	@Column(name = "created")
	private String created;

	/*
	 * PN: 16 EmployerSDL
	 */
	@CSVAnnotation(name = "EmployerSDL", className = String.class, lookupField = "legacyOrganisationSites")
	@Column(name = "employer_sdl")
	private String employerSdl;

	@Column(name = "legacy_organisation_sites")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkCountByRefNumber", paramClass = String.class)
	private Boolean legacyOrganisationSites;

	/*
	 * PN: 17 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal", length = 500)
	private String organisationNameLegal;

	/*
	 * PN: 18 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade", length = 500)
	private String organisationNameTrade;

	/*
	 * PN: 19 ProviderSDL
	 */
	@CSVAnnotation(name = "ProviderSDL", className = String.class, lookupField = "legacyProviderExists")
	@Column(name = "provider_sdl")
	private String providerSdl;

	@Column(name = "legacy_provider_exists")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyProviderSDL", paramClass = String.class)
	private Boolean legacyProviderExists;

	/*
	 * PN: 20 DOLOrganisationNameLegal
	 */
	@CSVAnnotation(name = "DOLOrganisationNameLegal", className = String.class)
	@Column(name = "dol_organisation_name_legal", length = 500)
	private String dolOrganisationNameLegal;

	/*
	 * PN: 21 Funded
	 */
	@CSVAnnotation(name = "Funded", className = String.class)
	@Column(name = "funded")
	private String funded;

	/*
	 * PN: 22 ProjectType
	 */
	@CSVAnnotation(name = "ProjectType", className = String.class)
	@Column(name = "project_type")
	private String projectType;

	/*
	 * PN: 23 PreviousLearnership
	 */
	@CSVAnnotation(name = "PreviousLearnership", className = String.class)
	@Column(name = "previous_learnership", length = 500)
	private String previousLearnership;

	/*
	 * PN: 24 PrevLearnershipTitle
	 */
	@CSVAnnotation(name = "PrevLearnershipTitle", className = String.class)
	@Column(name = "prev_learnership_title", length = 500)
	private String prevLearnershipTitle;

	/*
	 * PN: 25 PrevLearnerhipCode
	 */
	@CSVAnnotation(name = "PrevLearnerhipCode", className = String.class)
	@Column(name = "prev_learnerhip_code", length = 500)
	private String prevLearnerhipCode;

	/*
	 * PN: 26 PreviousEmployed
	 */
	@CSVAnnotation(name = "PreviousEmployed", className = String.class)
	@Column(name = "previous_employed")
	private String previousEmployed;

	/*
	 * PN: 27 LearnerEmployed
	 */
	@CSVAnnotation(name = "LearnerEmployed", className = String.class)
	@Column(name = "learner_employed")
	private String learnerEmployed;

	/*
	 * PN: 28 DurationLearneremployed
	 */
	@CSVAnnotation(name = "DurationLearneremployed", className = String.class)
	@Column(name = "duration_learner_employed")
	private String durationLearnerEmployed;

	/*
	 * PN: 29 RegistrationDate
	 */
	@CSVAnnotation(name = "RegistrationDate", className = String.class)
	@Column(name = "registration_date")
	private String registrationDate;

	/*
	 * PN: 30 CaptureDate
	 */
	@CSVAnnotation(name = "CaptureDate", className = String.class)
	@Column(name = "capture_date")
	private String captureDate;

	/*
	 * PN: 31 SocioEcoStatusDesc
	 */
	@CSVAnnotation(name = "SocioEcoStatusDesc", className = String.class)
	@Column(name = "socio_eco_status_desc")
	private String socioEcoStatusDesc;

	@Column(name = "processed")
	private Boolean processed;
	
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
	
	@Transient
	private List<LegacyLearnershipAssessment>legacyLearnershipAssessmentList ;

	public LegacyLearnership() {
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
		LegacyLearnership other = (LegacyLearnership) obj;
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

	public String getAgreementId() {
		return agreementId;
	}

	public void setAgreementId(String agreementId) {
		this.agreementId = agreementId;
	}

	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public String getFirstname() {
		return firstName;
	}

	public void setFirstname(String firstname) {
		this.firstName = firstname;
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

	public String getLearnershipCode() {
		return learnershipCode;
	}

	public void setLearnershipCode(String learnershipCode) {
		this.learnershipCode = learnershipCode;
	}

	public String getLearnershipTitle() {
		return learnershipTitle;
	}

	public void setLearnershipTitle(String learnershipTitle) {
		this.learnershipTitle = learnershipTitle;
	}

	public String getAgreementRefNo() {
		return agreementRefNo;
	}

	public void setAgreementRefNo(String agreementRefNo) {
		this.agreementRefNo = agreementRefNo;
	}

	public String getAgreementStartDate() {
		return agreementStartDate;
	}

	public void setAgreementStartDate(String agreementStartDate) {
		this.agreementStartDate = agreementStartDate;
	}

	public String getAgreementEndDate() {
		return agreementEndDate;
	}

	public void setAgreementEndDate(String agreementEndDate) {
		this.agreementEndDate = agreementEndDate;
	}

	public String getAgreementStatusDesc() {
		return agreementStatusDesc;
	}

	public void setAgreementStatusDesc(String agreementStatusDesc) {
		this.agreementStatusDesc = agreementStatusDesc;
	}

	public String getAgreementStatusEffectiveDate() {
		return agreementStatusEffectiveDate;
	}

	public void setAgreementStatusEffectiveDate(String agreementStatusEffectiveDate) {
		this.agreementStatusEffectiveDate = agreementStatusEffectiveDate;
	}

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
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

	public String getDolOrganisationNameLegal() {
		return dolOrganisationNameLegal;
	}

	public void setDolOrganisationNameLegal(String dolOrganisationNameLegal) {
		this.dolOrganisationNameLegal = dolOrganisationNameLegal;
	}

	public String getFunded() {
		return funded;
	}

	public void setFunded(String funded) {
		this.funded = funded;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getPreviousLearnership() {
		return previousLearnership;
	}

	public void setPreviousLearnership(String previousLearnership) {
		this.previousLearnership = previousLearnership;
	}

	public String getPrevLearnershipTitle() {
		return prevLearnershipTitle;
	}

	public void setPrevLearnershipTitle(String prevLearnershipTitle) {
		this.prevLearnershipTitle = prevLearnershipTitle;
	}

	public String getPrevLearnerhipCode() {
		return prevLearnerhipCode;
	}

	public void setPrevLearnerhipCode(String prevLearnerhipCode) {
		this.prevLearnerhipCode = prevLearnerhipCode;
	}

	public String getPreviousEmployed() {
		return previousEmployed;
	}

	public void setPreviousEmployed(String previousEmployed) {
		this.previousEmployed = previousEmployed;
	}

	public String getLearnerEmployed() {
		return learnerEmployed;
	}

	public void setLearnerEmployed(String learnerEmployed) {
		this.learnerEmployed = learnerEmployed;
	}

	public String getDurationLearnerEmployed() {
		return durationLearnerEmployed;
	}

	public void setDurationLearnerEmployed(String durationLearnerEmployed) {
		this.durationLearnerEmployed = durationLearnerEmployed;
	}

	public String getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(String registrationDate) {
		this.registrationDate = registrationDate;
	}

	public String getCaptureDate() {
		return captureDate;
	}

	public void setCaptureDate(String captureDate) {
		this.captureDate = captureDate;
	}

	public String getSocioEcoStatusDesc() {
		return socioEcoStatusDesc;
	}

	public void setSocioEcoStatusDesc(String socioEcoStatusDesc) {
		this.socioEcoStatusDesc = socioEcoStatusDesc;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getlShipCode() {
		return lShipCode;
	}

	public void setlShipCode(String lShipCode) {
		this.lShipCode = lShipCode;
	}

	public String getEmployerSdl() {
		return employerSdl;
	}

	public void setEmployerSdl(String employerSdl) {
		this.employerSdl = employerSdl;
	}

	public String getProviderSdl() {
		return providerSdl;
	}

	public void setProviderSdl(String providerSdl) {
		this.providerSdl = providerSdl;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public String getIdNoTransient() {
		return idNoTransient;
	}

	public void setIdNoTransient(String idNoTransient) {
		this.idNoTransient = idNoTransient;
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

	public Learnership getLearnership() {
		return learnership;
	}

	public void setLearnership(Learnership learnership) {
		this.learnership = learnership;
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

	public List<LegacyLearnershipAssessment> getLegacyLearnershipAssessmentList() {
		return legacyLearnershipAssessmentList;
	}

	public void setLegacyLearnershipAssessmentList(List<LegacyLearnershipAssessment> legacyLearnershipAssessmentList) {
		this.legacyLearnershipAssessmentList = legacyLearnershipAssessmentList;
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

}
