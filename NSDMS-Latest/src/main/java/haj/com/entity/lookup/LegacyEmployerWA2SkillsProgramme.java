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
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.framework.AbstractLookup;

/**
 * Excel name: Employer WA 2.xlsx Tab Name: Skills Programme
 */
@Entity
@Table(name = "legacy_employer_wa2_skills_programme")
// id info
@LegacyReportingAnnotation(name = "Total Skills Program Linked to Employer WA", query = "select count(o) from LegacyEmployerWA2SkillsProgramme o where o.linkedAccreditationNumberOnProviderAccreditation = true", key = "count")
@LegacyReportingAnnotation(name = "Total Skills Program not Linked to Employer WA", query = "select count(o) from LegacyEmployerWA2SkillsProgramme o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total Linked Skills Program", query = "select count(o) from LegacyEmployerWA2SkillsProgramme o where o.skillsProgram is not null", key = "count")
@LegacyReportingAnnotation(name = "Total not Linked Skills Program", query = "select count(o) from LegacyEmployerWA2SkillsProgramme o where o.skillsProgram is null", key = "count")
// tables
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2SkillsProgramme o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "Learnership not Linked to Employer WA", returnType = LegacyEmployerWA2SkillsProgramme.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2SkillsProgramme o where o.skillsProgram is null", key = "Not Linked Skills Program", returnType = LegacyEmployerWA2SkillsProgramme.class, singleResult = false)
public class LegacyEmployerWA2SkillsProgramme extends AbstractLookup {

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
	 * PN: 1 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class, lookupField = "linkedAccreditationNumberOnProviderAccreditation")
	@Column(name = "sdl_no")
	private String sdlNo;

	/*
	 * PN: 2 Linked_SDL
	 */
	@CSVAnnotation(name = "Linked_SDl", className = String.class)
	@Column(name = "linked_sdl")
	private String linkedSdl;

	/*
	 * PN: 3 Linked_SDL_Number
	 */
	@CSVAnnotation(name = "Linked_SDL_Number", className = String.class)
	@Column(name = "linked_sdl_number")
	private String linkedSdlNumber;

	/*
	 * PN: 4 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 5 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 6 Phone
	 */
	@CSVAnnotation(name = "Phone", className = String.class)
	@Column(name = "phone")
	private String phone;

	/*
	 * PN: 7 Fax
	 */
	@CSVAnnotation(name = "Fax", className = String.class)
	@Column(name = "fax")
	private String fax;

	/*
	 * PN: 8 EmailAddress
	 */
	@CSVAnnotation(name = "EmailAddress", className = String.class)
	@Column(name = "email_address")
	private String emailAddress;

	/*
	 * PN: 9 CompanyRegistrationNo
	 */
	@CSVAnnotation(name = "CompanyRegistrationNo", className = String.class)
	@Column(name = "company_registration_no")
	private String companyRegistrationNo;

	/*
	 * PN: 10 SicCode
	 */
	@CSVAnnotation(name = "SicCode", className = String.class)
	@Column(name = "sic_code")
	private String sicCode;

	/*
	 * PN: 11 SicCodeDescription
	 */
	@CSVAnnotation(name = "SicCodeDescription", className = String.class)
	@Column(name = "sic_code_description")
	private String sicCodeDescription;

	/*
	 * PN: 12 OrgCatSize
	 */
	@CSVAnnotation(name = "OrgCatSize", className = String.class)
	@Column(name = "org_cat_size")
	private String orgCatSize;

	/*
	 * PN: 13 ChamberDescription
	 */
	@CSVAnnotation(name = "ChamberDescription", className = String.class)
	@Column(name = "chamber_description")
	private String chamberDescription;

	/*
	 * PN: 14 sProgrammeCode
	 */
	@CSVAnnotation(name = "sProgrammeCode", className = String.class, lookupField = "skillsProgram")
	@Column(name = "s_programme_code")
	private String sProgrammeCode;

	/*
	 * PN: 15 OFOCode
	 */
	@CSVAnnotation(name = "OFOCode", className = String.class)
	@Column(name = "ofo_code")
	private String ofoCode;

	/*
	 * PN: 16 QualificationID
	 */
	@CSVAnnotation(name = "QualificationID", className = String.class)
	@Column(name = "qualification_id")
	private String qualificationId;

	/*
	 * PN: 17 sProgrammeDesc
	 */
	@CSVAnnotation(name = "sProgrammeDesc", className = String.class)
	@Column(name = "s_programme_desc")
	private String sProgrammeDesc;

	/*
	 * PN: 18 ApplicationRecDate
	 */
	@CSVAnnotation(name = "ApplicationRecDate", className = String.class)
	@Column(name = "application_rec_date")
	private String applicationRecDate;

	/*
	 * PN: 19 AccreditationStartDate
	 */
	@CSVAnnotation(name = "AccreditationStartDate", className = String.class)
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;

	/*
	 * PN: 20 AccreditationEndDate
	 */
	@CSVAnnotation(name = "AccreditationEndDate", className = String.class)
	@Column(name = "accreditation_end_date")
	private String accreditationEndDate;

	/*
	 * PN: 21 DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_Created")
	private String dateCreated;

	/*
	 * PN: 22 WAEmployerStatus
	 */
	@CSVAnnotation(name = "WAEmployerStatus", className = String.class)
	@Column(name = "wa_employer_status")
	private String waEmployerStatus;
	
	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "skills_program_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findSkillsProgram", paramClass = String.class)
	private SkillsProgram skillsProgram;
	
	@Column(name = "linked_accreditation_number_on_provider_accreditation")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyEmployerWA2Workplace", paramClass = String.class)
	private Boolean linkedAccreditationNumberOnProviderAccreditation;

	@Column(name = "processed")
	private Boolean processed;
	
	@Column(name = "submitted")
	private Boolean submitted;
	
	public LegacyEmployerWA2SkillsProgramme() {
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
		LegacyEmployerWA2SkillsProgramme other = (LegacyEmployerWA2SkillsProgramme) obj;
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

	public String getsProgrammeCode() {
		return sProgrammeCode;
	}

	public void setsProgrammeCode(String sProgrammeCode) {
		this.sProgrammeCode = sProgrammeCode;
	}

	public String getOfoCode() {
		return ofoCode;
	}

	public void setOfoCode(String ofoCode) {
		this.ofoCode = ofoCode;
	}

	public String getsProgrammeDesc() {
		return sProgrammeDesc;
	}

	public void setsProgrammeDesc(String sProgrammeDesc) {
		this.sProgrammeDesc = sProgrammeDesc;
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

	public String getAccreditationEndDate() {
		return accreditationEndDate;
	}

	public void setAccreditationEndDate(String accreditationEndDate) {
		this.accreditationEndDate = accreditationEndDate;
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

	public String getQualificationId() {
		return qualificationId;
	}

	public void setQualificationId(String qualificationId) {
		this.qualificationId = qualificationId;
	}

	public String getLinkedSdlNumber() {
		return linkedSdlNumber;
	}

	public void setLinkedSdlNumber(String linkedSdlNumber) {
		this.linkedSdlNumber = linkedSdlNumber;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

	public SkillsProgram getSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(SkillsProgram skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public Boolean getLinkedAccreditationNumberOnProviderAccreditation() {
		return linkedAccreditationNumberOnProviderAccreditation;
	}

	public void setLinkedAccreditationNumberOnProviderAccreditation(Boolean linkedAccreditationNumberOnProviderAccreditation) {
		this.linkedAccreditationNumberOnProviderAccreditation = linkedAccreditationNumberOnProviderAccreditation;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}
}