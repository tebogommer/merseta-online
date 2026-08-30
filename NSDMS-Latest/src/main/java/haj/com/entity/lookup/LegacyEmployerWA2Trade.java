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
 * TO BE DELETED
 * 
 * Excel name: Employer WA 2.xlsx Tab Name: Trade
 */
@Entity
@Table(name = "legacy_employer_wa2_trade")
// id info
@LegacyReportingAnnotation(name = "Total Trade Linked to Employer WA", query = "select count(o) from LegacyEmployerWA2Trade o where o.linkedAccreditationNumberOnProviderAccreditation = true", key = "count")
@LegacyReportingAnnotation(name = "Total Trade not Linked to Employer WA", query = "select count(o) from LegacyEmployerWA2Trade o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total Linked Trade", query = "select count(o) from LegacyEmployerWA2Trade o where o.qualification is not null", key = "count")
@LegacyReportingAnnotation(name = "Total not Linked Trade", query = "select count(o) from LegacyEmployerWA2Trade o where o.qualification is null", key = "count")
// tables
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2Trade o where o.linkedAccreditationNumberOnProviderAccreditation = false", key = "Trade not Linked to Employer WA", returnType = LegacyEmployerWA2Trade.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2Trade o where o.qualification is null", key = "Not Linked Trade", returnType = LegacyEmployerWA2Trade.class, singleResult = false)
public class LegacyEmployerWA2Trade extends AbstractLookup {

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
	 * PN: 1 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class, lookupField = "linkedAccreditationNumberOnProviderAccreditation")
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
	 * PN: 13 OFOCode
	 */
	@CSVAnnotation(name = "OFOCode", className = String.class)
	@Column(name = "ofo_code")
	private String ofoCode;

	/*
	 * PN: 14 OFODescription
	 */
	@CSVAnnotation(name = "OFODescription", className = String.class)
	@Column(name = "ofo_description")
	private String ofoDescription;

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

	/*
	 * PN: 19 WAEmplyerTradeStatus
	 */
	@CSVAnnotation(name = "WAEmplyerTradeStatus", className = String.class)
	@Column(name = "wa_emplyer_trade_status")
	private String waEmplyerTradeStatus;

	/*
	 * PN: 20 TradeDescription
	 */
	@CSVAnnotation(name = "TradeDescription", className = String.class)
	@Column(name = "trade_description")
	private String tradeDescription;

	/*
	 * PN: 21 QualificationCode
	 */
	@CSVAnnotation(name = "QualificationCode", className = String.class, lookupField = "qualification")
	@Column(name = "qualification_code")
	private String qualificationCode;

	@Column(name = "linked_accreditation_number_on_provider_accreditation")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkLegacyEmployerWA2Workplace", paramClass = String.class)
	private Boolean linkedAccreditationNumberOnProviderAccreditation;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findQualification", paramClass = String.class)
	private Qualification qualification;

	@Column(name = "processed")
	private Boolean processed;
	
	@Column(name = "submitted")
	private Boolean submitted;

	public LegacyEmployerWA2Trade() {
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
		LegacyEmployerWA2Trade other = (LegacyEmployerWA2Trade) obj;
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

	public String getWaEmplyerTradeStatus() {
		return waEmplyerTradeStatus;
	}

	public void setWaEmplyerTradeStatus(String waEmplyerTradeStatus) {
		this.waEmplyerTradeStatus = waEmplyerTradeStatus;
	}

	public String getLinkedSdl() {
		return linkedSdl;
	}

	public void setLinkedSdl(String linkedSdl) {
		this.linkedSdl = linkedSdl;
	}

	public String getTradeDescription() {
		return tradeDescription;
	}

	public void setTradeDescription(String tradeDescription) {
		this.tradeDescription = tradeDescription;
	}

	public String getQualificationCode() {
		return qualificationCode;
	}

	public void setQualificationCode(String qualificationCode) {
		this.qualificationCode = qualificationCode;
	}

	public Qualification getQualification() {
		return qualification;
	}

	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
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