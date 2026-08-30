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

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import haj.com.annotations.CSVAnnotation;
import haj.com.framework.AbstractLookup;

/**
 * Excel name: Employer WA 2.xlsx 
 * Tab Name: Unit Standard
 */
@Entity
@Table(name = "Legacy_employer_wa2_unit_standard")
public class LegacyEmployerWA2UnitStandard extends AbstractLookup {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

//	@CSVAnnotation(name = "Name of row on excel", className = String.class)
//	@Column(name = "keep to standard")
//	private String keep to standard;

	/*
	 * Filed Sizes: Book: @Column(name = "dttc_id" , columnDefinition = "LONGTEXT")
	 * 500 or so chataers @Column(name = "dttc_id" , length = 500)
	 */

	/*
	 * PN: 1 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class)
	@Column(name = "sdl_no")
	private String sdlNo;
	
	/*
	 * PN: 2 Linked_SDL 
	 */
	@CSVAnnotation(name = "Linked_SDL", className = String.class)
	@Column(name = "linked_sdl")
	private String linkedSdl; 
	
	/*
	 * PN: 3 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 4 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 5 Phone
	 */
	@CSVAnnotation(name = "Phone", className = String.class)
	@Column(name = "phone")
	private String phone;

	/*
	 * PN: 6 Fax
	 */
	@CSVAnnotation(name = "Fax", className = String.class)
	@Column(name = "fax")
	private String fax;

	/*
	 * PN: 7 EmailAddress
	 */
	@CSVAnnotation(name = "EmailAddress", className = String.class)
	@Column(name = "email_address")
	private String emailAddress;

	/*
	 * PN: 8 CompanyRegistrationNo
	 */
	@CSVAnnotation(name = "CompanyRegistrationNo", className = String.class)
	@Column(name = "company_registration_no")
	private String companyRegistrationNo;

	/*
	 * PN: 9 SicCode
	 */
	@CSVAnnotation(name = "SicCode", className = String.class)
	@Column(name = "sic_code")
	private String sicCode;

	/*
	 * PN: 10 SicCodeDescription
	 */
	@CSVAnnotation(name = "SicCodeDescription", className = String.class)
	@Column(name = "sic_code_description")
	private String sicCodeDescription;

	/*
	 * PN: 11 OrgCatSize
	 */
	@CSVAnnotation(name = "OrgCatSize", className = String.class)
	@Column(name = "org_cat_size")
	private String orgCatSize;

	/*
	 * PN: 12 ChamberDescription
	 */
	@CSVAnnotation(name = "ChamberDescription", className = String.class)
	@Column(name = "chamber_description")
	private String chamberDescription;
	
	/*
	 * PN: 13 UnitStdCode
	 */
	@CSVAnnotation(name = "UnitStdCode", className = String.class)
	@Column(name = "unit_std_code")
	private String unitStdCode;
	
	/*
	 * PN: 14 UnitStdDesc
	 */
	@CSVAnnotation(name = "UnitStdDesc", className = String.class)
	@Column(name = "unit_std_desc")
	private String unitStdDesc;
	
	/*
	 * PN: 15 ApplicationRecDate
	 */
	@CSVAnnotation(name = "ApplicationRecDate", className = String.class)
	@Column(name = "application_rec_date")
	private String applicationRecDate;
	
	/*
	 * PN: 16 AccreditationStartDate
	 */
	@CSVAnnotation(name = "AccreditationStartDate", className = String.class)
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;
	
	/*
	 * PN: 17 DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_Created")
	private String dateCreated;
	
	/*
	 * PN: 18 WAEmployerStatus
	 */
	@CSVAnnotation(name = "WAEmployerStatus", className = String.class)
	@Column(name = "wa_employer_status")
	private String waEmployerStatus;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "legacy_organisation_sites_id")
	private LegacyOrganisationSites legacyOrganisationSites;
	
	
	@Column(name = "processed")
	private Boolean processed;
	
	@Column(name = "submitted")
	private Boolean submitted;
	
	public LegacyEmployerWA2UnitStandard() {
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LegacyEmployerWA2UnitStandard other = (LegacyEmployerWA2UnitStandard) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
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
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
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

	public String getCompanyRegistrationNo() {
		return companyRegistrationNo;
	}

	public void setCompanyRegistrationNo(String companyRegistrationNo) {
		this.companyRegistrationNo = companyRegistrationNo;
	}

	public String getSicCode() {
		return sicCode;
	}

	public void setSicCode(String sicCode) {
		this.sicCode = sicCode;
	}

	public String getSicCodeDescription() {
		return sicCodeDescription;
	}

	public void setSicCodeDescription(String sicCodeDescription) {
		this.sicCodeDescription = sicCodeDescription;
	}

	public String getOrgCatSize() {
		return orgCatSize;
	}

	public void setOrgCatSize(String orgCatSize) {
		this.orgCatSize = orgCatSize;
	}

	public String getChamberDescription() {
		return chamberDescription;
	}

	public void setChamberDescription(String chamberDescription) {
		this.chamberDescription = chamberDescription;
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

	public String getApplicationRecDate() {
		return applicationRecDate;
	}

	public void setApplicationRecDate(String applicationRecDate) {
		this.applicationRecDate = applicationRecDate;
	}

	public String getAccreditationStartDate() {
		return accreditationStartDate;
	}

	public void setAccreditationStartDate(String accreditationStartDate) {
		this.accreditationStartDate = accreditationStartDate;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getWaEmployerStatus() {
		return waEmployerStatus;
	}

	public void setWaEmployerStatus(String waEmployerStatus) {
		this.waEmployerStatus = waEmployerStatus;
	}

	public String getLinkedSdl() {
		return linkedSdl;
	}

	public void setLinkedSdl(String linkedSdl) {
		this.linkedSdl = linkedSdl;
	}

	public LegacyOrganisationSites getLegacyOrganisationSites() {
		return legacyOrganisationSites;
	}

	public void setLegacyOrganisationSites(LegacyOrganisationSites legacyOrganisationSites) {
		this.legacyOrganisationSites = legacyOrganisationSites;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}
}