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
 * Excel name: SECT28.xlsx Tab Name: SECT28
 */
@Entity
@Table(name = "legacy_sect_twenty_eight ")
// site info
@LegacyReportingAnnotation(name = "Total Apprenticeship Linked to Site", query = "select count(o) from LegacySECTTwentyEight o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Apprenticeship not Linked to Site", query = "select count(o) from LegacySECTTwentyEight o where o.legacyOrganisationSites = false", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySECTTwentyEight o where o.legacyOrganisationSites = false", key = "Apprenticeship not Linked Site", returnType = LegacySECTTwentyEight.class, singleResult = false)
// id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacySECTTwentyEight o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacySECTTwentyEight o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySECTTwentyEight o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacySECTTwentyEight.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacySECTTwentyEight o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacySECTTwentyEight.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacySECTTwentyEight o where o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacySECTTwentyEight.class, singleResult = false)
// qualification info
@LegacyReportingAnnotation(name = "Total Linked Qualification", query = "select count(o) from LegacySECTTwentyEight o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Qualification", query = "select count(o) from LegacySECTTwentyEight o where o.qualification is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySECTTwentyEight o where o.qualification is null", key = "Non Linked Qualifications", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacySECTTwentyEight o where o.description = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Home Affairs", query = "select o from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Home Affairs", query = "select o from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacySECTTwentyEight o where o.description = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacySECTTwentyEight o where o.description = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File", query = "select o from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File", query = "select o from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacySECTTwentyEight o where o.description = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Application' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacySECTTwentyEight o where o.description = 'Application' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File and Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File and Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File and Home Affairs", query = "select o from LegacySECTTwentyEight o where o.description = 'Externally Loaded' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File and Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File and Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File and Home Affairs", query = "select o from LegacySECTTwentyEight o where o.description = 'Qualification Obtained' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySECTTwentyEight.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Registered' and o.idNo in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacySECTTwentyEight o where o.description = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacySECTTwentyEight o where o.description = 'Registered' and o.idNo not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySECTTwentyEight.class, singleResult = false)
public class LegacySECTTwentyEight extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** Unique Id of AbetBand. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * PN: 1 ApprenticeshipID
	 */
	@CSVAnnotation(name = "ApprenticeshipID", className = String.class)
	@Column(name = "apprenticeship_id")
	private String apprenticeshipID;

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
	 * PN: 3 AlternateIDNo
	 */
	@CSVAnnotation(name = "AlternateID", className = String.class)
	@Column(name = "alternate_id_no")
	private String alternateIDNo;

	/*
	 * PN: 4 Firstname
	 */
	@CSVAnnotation(name = "Firstname", className = String.class)
	@Column(name = "first_name")
	private String firstName;

	/*
	 * PN: 5 Surname
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname")
	private String surname;

	/*
	 * PN: 6 LearnerStatusEffectiveDate
	 */
	@CSVAnnotation(name = "LearnerStatusEffectiveDate", className = String.class)
	@Column(name = "learner_status_effective_date")
	private String learnerStatusEffectiveDate;

	/*
	 * PN: 7 ApprenticeshipStartDate
	 */
	@CSVAnnotation(name = "ApprenticeshipStartDate", className = String.class)
	@Column(name = "apprenticeship_start_date")
	private String apprenticeshipStartDate;

	/*
	 * PN: 8 ApprenticeshipStatusEffectiveDate
	 */
	@CSVAnnotation(name = "ApprenticeshipStatusEffectiveDate", className = String.class)
	@Column(name = "apprenticeship_status_effective_date")
	private String apprenticeshipStatusEffectiveDate;

	/*
	 * PN: 9 ApprenticeshipEndDate
	 */
	@CSVAnnotation(name = "ApprenticeshipEndDate", className = String.class)
	@Column(name = "apprenticeship_end_date")
	private String apprenticeshipEndDate;

	/*
	 * PN: 10 TradeDescription
	 */
	@CSVAnnotation(name = "TradeDescription", className = String.class)
	@Column(name = "trade_description")
	private String tradeDescription;

	/*
	 * PN: 11 Description
	 */
	@CSVAnnotation(name = "Description", className = String.class)
	@Column(name = "description")
	private String description;

	/*
	 * PN: 12 CurrentPreviousEmp
	 */
	@CSVAnnotation(name = "CurrentPreviousEmp", className = String.class)
	@Column(name = "current_previous_emp")
	private String currentPreviousEmp;

	/*
	 * PN: 13 WASDL
	 */
	@CSVAnnotation(name = "WASDL", className = String.class, lookupField = "legacyOrganisationSites")
	@Column(name = "wasdl")
	private String wasdl;

	@Column(name = "legacy_organisation_sites")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkCountByRefNumber", paramClass = String.class)
	private Boolean legacyOrganisationSites;

	/*
	 * PN: 14 WALegalName
	 */
	@CSVAnnotation(name = "WALegalName", className = String.class)
	@Column(name = "wa_legal_name")
	private String waLegalName;

	/*
	 * PN: 15 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class)
	@Column(name = "sdl_no")
	private String sdlNo;

	/*
	 * PN: 16 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 17 Region
	 */
	@CSVAnnotation(name = "Region", className = String.class)
	@Column(name = "region")
	private String region;

	/*
	 * PN: 18 SETARegion
	 */
	@CSVAnnotation(name = "SETARegion", className = String.class)
	@Column(name = "seta_region")
	private String setaRegion;

	/*
	 * PN: 19 MagisterialDistrict
	 */
	@CSVAnnotation(name = "MagisterialDistrict", className = String.class)
	@Column(name = "magisterial_district")
	private String magisterialDistrict;

	/*
	 * PN: 20 ChamberCode
	 */
	@CSVAnnotation(name = "ChamberCode", className = String.class)
	@Column(name = "chamber_code")
	private String chamberCode;

	/*
	 * PN: 21 N2
	 */
	@CSVAnnotation(name = "N2", className = String.class)
	@Column(name = "n_two")
	private String nTwo;

	/*
	 * PN: 22 MinTraining
	 */
	@CSVAnnotation(name = "MinTraining", className = String.class)
	@Column(name = "min_training")
	private String minTraining;

	/*
	 * PN: 23 DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_created")
	private String dateCreated;

	/*
	 * PN: 24 DateLastChanged
	 */
	@CSVAnnotation(name = "DateLastChanged", className = String.class)
	@Column(name = "date_last_changed")
	private String dateLastChanged;

	/*
	 * PN: 25 CertificateNumber
	 */
	@CSVAnnotation(name = "CertificateNumber", className = String.class)
	@Column(name = "certificate_number")
	private String certificateNumber;

	/*
	 * PN: 26 DateCertificateCreated
	 */
	@CSVAnnotation(name = "DateCertificateCreated", className = String.class)
	@Column(name = "date_certificate_created")
	private String dateCertificateCreated;

	/*
	 * PN: 27 DGTag
	 */
	@CSVAnnotation(name = "DGTag", className = String.class)
	@Column(name = "dg_tag")
	private String dgTag;

	/*
	 * PN: 28 FundingStatus
	 */
	@CSVAnnotation(name = "FundingStatus", className = String.class)
	@Column(name = "funding_status")
	private String fundingStatus;

	/*
	 * PN: 29 MunicipalityName
	 */
	@CSVAnnotation(name = "MunicipalityName", className = String.class)
	@Column(name = "municipality_name")
	private String municipalityName;

	/*
	 * PN: 30 SocioEcoStatusDesc
	 */
	@CSVAnnotation(name = "SocioEcoStatusDesc", className = String.class)
	@Column(name = "socio_eco_status_desc")
	private String socioEcoStatusDesc;

	@CSVAnnotation(name = "SAQA_Code", className = String.class, lookupField = "qualification")
	@Column(name = "saqa_code")
	private String saqaCode;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findQualification", paramClass = String.class)
	private Qualification qualification;
	
	@Column(name = "created_company_learner")
	private Boolean createdCompanyLearner;
	
	@CSVAnnotation(name = "DateApplicationSent", className = String.class)
	@Column(name = "DateApplicationSent")
	private String dateApplicationSent;
	
	@CSVAnnotation(name = "WorkshopNo", className = String.class)
	@Column(name = "work_shop_no")
	private String workshopNo;
	
	@CSVAnnotation(name = "TestDateFrom", className = String.class)
	@Column(name = "test_date_from")
	private String testDateFrom;

	/*
	 * PN: 17 TestDateTo
	 */
	@CSVAnnotation(name = "TestDateTo", className = String.class)
	@Column(name = "test_date_to")
	private String testDateTo;
	
	@CSVAnnotation(name = "SerialNumber", className = String.class)
	@Column(name = "serial_number")
	private String serialNumber;
	
	@CSVAnnotation(name = "OFOCode", className = String.class, lookupField = "ofoCodes")
	@Column(name = "ofo_code")
	private String ofoCode;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findOfoCodeAndSpecialisation", paramClass = String.class)
	private OfoCodes ofoCodes;
	
	@CSVAnnotation(name = "OFODescription", className = String.class)
	@Column(name = "ofo_description")
	private String ofoDescription;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer_id", nullable = true)
	private Company employer;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "company_id", nullable = true)
	private Company company;
	
	@Transient
	private List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetestList;

	public LegacySECTTwentyEight() {
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
		LegacySECTTwentyEight other = (LegacySECTTwentyEight) obj;
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

	public String getApprenticeshipID() {
		return apprenticeshipID;
	}

	public void setApprenticeshipID(String apprenticeshipID) {
		this.apprenticeshipID = apprenticeshipID;
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

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getLearnerStatusEffectiveDate() {
		return learnerStatusEffectiveDate;
	}

	public void setLearnerStatusEffectiveDate(String learnerStatusEffectiveDate) {
		this.learnerStatusEffectiveDate = learnerStatusEffectiveDate;
	}

	public String getApprenticeshipStartDate() {
		return apprenticeshipStartDate;
	}

	public void setApprenticeshipStartDate(String apprenticeshipStartDate) {
		this.apprenticeshipStartDate = apprenticeshipStartDate;
	}

	public String getApprenticeshipStatusEffectiveDate() {
		return apprenticeshipStatusEffectiveDate;
	}

	public void setApprenticeshipStatusEffectiveDate(String apprenticeshipStatusEffectiveDate) {
		this.apprenticeshipStatusEffectiveDate = apprenticeshipStatusEffectiveDate;
	}

	public String getApprenticeshipEndDate() {
		return apprenticeshipEndDate;
	}

	public void setApprenticeshipEndDate(String apprenticeshipEndDate) {
		this.apprenticeshipEndDate = apprenticeshipEndDate;
	}

	public String getTradeDescription() {
		return tradeDescription;
	}

	public void setTradeDescription(String tradeDescription) {
		this.tradeDescription = tradeDescription;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCurrentPreviousEmp() {
		return currentPreviousEmp;
	}

	public void setCurrentPreviousEmp(String currentPreviousEmp) {
		this.currentPreviousEmp = currentPreviousEmp;
	}

	public String getWasdl() {
		return wasdl;
	}

	public void setWasdl(String wasdl) {
		this.wasdl = wasdl;
	}

	public String getWaLegalName() {
		return waLegalName;
	}

	public void setWaLegalName(String waLegalName) {
		this.waLegalName = waLegalName;
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

	public String getChamberCode() {
		return chamberCode;
	}

	public void setChamberCode(String chamberCode) {
		this.chamberCode = chamberCode;
	}

	public String getnTwo() {
		return nTwo;
	}

	public void setnTwo(String nTwo) {
		this.nTwo = nTwo;
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

	public String getCertificateNumber() {
		return certificateNumber;
	}

	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}

	public String getDateCertificateCreated() {
		return dateCertificateCreated;
	}

	public void setDateCertificateCreated(String dateCertificateCreated) {
		this.dateCertificateCreated = dateCertificateCreated;
	}

	public String getDgTag() {
		return dgTag;
	}

	public void setDgTag(String dgTag) {
		this.dgTag = dgTag;
	}

	public String getFundingStatus() {
		return fundingStatus;
	}

	public void setFundingStatus(String fundingStatus) {
		this.fundingStatus = fundingStatus;
	}

	public String getMunicipalityName() {
		return municipalityName;
	}

	public void setMunicipalityName(String municipalityName) {
		this.municipalityName = municipalityName;
	}

	public String getSocioEcoStatusDesc() {
		return socioEcoStatusDesc;
	}

	public void setSocioEcoStatusDesc(String socioEcoStatusDesc) {
		this.socioEcoStatusDesc = socioEcoStatusDesc;
	}

	public String getSaqaCode() {
		return saqaCode;
	}

	public void setSaqaCode(String saqaCode) {
		this.saqaCode = saqaCode;
	}

	public Boolean getAppearsOnHomeAffairsData() {
		return appearsOnHomeAffairsData;
	}

	public void setAppearsOnHomeAffairsData(Boolean appearsOnHomeAffairsData) {
		this.appearsOnHomeAffairsData = appearsOnHomeAffairsData;
	}

	public String getIdNoTransient() {
		return idNoTransient;
	}

	public void setIdNoTransient(String idNoTransient) {
		this.idNoTransient = idNoTransient;
	}

	public Boolean getValidRsaIdNumber() {
		return validRsaIdNumber;
	}

	public void setValidRsaIdNumber(Boolean validRsaIdNumber) {
		this.validRsaIdNumber = validRsaIdNumber;
	}

	public Boolean getLegacyOrganisationSites() {
		return legacyOrganisationSites;
	}

	public void setLegacyOrganisationSites(Boolean legacyOrganisationSites) {
		this.legacyOrganisationSites = legacyOrganisationSites;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public List<LegacySectionTwentyEightTradeTest> getLegacysectiontwentyeighttradetestList() {
		return legacysectiontwentyeighttradetestList;
	}

	public void setLegacysectiontwentyeighttradetestList(
			List<LegacySectionTwentyEightTradeTest> legacysectiontwentyeighttradetestList) {
		this.legacysectiontwentyeighttradetestList = legacysectiontwentyeighttradetestList;
	}

	public Boolean getCreatedCompanyLearner() {
		return createdCompanyLearner;
	}

	public void setCreatedCompanyLearner(Boolean createdCompanyLearner) {
		this.createdCompanyLearner = createdCompanyLearner;
	}
	public String getDateApplicationSent() {
		return dateApplicationSent;
	}

	public String getWorkshopNo() {
		return workshopNo;
	}

	public String getTestDateFrom() {
		return testDateFrom;
	}

	public String getTestDateTo() {
		return testDateTo;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public OfoCodes getOfoCodes() {
		return ofoCodes;
	}

	public String getOfoDescription() {
		return ofoDescription;
	}

	public void setDateApplicationSent(String dateApplicationSent) {
		this.dateApplicationSent = dateApplicationSent;
	}

	public void setWorkshopNo(String workshopNo) {
		this.workshopNo = workshopNo;
	}

	public void setTestDateFrom(String testDateFrom) {
		this.testDateFrom = testDateFrom;
	}

	public void setTestDateTo(String testDateTo) {
		this.testDateTo = testDateTo;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public void setOfoCodes(OfoCodes ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public void setOfoDescription(String ofoDescription) {
		this.ofoDescription = ofoDescription;
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
