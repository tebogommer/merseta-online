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
import haj.com.entity.OfoCodes;
import haj.com.framework.AbstractLookup;
import haj.com.service.lookup.HomeAffairsService;

/**
 * Excel name: Apprenticeship.xlsx Tab Name: Apprenticeship
 */
@Entity
@Table(name = "legacy_apprenticeship")
// site info
@LegacyReportingAnnotation(name = "Total Apprenticeship Linked to Site", query = "select count(o) from LegacyApprenticeship o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Apprenticeship not Linked to Site", query = "select count(o) from LegacyApprenticeship o where o.legacyOrganisationSites = false", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyApprenticeship o where o.legacyOrganisationSites = false", key = "Apprenticeship not Linked Site", returnType = LegacyApprenticeship.class, singleResult = false)
// id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacyApprenticeship o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacyApprenticeship o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacyApprenticeship o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacyApprenticeship o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyApprenticeship o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacyApprenticeship.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyApprenticeship o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacyApprenticeship.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyApprenticeship o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacyApprenticeship.class, singleResult = false)
// qualification info
@LegacyReportingAnnotation(name = "Total Linked Qualification", query = "select count(o) from LegacyApprenticeship o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Qualification", query = "select count(o) from LegacyApprenticeship o where o.qualification is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyApprenticeship o where o.qualification is null", key = "Non Linked Qualifications", returnType = LegacyApprenticeship.class, singleResult = false)
// ofoCodes info
@LegacyReportingAnnotation(name = "Total Linked Ofo Codes", query = "select count(o) from LegacyApprenticeship o where o.ofoCodes is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Ofo Codes", query = "select count(o) from LegacyApprenticeship o where o.ofoCodes is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyApprenticeship o where o.ofoCodes is null", key = "Non Linked Ofo Codes", returnType = LegacyApprenticeship.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacyApprenticeship.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Home Affairs", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", returnType = LegacyApprenticeship.class, singleResult = false)
//Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Home Affairs", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", returnType = LegacyApprenticeship.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacyApprenticeship.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyApprenticeship.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyApprenticeship.class, singleResult = false)
//Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyApprenticeship.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacyApprenticeship.class, singleResult = false)
//Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyApprenticeship.class, singleResult = false)
//Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File and Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File and Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File and Home Affairs", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyApprenticeship.class, singleResult = false)
//Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File and Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File and Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File and Home Affairs", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyApprenticeship.class, singleResult = false)
//Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacyApprenticeship o where o.contractStatus = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacyApprenticeship o where o.contractStatus = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacyApprenticeship.class, singleResult = false)
public class LegacyApprenticeship extends AbstractLookup {

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
	 * PN: 3 AlternateID
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
	 * PN: 7 FromDate
	 */
	@CSVAnnotation(name = "FromDate", className = String.class)
	@Column(name = "from_date")
	private String fromDate;

	/*
	 * PN: 8 ToDate
	 */
	@CSVAnnotation(name = "ToDate", className = String.class)
	@Column(name = "to_date")
	private String toDate;

	/*
	 * PN: 9 TradeDescription
	 */
	@CSVAnnotation(name = "TradeDescription", className = String.class)
	@Column(name = "trade_description")
	private String tradeDescription;

	/*
	 * PN: 10 ContractNumber
	 */
	@CSVAnnotation(name = "ContractNumber", className = String.class)
	@Column(name = "contract_number")
	private String contractNumber;

	/*
	 * PN: 11 OFOCode
	 */
	@CSVAnnotation(name = "OFOCode", className = String.class, lookupField = "ofoCodes")
	@Column(name = "ofo_code")
	private String ofoCode;

	/*
	 * PN: 12 OFOCode
	 */
	@CSVAnnotation(name = "OFODescription", className = String.class)
	@Column(name = "ofo_description")
	private String ofoDescription;

	/*
	 * PN: 13 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class)
	@Column(name = "sdl_no")
	private String sdlNo;

	/*
	 * PN: 14 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 15 WAEmpSDL
	 */
	@CSVAnnotation(name = "WAEmpSDL", className = String.class, lookupField = "legacyOrganisationSites")
	@Column(name = "wae_mp_sdl")
	private String waeMpSdl;

	@Column(name = "legacy_organisation_sites")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLegacyOrganisationSites", paramClass = String.class)
	private Boolean legacyOrganisationSites;

	/*
	 * PN: 16 WALegalName
	 */
	@CSVAnnotation(name = "WALegalName", className = String.class)
	@Column(name = "wa_legal_name")
	private String waLegalName;

	/*
	 * PN: 17 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 18 ContractStatus
	 */
	@CSVAnnotation(name = "ContractStatus", className = String.class)
	@Column(name = "contract_status")
	private String contractStatus;

	/*
	 * PN: 19 StatusDate
	 */
	@CSVAnnotation(name = "StatusDate", className = String.class)
	@Column(name = "status_date")
	private String statusDate;

	/*
	 * PN: 20 AgreementNumber
	 */
	@CSVAnnotation(name = "AgreementNumber", className = String.class)
	@Column(name = "agreement_number")
	private String agreementNumber;

	/*
	 * PN: 21 CertificateNumber
	 */
	@CSVAnnotation(name = "CertificateNumber", className = String.class)
	@Column(name = "certificate_number")
	private String certificateNumber;

	/*
	 * PN: 22 CertificateDate
	 */
	@CSVAnnotation(name = "CertificateDate", className = String.class)
	@Column(name = "certificate_date")
	private String certificateDate;

	/*
	 * PN: 23 LastDateChanged
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed")
	private String lastDateChanged;

	/*
	 * PN: 24 Region
	 */
	@CSVAnnotation(name = "Region", className = String.class)
	@Column(name = "region")
	private String region;

	/*
	 * PN: 25 SETARegion
	 */
	@CSVAnnotation(name = "SETARegion", className = String.class)
	@Column(name = "seta_region")
	private String setaRegion;

	/*
	 * PN: 26 MagisterialDistrict
	 */
	@CSVAnnotation(name = "MagisterialDistrict", className = String.class)
	@Column(name = "magisterial_district")
	private String magisterialDistrict;

	/*
	 * PN: 27 N2
	 */
	@CSVAnnotation(name = "N2", className = String.class)
	@Column(name = "n2")
	private String nTwo;

	/*
	 * PN: 28 MinTraining
	 */
	@CSVAnnotation(name = "MinTraining", className = String.class)
	@Column(name = "min_training")
	private String minTraining;

	/*
	 * PN: 29 DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_created")
	private String dateCreated;

	/*
	 * PN: 30 DateLastChanged
	 */
	@CSVAnnotation(name = "DateLastChanged", className = String.class)
	@Column(name = "date_last_changed")
	private String dateLastChanged;

	/*
	 * PN: 31 ApprenticeshipRegisterDate
	 */
	@CSVAnnotation(name = "ApprenticeshipRegisterDate", className = String.class)
	@Column(name = "apprenticeship_register_date")
	private String apprenticeshipRegisterDate;

	/*
	 * PN: 32 ProjectType
	 */
	@CSVAnnotation(name = "ProjectType", className = String.class)
	@Column(name = "project_type")
	private String projectType;

	/*
	 * PN: 33 SocioEcoStatusDesc
	 */
	@CSVAnnotation(name = "SocioEcoStatusDesc", className = String.class)
	@Column(name = "socio_eco_status_desc")
	private String socioEcoStatusDesc;

	/*
	 * PN: 34 ApprenticeshipType
	 */
	@CSVAnnotation(name = "ApprenticeshipType", className = String.class)
	@Column(name = "apprenticeship_type")
	private String apprenticeshipType;

	@CSVAnnotation(name = "QualCode", className = String.class, lookupField = "qualification")
	@Column(name = "qual_code")
	private String qualCode;

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
	
	@Transient
	private List<LegacyApprenticeshipTradeTest>legacyApprenticeshipTradeTestList ;

	public LegacyApprenticeship() {
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
		LegacyApprenticeship other = (LegacyApprenticeship) obj;
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

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getTradeDescription() {
		return tradeDescription;
	}

	public void setTradeDescription(String tradeDescription) {
		this.tradeDescription = tradeDescription;
	}

	public String getContractNumber() {
		return contractNumber;
	}

	public void setContractNumber(String contractNumber) {
		this.contractNumber = contractNumber;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getOfoDescription() {
		return ofoDescription;
	}

	public void setOfoDescription(String ofoDescription) {
		this.ofoDescription = ofoDescription;
	}

	public String getSdlNo() {
		return sdlNo;
	}

	public void setSdlNo(String sdlNo) {
		this.sdlNo = sdlNo;
	}

	public String getOrganisationNameLegal() {
		return organisationNameLegal;
	}

	public void setOrganisationNameLegal(String organisationNameLegal) {
		this.organisationNameLegal = organisationNameLegal;
	}

	public String getWaLegalName() {
		return waLegalName;
	}

	public void setWaLegalName(String waLegalName) {
		this.waLegalName = waLegalName;
	}

	public String getOrganisationNameTrade() {
		return organisationNameTrade;
	}

	public void setOrganisationNameTrade(String organisationNameTrade) {
		this.organisationNameTrade = organisationNameTrade;
	}

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getStatusDate() {
		return statusDate;
	}

	public void setStatusDate(String statusDate) {
		this.statusDate = statusDate;
	}

	public String getAgreementNumber() {
		return agreementNumber;
	}

	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getCertificateDate() {
		return certificateDate;
	}

	public void setCertificateDate(String certificateDate) {
		this.certificateDate = certificateDate;
	}

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getSetaRegion() {
		return setaRegion;
	}

	public void setSetaRegion(String setaRegion) {
		this.setaRegion = setaRegion;
	}

	public String getMagisterialDistrict() {
		return magisterialDistrict;
	}

	public void setMagisterialDistrict(String magisterialDistrict) {
		this.magisterialDistrict = magisterialDistrict;
	}

	public String getMinTraining() {
		return minTraining;
	}

	public void setMinTraining(String minTraining) {
		this.minTraining = minTraining;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateLastChanged() {
		return dateLastChanged;
	}

	public void setDateLastChanged(String dateLastChanged) {
		this.dateLastChanged = dateLastChanged;
	}

	public String getProjectType() {
		return projectType;
	}

	public void setProjectType(String projectType) {
		this.projectType = projectType;
	}

	public String getSocioEcoStatusDesc() {
		return socioEcoStatusDesc;
	}

	public void setSocioEcoStatusDesc(String socioEcoStatusDesc) {
		this.socioEcoStatusDesc = socioEcoStatusDesc;
	}

	public String getApprenticeshipType() {
		return apprenticeshipType;
	}

	public void setApprenticeshipType(String apprenticeshipType) {
		this.apprenticeshipType = apprenticeshipType;
	}

	public String getApprenticeshipRegisterDate() {
		return apprenticeshipRegisterDate;
	}

	public void setApprenticeshipRegisterDate(String apprenticeshipRegisterDate) {
		this.apprenticeshipRegisterDate = apprenticeshipRegisterDate;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public String getIdTwo() {
		return idTwo;
	}

	public void setIdTwo(String idTwo) {
		this.idTwo = idTwo;
	}

	public String getAlternateId() {
		return alternateId;
	}

	public void setAlternateId(String alternateId) {
		this.alternateId = alternateId;
	}

	public String getWaeMpSdl() {
		return waeMpSdl;
	}

	public void setWaeMpSdl(String waeMpSdl) {
		this.waeMpSdl = waeMpSdl;
	}

	public String getnTwo() {
		return nTwo;
	}

	public void setnTwo(String nTwo) {
		this.nTwo = nTwo;
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

	public String getQualCode() {
		return qualCode;
	}

	public void setQualCode(String qualCode) {
		this.qualCode = qualCode;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public List<LegacyApprenticeshipTradeTest> getLegacyApprenticeshipTradeTestList() {
		return legacyApprenticeshipTradeTestList;
	}

	public void setLegacyApprenticeshipTradeTestList(
			List<LegacyApprenticeshipTradeTest> legacyApprenticeshipTradeTestList) {
		this.legacyApprenticeshipTradeTestList = legacyApprenticeshipTradeTestList;
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
