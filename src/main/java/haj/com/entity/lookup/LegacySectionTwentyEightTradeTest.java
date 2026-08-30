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
import haj.com.entity.OfoCodes;
import haj.com.framework.AbstractLookup;
import haj.com.service.lookup.HomeAffairsService;

/**
 * TO BE DELETED
 * 
 * Excel name: Section 28 Trade Test.xlsx Tab Name: Trade Test
 */
@Entity
@Table(name = "legacy_section_twenty_eight_trade_test")
// site info
@LegacyReportingAnnotation(name = "Total Apprenticeship Linked to Site", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Apprenticeship not Linked to Site", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.legacyOrganisationSites = false", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySectionTwentyEightTradeTest o where o.legacyOrganisationSites = false", key = "Apprenticeship not Linked Site", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// id info
@LegacyReportingAnnotation(name = "Total Valid ID Numbers", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.validRsaIdNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Invalid ID Numbers", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.validRsaIdNumber = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.idNO in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total ID Numbers not on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.idNO not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySectionTwentyEightTradeTest o where o.validRsaIdNumber = false", key = "Invalid ID Numbers", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacySectionTwentyEightTradeTest o where o.appearsOnHomeAffairsData = false", key = "ID Numbers not on Home Affairs", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacySectionTwentyEightTradeTest o where o.idNO not in (select distinct x.idNo from LegacyPerson x)", key = "ID Numbers not on Person File", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// ofoCodes info
@LegacyReportingAnnotation(name = "Total Linked Ofo Codes", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.ofoCodes is not null", key = "count")
@LegacyReportingAnnotation(name = "Total Non Linked Ofo Codes", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.ofoCodes is null", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacySectionTwentyEightTradeTest o where o.ofoCodes is null", key = "Non Linked Ofo Codes", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Home Affairs", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and  o.appearsOnHomeAffairsData = false", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Home Affairs", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and  o.appearsOnHomeAffairsData = false", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Home Affairs", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and  o.appearsOnHomeAffairsData = false", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Home Affairs", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and  o.appearsOnHomeAffairsData = false", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and o.idNO in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and o.idNO not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and o.idNO not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and o.idNO in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and o.idNO not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and o.idNO not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and o.idNO in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and o.idNO not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and o.idNO not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and o.idNO in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and o.idNO not in (select distinct x.idNo from LegacyPerson x)", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and o.idNO not in (select distinct x.idNo from LegacyPerson x)", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Application
@LegacyReportingAnnotation(name = "Total Application Status on Person File and Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and o.idNO in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Application Status not on Person File and Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and o.idNO not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Application Status not on Person File and Home Affairs", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Application' and o.idNO not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Externally Loaded
@LegacyReportingAnnotation(name = "Total Externally Loaded Status on Person File and Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and o.idNO in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Externally Loaded Status not on Person File and Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and o.idNO not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Externally Loaded Status not on Person File and Home Affairs", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Externally Loaded' and o.idNO not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Qualification Obtained
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status on Person File and Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and o.idNO in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Qualification Obtained Status not on Person File and Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and o.idNO not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Qualification Obtained Status not on Person File and Home Affairs", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Qualification Obtained' and o.idNO not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
// Registered
@LegacyReportingAnnotation(name = "Total Registered Status on Person File and Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and o.idNO in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = true", key = "count")
@LegacyReportingAnnotation(name = "Total Registered Status not on Person File and Home Affairs", query = "select count(o) from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and o.idNO not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", key = "count")
@LegacyReportingAnnotation(key = "Registered Status not on Person File and Home Affairs", query = "select o from LegacySectionTwentyEightTradeTest o where o.contractStatus = 'Registered' and o.idNO not in (select distinct x.idNo from LegacyPerson x) and  o.appearsOnHomeAffairsData = false", returnType = LegacySectionTwentyEightTradeTest.class, singleResult = false)
public class LegacySectionTwentyEightTradeTest extends AbstractLookup {

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
	 * PN: 1 TradeTestID
	 */
	@CSVAnnotation(name = "TradeTestID", className = String.class)
	@Column(name = "trade_test_id")
	private String tradeTestID;

	/*
	 * PN: 2 IDNO
	 */
	@CSVAnnotation(name = "IDNO", className = String.class, lookupField = "appearsOnHomeAffairsData")
	@Column(name = "id_no")
	private String idNO;

	@CSVAnnotation(name = "IDNO", className = String.class, lookupField = "validRsaIdNumber")
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
	 * PN: 4 Surname
	 */
	@CSVAnnotation(name = "Surname", className = String.class)
	@Column(name = "surname")
	private String surname;

	/*
	 * PN: 5 TradeDescription
	 */
	@CSVAnnotation(name = "TradeDescription", className = String.class)
	@Column(name = "trade_description")
	private String tradeDescription;

	/*
	 * PN: 6 OFOCode
	 */
	@CSVAnnotation(name = "OFOCode", className = String.class, lookupField = "ofoCodes")
	@Column(name = "ofo_code")
	private String ofoCode;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ofo_codes_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findOfoCodeAndSpecialisation", paramClass = String.class)
	private OfoCodes ofoCodes;

	/*
	 * PN: 7 OFODescription
	 */
	@CSVAnnotation(name = "OFODescription", className = String.class)
	@Column(name = "ofo_description")
	private String ofoDescription;

	/*
	 * PN: 8 ContractStatus
	 */
	@CSVAnnotation(name = "ContractStatus", className = String.class)
	@Column(name = "contract_status")
	private String contractStatus;

	/*
	 * PN: 9 DateApplicationReceived
	 */
	@CSVAnnotation(name = "DateApplicationReceived", className = String.class)
	@Column(name = "date_application_received")
	private String dateApplicationReceived;

	/*
	 * PN: 10 DateApplicationSent
	 */
	@CSVAnnotation(name = "DateApplicationSent", className = String.class)
	@Column(name = "DateApplicationSent")
	private String dateApplicationSent;

	/*
	 * PN: 11 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class, lookupField = "legacyOrganisationSites")
	@Column(name = "sdl_no")
	private String sdlNo;

	@Column(name = "legacy_organisation_sites")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLegacyOrganisationSites", paramClass = String.class)
	private Boolean legacyOrganisationSites;

	/*
	 * PN: 12 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 13 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 14 Status
	 */
	@CSVAnnotation(name = "Status", className = String.class)
	@Column(name = "status")
	private String status;

	/*
	 * PN: 15 WorkshopNo
	 */
	@CSVAnnotation(name = "WorkshopNo", className = String.class)
	@Column(name = "work_shop_no")
	private String workshopNo;

	/*
	 * PN: 16 TestDateFrom
	 */
	@CSVAnnotation(name = "TestDateFrom", className = String.class)
	@Column(name = "test_date_from")
	private String testDateFrom;

	/*
	 * PN: 17 TestDateTo
	 */
	@CSVAnnotation(name = "TestDateTo", className = String.class)
	@Column(name = "test_date_to")
	private String testDateTo;

	/*
	 * PN: 18 SerialNumber
	 */
	@CSVAnnotation(name = "SerialNumber", className = String.class)
	@Column(name = "serial_number")
	private String serialNumber;

	/*
	 * PN: 19 NumberOfTestAttempts
	 */
	@CSVAnnotation(name = "NumberOfTestAttempts", className = String.class)
	@Column(name = "number_of_test_attempts")
	private String numberOfTestAttempts;

	/*
	 * PN: 20 TradeTestLevel
	 */
	@CSVAnnotation(name = "TradeTestLevel", className = String.class)
	@Column(name = "TradeTestLevel")
	private String tradeTestLevel;

	/*
	 * PN: 21 MinTraining
	 */
	@CSVAnnotation(name = "MinTraining", className = String.class)
	@Column(name = "min_training")
	private String minTraining;

	/*
	 * PN: 22 N2Exempt
	 */
	@CSVAnnotation(name = "N2Exempt", className = String.class)
	@Column(name = "n2_exempt")
	private String n2Exempt;

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
	
	@CSVAnnotation(name = "SAQA_Code", className = String.class, lookupField = "qualification")
	@Column(name = "saqa_code")
	private String saqaCode;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findQualification", paramClass = String.class)
	private Qualification qualification;

	public LegacySectionTwentyEightTradeTest() {
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
		LegacySectionTwentyEightTradeTest other = (LegacySectionTwentyEightTradeTest) obj;
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

	public String getTradeTestID() {
		return tradeTestID;
	}

	public void setTradeTestID(String tradeTestID) {
		this.tradeTestID = tradeTestID;
	}

	public String getIdNO() {
		return idNO;
	}

	public void setIdNO(String idNO) {
		this.idNO = idNO;
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

	public String getTradeDescription() {
		return tradeDescription;
	}

	public void setTradeDescription(String tradeDescription) {
		this.tradeDescription = tradeDescription;
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

	public String getContractStatus() {
		return contractStatus;
	}

	public void setContractStatus(String contractStatus) {
		this.contractStatus = contractStatus;
	}

	public String getDateApplicationReceived() {
		return dateApplicationReceived;
	}

	public void setDateApplicationReceived(String dateApplicationReceived) {
		this.dateApplicationReceived = dateApplicationReceived;
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

	public String getOrganisationNameTrade() {
		return organisationNameTrade;
	}

	public void setOrganisationNameTrade(String organisationNameTrade) {
		this.organisationNameTrade = organisationNameTrade;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getWorkshopNo() {
		return workshopNo;
	}

	public void setWorkshopNo(String workshopNo) {
		this.workshopNo = workshopNo;
	}

	public String getTestDateFrom() {
		return testDateFrom;
	}

	public void setTestDateFrom(String testDateFrom) {
		this.testDateFrom = testDateFrom;
	}

	public String getTestDateTo() {
		return testDateTo;
	}

	public void setTestDateTo(String testDateTo) {
		this.testDateTo = testDateTo;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getNumberOfTestAttempts() {
		return numberOfTestAttempts;
	}

	public void setNumberOfTestAttempts(String numberOfTestAttempts) {
		this.numberOfTestAttempts = numberOfTestAttempts;
	}

	public String getMinTraining() {
		return minTraining;
	}

	public void setMinTraining(String minTraining) {
		this.minTraining = minTraining;
	}

	public String getN2Exempt() {
		return n2Exempt;
	}

	public void setN2Exempt(String n2Exempt) {
		this.n2Exempt = n2Exempt;
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

	public String getDateApplicationSent() {
		return dateApplicationSent;
	}

	public void setDateApplicationSent(String dateApplicationSent) {
		this.dateApplicationSent = dateApplicationSent;
	}

	public String getTradeTestLevel() {
		return tradeTestLevel;
	}

	public void setTradeTestLevel(String tradeTestLevel) {
		this.tradeTestLevel = tradeTestLevel;
	}

	public String getSaqaCode() {
		return saqaCode;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setSaqaCode(String saqaCode) {
		this.saqaCode = saqaCode;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

}
