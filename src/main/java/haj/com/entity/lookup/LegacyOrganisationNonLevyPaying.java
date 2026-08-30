package haj.com.entity.lookup;

import static javax.persistence.GenerationType.IDENTITY;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

/**
 * Excel name: Organisation.xlsx
 * Tab Name: NonLevyPaying
 *
 * @author Techfinium
 */
@Entity
@Table(name = "legacy_organisation_non_levy_paying")
public class LegacyOrganisationNonLevyPaying extends AbstractLookup {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** Unique Id of Accreditation. */
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	/*
	 * PN: 1
	 * OrganisationID 
	 */
	@CSVAnnotation(name = "OrganisationID", className = String.class)
	@Column(name = "organisation_id", columnDefinition ="LONGTEXT")
	private String organisationId;
	
	/* 
	 * PN: 2
	 * SDL_Number
	 */
	@CSVAnnotation(name = "SDL_Number", className = String.class)
	@Column(name = "sdl_number", columnDefinition ="LONGTEXT")
	private String sdlNumber;
	
	
	/*
	 * PN: 3
	 * Linked_SDL 
	 */
	@CSVAnnotation(name = "Linked_SDL", className = String.class)
	@Column(name = "linked_SDL" , columnDefinition ="LONGTEXT")
	private String linkedSdl;
	
	/* 
	 * PN: 4
	 * MainSDL
	 */
	@CSVAnnotation(name = "MainSDL", className = String.class)
	@Column(name = "main_SDL", columnDefinition ="LONGTEXT" ) 
	private String mainSDL;
	
	
	/*
	 * PN: 5
	 * OrganisationNameLegal 
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal", columnDefinition ="LONGTEXT" ) 
	private String organisationNameLegal;
	
	/* 
	 * PN: 6
	 * OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade", columnDefinition ="LONGTEXT" ) 
	private String organisationNameTrade;
	
	
	/*
	 * PN: 7
	 * LinkedOrganisationID 
	 */
	@CSVAnnotation(name = "LinkedOrganisationID", className = String.class)
	@Column(name = "linked_organisation_id", columnDefinition ="LONGTEXT" ) 
	private String linkedOrganisationID;
	
	/* 
	 * PN: 8
	 * Linked_SDL_Number
	 */
	@CSVAnnotation(name = "Linked_SDL_Number", className = String.class)
	@Column(name = "linked_sdl_number", columnDefinition ="LONGTEXT" ) 
	private String linkedSdlNumber;
	
	
	/*
	 * PN: 9
	 * LegalStatus 
	 */
	@CSVAnnotation(name = "LegalStatus", className = String.class)
	@Column(name = "legal_status", columnDefinition ="LONGTEXT" ) 
	private String legalStatus;
	
	/* 
	 * PN: 10
	 * SicCode
	 */
	@CSVAnnotation(name = "SicCode", className = String.class)
	@Column(name = "sic_code", columnDefinition ="LONGTEXT" ) 
	private String SicCode;
	
	
	/*
	 * PN: 11
	 * SicCodeDescription 
	 */
	@CSVAnnotation(name = "SicCodeDescription", className = String.class)
	@Column(name = "sic_code_description", columnDefinition ="LONGTEXT" ) 
	private String sicCodeDescription;
	
	/* 
	 * PN: 12
	 * ChamberDescription
	 */
	@CSVAnnotation(name = "ChamberDescription", className = String.class)
	@Column(name = "chamber_description", columnDefinition ="LONGTEXT" ) 
	private String chamberDescription;
	
	
	/*
	 * PN: 13
	 * MainBusinessActivity 
	 */
	@CSVAnnotation(name = "MainBusinessActivity", className = String.class)
	@Column(name = "main_business_activity", columnDefinition ="LONGTEXT" ) 
	private String mainBusinessActivity;
	
	/* 
	 * PN: 14
	 * Phone
	 */
	@CSVAnnotation(name = "Phone", className = String.class)
	@Column(name = "phone", columnDefinition ="LONGTEXT" ) 
	private String phone;
	
	/* 
	 * PN: 15
	 * Fax
	 */
	@CSVAnnotation(name = "Fax", className = String.class)
	@Column(name = "fax", columnDefinition ="LONGTEXT" ) 
	private String fax;
	
	/* 
	 * PN: 16
	 * EmailAddress
	 */
	@CSVAnnotation(name = "EmailAddress", className = String.class)
	@Column(name = "email_address", columnDefinition ="LONGTEXT" ) 
	private String emailAddress;
	
	/* 
	 * PN: 17
	 * Website_URL
	 */
	@CSVAnnotation(name = "Website_URL", className = String.class)
	@Column(name = "website_URL", columnDefinition ="LONGTEXT" ) 
	private String websiteURL;
	
	/* 
	 * PN: 18
	 * SARSRegistrationNo
	 */
	@CSVAnnotation(name = "SARSRegistrationNo", className = String.class)
	@Column(name = "sars_registration_no", columnDefinition ="LONGTEXT" ) 
	private String sarsRegistrationNo;
	
	/* 
	 * PN: 19
	 * CompanyRegistrationNo
	 */
	@CSVAnnotation(name = "CompanyRegistrationNo", className = String.class)
	@Column(name = "company_registration_no", columnDefinition ="LONGTEXT" ) 
	private String companyRegistrationNo;
	
	/* 
	 * PN: 20
	 * Payroll
	 */
	@CSVAnnotation(name = "Payroll", className = String.class)
	@Column(name = "payroll", columnDefinition ="LONGTEXT" ) 
	private String payroll;
	
	/* 
	 * PN: 21
	 * GeographicAreaDesc
	 */
	@CSVAnnotation(name = "GeographicAreaDesc", className = String.class)
	@Column(name = "geographic_area_desc", columnDefinition ="LONGTEXT" ) 
	private String geographicAreaDesc;
	
	/* 
	 * PN: 22
	 * TotalEmployment
	 */
	@CSVAnnotation(name = "TotalEmployment", className = String.class)
	@Column(name = "total_employment", columnDefinition ="LONGTEXT" ) 
	private String totalEmployment;
	
	/* 
	 * PN: 23
	 * AnnualTurnover
	 */
	@CSVAnnotation(name = "AnnualTurnover", className = String.class)
	@Column(name = "annual_turnover", columnDefinition ="LONGTEXT" ) 
	private String annualTurnover;
	
	/* 
	 * PN: 24
	 * CompanyRegistrationNo2
	 */
	@CSVAnnotation(name = "CompanyRegistrationNo2", className = String.class)
	@Column(name = "company_registration_no2", columnDefinition ="LONGTEXT" ) 
	private String companyRegistrationNo2;
	
	/* 
	 * PN: 25
	 * MainBusinessActivity2
	 */
	@CSVAnnotation(name = "MainBusinessActivity2", className = String.class)
	@Column(name = "main_business_activity_two", columnDefinition ="LONGTEXT" ) 
	private String mainBusinessActivityTwo;
	
	/* 
	 * PN: 26
	 * DeleteStatus
	 */
	@CSVAnnotation(name = "DeleteStatus", className = String.class)
	@Column(name = "delete_status", columnDefinition ="LONGTEXT" ) 
	private String deleteStatus;
	
	/* 
	 * PN: 27
	 * OrganisationStatusEffectiveDate
	 */
	@CSVAnnotation(name = "OrganisationStatusEffectiveDate", className = String.class)
	@Column(name = "organisation_status_effective_date", columnDefinition ="LONGTEXT" ) 
	private String organisationStatusEffectiveDate;
	
	/* 
	 * PN: 28
	 * CompRegYear
	 */
	@CSVAnnotation(name = "CompRegYear", className = String.class)
	@Column(name = "comp_reg_year", columnDefinition ="LONGTEXT" ) 
	private String compRegYear;
	
	/* 
	 * PN: 29
	 * CompanyYearsTrading
	 */
	@CSVAnnotation(name = "CompanyYearsTrading", className = String.class)
	@Column(name = "company_years_trading", columnDefinition ="LONGTEXT" ) 
	private String companyYearsTrading;
	
	/* 
	 * PN: 30
	 * CommunicationStatus
	 */
	@CSVAnnotation(name = "CommunicationStatus", className = String.class)
	@Column(name = "communication_status", columnDefinition ="LONGTEXT" ) 
	private String communicationStatus;
	
	/* 
	 * PN: 31
	 * FinancialYearStart
	 */
	@CSVAnnotation(name = "FinancialYearStart", className = String.class)
	@Column(name = "financial_year_start", columnDefinition ="LONGTEXT" ) 
	private String financialYearStart;
	
	/* 
	 * PN: 32
	 * FinancialYearEnd
	 */
	@CSVAnnotation(name = "FinancialYearEnd", className = String.class)
	@Column(name = "financial_yearEnd", columnDefinition ="LONGTEXT" ) 
	private String financialYearEnd;
	
	/* 
	 * PN: 33
	 * DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_created", columnDefinition ="LONGTEXT" ) 
	private String dateCreated;
	
	/* 
	 * PN: 34
	 * LastDateChanged
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed", columnDefinition ="LONGTEXT" ) 
	private String lastDateChanged;
	
	/* 
	 * PN: 35
	 * PossibleSDLNo
	 */
	@CSVAnnotation(name = "PossibleSDLNo", className = String.class)
	@Column(name = "possible_sdl_no", columnDefinition ="LONGTEXT" ) 
	private String possibleSDLNo;
	
	/* 
	 * PN: 36
	 * NQFLevel
	 */
	@CSVAnnotation(name = "NQFLevel", className = String.class)
	@Column(name = "nqf_Level", columnDefinition ="LONGTEXT" ) 
	private String nqfLevel;
	
	/* 
	 * PN: 37
	 * RegistrationYear
	 */
	@CSVAnnotation(name = "RegistrationYear", className = String.class)
	@Column(name = "registration_year", columnDefinition ="LONGTEXT" ) 
	private String registrationYear;
	
	/* 
	 * PN: 38
	 * YearsTrading
	 */
	@CSVAnnotation(name = "YearsTrading", className = String.class)
	@Column(name = "years_trading", columnDefinition ="LONGTEXT" ) 
	private String yearsTrading;
	
	/* 
	 * PN: 39
	 * InterSetaTransfer
	 */
	@CSVAnnotation(name = "InterSetaTransfer", className = String.class)
	@Column(name = "inter_seta_transfer", columnDefinition ="LONGTEXT" ) 
	private String interSetaTransfer;
	
	/* 
	 * PN: 40
	 * SETAStatus
	 */
	@CSVAnnotation(name = "SETAStatus", className = String.class)
	@Column(name = "seta__status", columnDefinition ="LONGTEXT" ) 
	private String setaStatus;
	
	/* 
	 * PN: 41
	 * CoreBusinessDesc
	 */
	@CSVAnnotation(name = "CoreBusinessDesc", className = String.class)
	@Column(name = "core_business_desc", columnDefinition ="LONGTEXT" ) 
	private String coreBusinessDesc;
	
	/* 
	 * PN: 42
	 * Sector
	 */
	@CSVAnnotation(name = "Sector", className = String.class)
	@Column(name = "sector", columnDefinition ="LONGTEXT" ) 
	private String sector;
	
	/* 
	 * PN: 43
	 * IsCBO
	 */
	@CSVAnnotation(name = "IsCBO", className = String.class)
	@Column(name = "is_cbo", columnDefinition ="LONGTEXT" ) 
	private String isCBO;
	
	/* 
	 * PN: 44
	 * IsNGO
	 */
	@CSVAnnotation(name = "IsNGO", className = String.class)
	@Column(name = "is_ngo", columnDefinition ="LONGTEXT" ) 
	private String isNGO;
	
	/* 
	 * PN: 45
	 * IsS21
	 */
	@CSVAnnotation(name = "IsS21", className = String.class)
	@Column(name = "is_S_twenty_one", columnDefinition ="LONGTEXT" ) 
	private String isStwenty_one;
	
	/* 
	 * PN: 46
	 * IsBEE
	 */
	@CSVAnnotation(name = "IsBEE", className = String.class)
	@Column(name = "is_BEE", columnDefinition ="LONGTEXT" ) 
	private String isBEE;
	
	/* 
	 * PN: 47
	 * SubSector
	 */
	@CSVAnnotation(name = "SubSector", className = String.class)
	@Column(name = "sub_sector", columnDefinition ="LONGTEXT" ) 
	private String subSector;
	
	/* 
	 * PN: 48
	 * NonLevyNumber
	 */
	@CSVAnnotation(name = "NonLevyNumber", className = String.class)
	@Column(name = "non_levy_number", columnDefinition ="LONGTEXT" ) 
	private String nonLevyNumber;
	
	/* 
	 * PN: 49
	 * UIFNumber
	 */
	@CSVAnnotation(name = "UIFNumber", className = String.class)
	@Column(name = "uif_number", columnDefinition ="LONGTEXT" ) 
	private String uifNumber;
	
	/* 
	 * PN: 50
	 * EntityType
	 */
	@CSVAnnotation(name = "EntityType", className = String.class)
	@Column(name = "entity_type", columnDefinition ="LONGTEXT" ) 
	private String entityType;
	
	/* 
	 * PN: 51
	 * PAYENo
	 */
	@CSVAnnotation(name = "PAYENo", className = String.class)
	@Column(name = "paye_no", columnDefinition ="LONGTEXT" ) 
	private String payeNo;
	
	/* 
	 * PN: 52
	 * BBBEE1
	 */
	@CSVAnnotation(name = "BBBEE1", className = String.class)
	@Column(name = "bbbee_one", columnDefinition ="LONGTEXT" ) 
	private String bbbeeOne;
	
	/* 
	 * PN: 53
	 * BBBEE2
	 */
	@CSVAnnotation(name = "BBBEE2", className = String.class)
	@Column(name = "bbbee_two", columnDefinition ="LONGTEXT" ) 
	private String bbbeeTwo;
	
	/* 
	 * PN: 54
	 * BBBEE3
	 */
	@CSVAnnotation(name = "BBBEE3", className = String.class)
	@Column(name = "bbbee_three", columnDefinition ="LONGTEXT" ) 
	private String bbbeeThree;
	
	/* 
	 * PN: 55
	 * BBBEE4
	 */
	@CSVAnnotation(name = "BBBEE4", className = String.class)
	@Column(name = "bbbee_four", columnDefinition ="LONGTEXT" ) 
	private String bbbeeFour;
	
	/* 
	 * PN: 56
	 * RegCiproNo
	 */
	@CSVAnnotation(name = "RegCiproNo", className = String.class)
	@Column(name = "reg_cipro_no", columnDefinition ="LONGTEXT" ) 
	private String regCiproNo;
	
	/* 
	 * PN: 57
	 * CollegeId
	 */
	@CSVAnnotation(name = "CollegeId", className = String.class)
	@Column(name = "college_Id", columnDefinition ="LONGTEXT" ) 
	private String collegeId;
	
	/* 
	 * PN: 58
	 * Institution
	 */
	@CSVAnnotation(name = "Institution", className = String.class)
	@Column(name = "institution", columnDefinition ="LONGTEXT" ) 
	private String institution;
	
	/* 
	 * PN: 59
	 * PartnershipDesc
	 */
	@CSVAnnotation(name = "PartnershipDesc", className = String.class)
	@Column(name = "partnership_desc", columnDefinition ="LONGTEXT" ) 
	private String partnershipDesc;
	
	/* 
	 * PN: 60
	 * MagisterialDistrict
	 */
	@CSVAnnotation(name = "MagisterialDistrict", className = String.class)
	@Column(name = "magisterial_district", columnDefinition ="LONGTEXT" ) 
	private String magisterialDistrict;
	
	/* 
	 * PN: 61
	 * OrgCatSize
	 */
	@CSVAnnotation(name = "OrgCatSize", className = String.class)
	@Column(name = "org_cat_size", columnDefinition ="LONGTEXT" ) 
	private String orgCatSize;
	
	/* 
	 * PN: 62
	 * Region
	 */
	@CSVAnnotation(name = "Region", className = String.class)
	@Column(name = "region", columnDefinition ="LONGTEXT" ) 
	private String region;
	
	/* 
	 * PN: 63
	 * OrgCategoryDesc
	 */
	@CSVAnnotation(name = "OrgCategoryDesc", className = String.class)
	@Column(name = "org_category_desc", columnDefinition ="LONGTEXT" ) 
	private String orgCategoryDesc;
	
	/* 
	 * PN: 64
	 * UnionAgreement
	 */
	@CSVAnnotation(name = "UnionAgreement", className = String.class)
	@Column(name = "union_agreement", columnDefinition ="LONGTEXT" ) 
	private String unionAgreement;
	
	/* 
	 * PN: 65
	 * Description
	 */
	@CSVAnnotation(name = "Description", className = String.class)
	@Column(name = "description", columnDefinition ="LONGTEXT" ) 
	private String description;
	
	/* 
	 * PN: 66
	 * IsNew
	 */
	@CSVAnnotation(name = "IsNew", className = String.class)
	@Column(name = "is_new", columnDefinition ="LONGTEXT" ) 
	private String isNew;
	
	/* 
	 * PN: 67
	 * RegDate
	 */
	@CSVAnnotation(name = "RegDate", className = String.class)
	@Column(name = "reg_date", columnDefinition ="LONGTEXT" ) 
	private String regDate;
	
	/* 
	 * PN: 68
	 * ConfirmSICCode
	 */
	@CSVAnnotation(name = "ConfirmSICCode", className = String.class)
	@Column(name = "confirm_SIC_code", columnDefinition ="LONGTEXT" ) 
	private String confirmSICCode;
	
	/* 
	 * PN: 69
	 * CoreBusiness
	 */
	@CSVAnnotation(name = "CoreBusiness", className = String.class)
	@Column(name = "core_business", columnDefinition ="LONGTEXT" ) 
	private String coreBusiness;
	
	/* 
	 * PN: 70
	 * Province
	 */
	@CSVAnnotation(name = "Province", className = String.class)
	@Column(name = "province", columnDefinition ="LONGTEXT" ) 
	private String province;
	
	/* 
	 * PN: 71
	 * SMS Chamber
	 */
	@CSVAnnotation(name = "SMS_Chamber", className = String.class)
	@Column(name = "sms_chamber", columnDefinition ="LONGTEXT" ) 
	private String smsChamber;
	
	/* 
	 * PN: 72
	 * SMS SIC Code Chamber
	 */
	@CSVAnnotation(name = "SIC_Code_Chamber", className = String.class)
	@Column(name = "sic_code_chamber", columnDefinition ="LONGTEXT" ) 
	private String sicCodeChamber;
	
	@Column(name = "sdl_number_on_sars_employer_file")
	private Boolean sdlNumberOnSarsEmployerFile;
	
	@Column(name = "main_sdl_number_on_sars_employer_file" ) 
	private Boolean mainSdlNumberOnSarsEmployerFile;

	
	/**
	 * Instantiates a new Accreditation.
	 */
	public LegacyOrganisationNonLevyPaying() {
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
		LegacyOrganisationNonLevyPaying other = (LegacyOrganisationNonLevyPaying) obj;
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

	public String getOrganisationId() {
		return organisationId;
	}

	public void setOrganisationId(String organisationId) {
		this.organisationId = organisationId;
	}

	public String getSdlNumber() {
		return sdlNumber;
	}

	public void setSdlNumber(String sdlNumber) {
		this.sdlNumber = sdlNumber;
	}

	public String getLinkedSdl() {
		return linkedSdl;
	}

	public void setLinkedSdl(String linkedSdl) {
		this.linkedSdl = linkedSdl;
	}

	public String getMainSDL() {
		return mainSDL;
	}

	public void setMainSDL(String mainSDL) {
		this.mainSDL = mainSDL;
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

	public String getLinkedOrganisationID() {
		return linkedOrganisationID;
	}

	public void setLinkedOrganisationID(String linkedOrganisationID) {
		this.linkedOrganisationID = linkedOrganisationID;
	}

	public String getLinkedSdlNumber() {
		return linkedSdlNumber;
	}

	public void setLinkedSdlNumber(String linkedSdlNumber) {
		this.linkedSdlNumber = linkedSdlNumber;
	}

	public String getLegalStatus() {
		return legalStatus;
	}

	public void setLegalStatus(String legalStatus) {
		this.legalStatus = legalStatus;
	}

	public String getSicCode() {
		return SicCode;
	}

	public void setSicCode(String sicCode) {
		SicCode = sicCode;
	}

	public String getSicCodeDescription() {
		return sicCodeDescription;
	}

	public void setSicCodeDescription(String sicCodeDescription) {
		this.sicCodeDescription = sicCodeDescription;
	}

	public String getChamberDescription() {
		return chamberDescription;
	}

	public void setChamberDescription(String chamberDescription) {
		this.chamberDescription = chamberDescription;
	}

	public String getMainBusinessActivity() {
		return mainBusinessActivity;
	}

	public void setMainBusinessActivity(String mainBusinessActivity) {
		this.mainBusinessActivity = mainBusinessActivity;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public String getSarsRegistrationNo() {
		return sarsRegistrationNo;
	}

	public void setSarsRegistrationNo(String sarsRegistrationNo) {
		this.sarsRegistrationNo = sarsRegistrationNo;
	}

	public String getCompanyRegistrationNo() {
		return companyRegistrationNo;
	}

	public void setCompanyRegistrationNo(String companyRegistrationNo) {
		this.companyRegistrationNo = companyRegistrationNo;
	}

	public String getPayroll() {
		return payroll;
	}

	public void setPayroll(String payroll) {
		this.payroll = payroll;
	}

	public String getTotalEmployment() {
		return totalEmployment;
	}

	public void setTotalEmployment(String totalEmployment) {
		this.totalEmployment = totalEmployment;
	}

	public String getAnnualTurnover() {
		return annualTurnover;
	}

	public void setAnnualTurnover(String annualTurnover) {
		this.annualTurnover = annualTurnover;
	}

	public String getCompanyRegistrationNo2() {
		return companyRegistrationNo2;
	}

	public void setCompanyRegistrationNo2(String companyRegistrationNo2) {
		this.companyRegistrationNo2 = companyRegistrationNo2;
	}

	public String getDeleteStatus() {
		return deleteStatus;
	}

	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}

	public String getOrganisationStatusEffectiveDate() {
		return organisationStatusEffectiveDate;
	}

	public void setOrganisationStatusEffectiveDate(String organisationStatusEffectiveDate) {
		this.organisationStatusEffectiveDate = organisationStatusEffectiveDate;
	}

	public String getCompRegYear() {
		return compRegYear;
	}

	public void setCompRegYear(String compRegYear) {
		this.compRegYear = compRegYear;
	}

	public String getCompanyYearsTrading() {
		return companyYearsTrading;
	}

	public void setCompanyYearsTrading(String companyYearsTrading) {
		this.companyYearsTrading = companyYearsTrading;
	}

	public String getCommunicationStatus() {
		return communicationStatus;
	}

	public void setCommunicationStatus(String communicationStatus) {
		this.communicationStatus = communicationStatus;
	}

	public String getFinancialYearStart() {
		return financialYearStart;
	}

	public void setFinancialYearStart(String financialYearStart) {
		this.financialYearStart = financialYearStart;
	}

	public String getFinancialYearEnd() {
		return financialYearEnd;
	}

	public void setFinancialYearEnd(String financialYearEnd) {
		this.financialYearEnd = financialYearEnd;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
	}

	public String getPossibleSDLNo() {
		return possibleSDLNo;
	}

	public void setPossibleSDLNo(String possibleSDLNo) {
		this.possibleSDLNo = possibleSDLNo;
	}

	public String getNqfLevel() {
		return nqfLevel;
	}

	public void setNqfLevel(String nqfLevel) {
		this.nqfLevel = nqfLevel;
	}

	public String getRegistrationYear() {
		return registrationYear;
	}

	public void setRegistrationYear(String registrationYear) {
		this.registrationYear = registrationYear;
	}

	public String getYearsTrading() {
		return yearsTrading;
	}

	public void setYearsTrading(String yearsTrading) {
		this.yearsTrading = yearsTrading;
	}

	public String getInterSetaTransfer() {
		return interSetaTransfer;
	}

	public void setInterSetaTransfer(String interSetaTransfer) {
		this.interSetaTransfer = interSetaTransfer;
	}

	public String getSetaStatus() {
		return setaStatus;
	}

	public void setSetaStatus(String setaStatus) {
		this.setaStatus = setaStatus;
	}

	public String getCoreBusinessDesc() {
		return coreBusinessDesc;
	}

	public void setCoreBusinessDesc(String coreBusinessDesc) {
		this.coreBusinessDesc = coreBusinessDesc;
	}

	public String getSector() {
		return sector;
	}

	public void setSector(String sector) {
		this.sector = sector;
	}

	public String getIsCBO() {
		return isCBO;
	}

	public void setIsCBO(String isCBO) {
		this.isCBO = isCBO;
	}

	public String getIsNGO() {
		return isNGO;
	}

	public void setIsNGO(String isNGO) {
		this.isNGO = isNGO;
	}

	public String getIsStwenty_one() {
		return isStwenty_one;
	}

	public void setIsStwenty_one(String isStwenty_one) {
		this.isStwenty_one = isStwenty_one;
	}

	public String getIsBEE() {
		return isBEE;
	}

	public void setIsBEE(String isBEE) {
		this.isBEE = isBEE;
	}

	public String getSubSector() {
		return subSector;
	}

	public void setSubSector(String subSector) {
		this.subSector = subSector;
	}

	public String getNonLevyNumber() {
		return nonLevyNumber;
	}

	public void setNonLevyNumber(String nonLevyNumber) {
		this.nonLevyNumber = nonLevyNumber;
	}

	public String getUifNumber() {
		return uifNumber;
	}

	public void setUifNumber(String uifNumber) {
		this.uifNumber = uifNumber;
	}

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getPayeNo() {
		return payeNo;
	}

	public void setPayeNo(String payeNo) {
		this.payeNo = payeNo;
	}


	public String getRegCiproNo() {
		return regCiproNo;
	}

	public void setRegCiproNo(String regCiproNo) {
		this.regCiproNo = regCiproNo;
	}

	public String getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(String collegeId) {
		this.collegeId = collegeId;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getPartnershipDesc() {
		return partnershipDesc;
	}

	public void setPartnershipDesc(String partnershipDesc) {
		this.partnershipDesc = partnershipDesc;
	}

	public String getMagisterialDistrict() {
		return magisterialDistrict;
	}

	public void setMagisterialDistrict(String magisterialDistrict) {
		this.magisterialDistrict = magisterialDistrict;
	}

	public String getOrgCatSize() {
		return orgCatSize;
	}

	public void setOrgCatSize(String orgCatSize) {
		this.orgCatSize = orgCatSize;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getOrgCategoryDesc() {
		return orgCategoryDesc;
	}

	public void setOrgCategoryDesc(String orgCategoryDesc) {
		this.orgCategoryDesc = orgCategoryDesc;
	}

	public String getUnionAgreement() {
		return unionAgreement;
	}

	public void setUnionAgreement(String unionAgreement) {
		this.unionAgreement = unionAgreement;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getConfirmSICCode() {
		return confirmSICCode;
	}

	public void setConfirmSICCode(String confirmSICCode) {
		this.confirmSICCode = confirmSICCode;
	}

	public String getCoreBusiness() {
		return coreBusiness;
	}

	public void setCoreBusiness(String coreBusiness) {
		this.coreBusiness = coreBusiness;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getSicCodeChamber() {
		return sicCodeChamber;
	}

	public void setSicCodeChamber(String sicCodeChamber) {
		this.sicCodeChamber = sicCodeChamber;
	}

	public String getWebsiteURL() {
		return websiteURL;
	}

	public void setWebsiteURL(String websiteURL) {
		this.websiteURL = websiteURL;
	}

	public String getGeographicAreaDesc() {
		return geographicAreaDesc;
	}

	public void setGeographicAreaDesc(String geographicAreaDesc) {
		this.geographicAreaDesc = geographicAreaDesc;
	}

	public String getMainBusinessActivityTwo() {
		return mainBusinessActivityTwo;
	}

	public void setMainBusinessActivityTwo(String mainBusinessActivityTwo) {
		this.mainBusinessActivityTwo = mainBusinessActivityTwo;
	}

	public String getSmsChamber() {
		return smsChamber;
	}

	public void setSmsChamber(String smsChamber) {
		this.smsChamber = smsChamber;
	}

	public Boolean getSdlNumberOnSarsEmployerFile() {
		return sdlNumberOnSarsEmployerFile;
	}

	public void setSdlNumberOnSarsEmployerFile(Boolean sdlNumberOnSarsEmployerFile) {
		this.sdlNumberOnSarsEmployerFile = sdlNumberOnSarsEmployerFile;
	}

	public Boolean getMainSdlNumberOnSarsEmployerFile() {
		return mainSdlNumberOnSarsEmployerFile;
	}

	public void setMainSdlNumberOnSarsEmployerFile(Boolean mainSdlNumberOnSarsEmployerFile) {
		this.mainSdlNumberOnSarsEmployerFile = mainSdlNumberOnSarsEmployerFile;
	}

	public String getBbbeeOne() {
		return bbbeeOne;
	}

	public void setBbbeeOne(String bbbeeOne) {
		this.bbbeeOne = bbbeeOne;
	}

	public String getBbbeeTwo() {
		return bbbeeTwo;
	}

	public void setBbbeeTwo(String bbbeeTwo) {
		this.bbbeeTwo = bbbeeTwo;
	}

	public String getBbbeeThree() {
		return bbbeeThree;
	}

	public void setBbbeeThree(String bbbeeThree) {
		this.bbbeeThree = bbbeeThree;
	}

	public String getBbbeeFour() {
		return bbbeeFour;
	}

	public void setBbbeeFour(String bbbeeFour) {
		this.bbbeeFour = bbbeeFour;
	}

	

}
