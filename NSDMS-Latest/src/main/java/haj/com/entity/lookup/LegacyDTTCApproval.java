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

import haj.com.annotations.CSVAnnotation;
import haj.com.annotations.CSVLookupAnnotation;
import haj.com.annotations.LegacyReportingAnnotation;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.Company;
import haj.com.framework.AbstractLookup;

/**
 * Excel name: DTTC.xlsx Tab Name: Approval
 */
@Entity
@Table(name = "legacy_dttc_approval")
// qualification info
@LegacyReportingAnnotation(name = "Total Sites Linked to SDL Number", query = "select count(o) from LegacyDTTCApproval o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Sites not Linked to SDL Number", query = "select count(o) from LegacyDTTCApproval o where o.legacyOrganisationSites = false", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total Linked SDL on SARS File", query = "select count(o) from LegacyDTTCApproval o where o.checkCountByRefNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Linked SDL not on SARS File", query = "select count(o) from LegacyDTTCApproval o where o.checkCountByRefNumber = false", key = "count")
// id info
@LegacyReportingAnnotation(name = "Total Non Levy Paying Orgs Linked to SDL Number", query = "select count(o) from LegacyDTTCApproval o where o.legacyOrganisationNonLevyPaying = true", key = "count")
@LegacyReportingAnnotation(name = "Total Non Levy Paying Orgs not Linked to SDL Number", query = "select count(o) from LegacyDTTCApproval o where o.legacyOrganisationNonLevyPaying = false", key = "count")
// accreditation info
@LegacyReportingAnnotation(name = "Total DTTC with Accreditation Num", query = "select count(o) from LegacyDTTCApproval o where o.approvalNumber is not null", key = "count")
@LegacyReportingAnnotation(name = "Total DTTC without Accreditation Num", query = "select count(o) from LegacyDTTCApproval o where o.approvalNumber is null", key = "count")
// tables
@LegacyReportingAnnotation(query = "select o from LegacyDTTCApproval o where o.legacyOrganisationSites = false", key = "Sites not Linked to SDL Number", returnType = LegacyDTTCApproval.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyDTTCApproval o where o.checkCountByRefNumber = false", key = "Linked SDL not on SARS File", returnType = LegacyDTTCApproval.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyDTTCApproval o where o.legacyOrganisationNonLevyPaying = false", key = "Non Levy Paying Orgs not Linked to SDL Number", returnType = LegacyDTTCApproval.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyDTTCApproval o where o.approvalNumber is null", key = "DTTC without Accreditation Num", returnType = LegacyDTTCApproval.class, singleResult = false)
// accreditationStartDate accreditationEndDate
// date info
@LegacyReportingAnnotation(name = "Total DTTC with Valid End Date", query = "select count(o) from LegacyDTTCApproval o where date(o.accreditationEndDate) > date(o.accreditationStartDate)", key = "count")
@LegacyReportingAnnotation(name = "Total DTTC with Invalid End Date", query = "select count(o) from LegacyDTTCApproval o where date(o.accreditationEndDate) < date(o.accreditationStartDate) or date(o.accreditationEndDate) = date(o.accreditationStartDate) or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyDTTCApproval o where date(o.accreditationEndDate) < date(o.accreditationStartDate)", key = "DTTC with Invalid End Date", returnType = LegacyDTTCApproval.class, singleResult = false)
// expired info
@LegacyReportingAnnotation(name = "Total DTTC with Valid Status", query = "select count(o) from LegacyDTTCApproval o where date(o.accreditationEndDate) < date(NOW()) and o.dttcStatus = 'Expired'", key = "count")
@LegacyReportingAnnotation(name = "Total DTTC with Invalid Status", query = "select count(o) from LegacyDTTCApproval o where date(o.accreditationEndDate) < date(NOW()) and o.dttcStatus <> 'Expired'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyDTTCApproval o where date(o.accreditationEndDate) < date(NOW()) and o.dttcStatus <> 'Expired'", key = "DTTC with Invalid Status", returnType = LegacyDTTCApproval.class, singleResult = false)
// accreditation info
@LegacyReportingAnnotation(name = "Total DTTC with Start and End Date", query = "select count(o) from LegacyDTTCApproval o where (o.accreditationEndDate is not null and o.accreditationStartDate is not null) and o.accreditationEndDate <> 'NULL' and o.accreditationStartDate <> 'NULL'", key = "count")
@LegacyReportingAnnotation(name = "Total DTTC without Start or End Date", query = "select count(o) from LegacyDTTCApproval o where o.accreditationEndDate is null or o.accreditationStartDate is null or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyDTTCApproval o where o.accreditationEndDate is null or o.accreditationStartDate is null or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "DTTC with Start or End Date", returnType = LegacyDTTCApproval.class, singleResult = false) //
public class LegacyDTTCApproval extends AbstractLookup {

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
	 * PN: 1 DTTCId
	 */
	@CSVAnnotation(name = "DTTCId", className = String.class)
	@Column(name = "dttc_id")
	private String dttcId;

	/*
	 * PN: 2 SDLNo
	 */
	@CSVAnnotation(name = "SDLNo", className = String.class, lookupField = "legacyOrganisationSites")
	@Column(name = "sdl_no")
	private String sdlNo;

	@Column(name = "legacy_organisation_sites")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLegacyOrganisationSites", paramClass = String.class)
	private Boolean legacyOrganisationSites;

	/*
	 * PN: 3 Linked_SDL
	 */
	@CSVAnnotation(name = "Linked_SDL", className = String.class, lookupField = "checkCountByRefNumber")
	@Column(name = "linked_sdl")
	private String linkedSdl;

	@CSVAnnotation(name = "Linked_SDL", className = String.class, lookupField = "legacyOrganisationNonLevyPaying")
	@Transient
	private String linkedSdlTransient;

	@Column(name = "checkCount_by_ref_number")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "checkCountByRefNumber", paramClass = String.class)
	private Boolean checkCountByRefNumber;

	@Column(name = "legacy_organisation_non_levy_paying")
	@CSVLookupAnnotation(className = ResolveByCodeLookupDAO.class, method = "findLegacyOrganisationNonLevyPaying", paramClass = String.class)
	private Boolean legacyOrganisationNonLevyPaying;


	/*
	 * PN: 4 OrganisationNameLegal
	 */
	@CSVAnnotation(name = "OrganisationNameLegal", className = String.class)
	@Column(name = "organisation_name_legal", length = 500)
	private String organisationNameLegal;

	/*
	 * PN: 5 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "oprganisation_name_trade", length = 500)
	private String organisationNameTrade;

	/*
	 * PN: 6 DTTCStatus
	 */
	@CSVAnnotation(name = "DTTCStatus", className = String.class)
	@Column(name = "dttc_status")
	private String dttcStatus;

	/*
	 * PN: 7 ApplicationRecDate
	 */
	@CSVAnnotation(name = "ApplicationRecDate", className = String.class)
	@Column(name = "application_recDate")
	private String applicationRecDate;

	/*
	 * PN: 8 AccreditationStartDate
	 */
	@CSVAnnotation(name = "AccreditationStartDate", className = String.class)
	@Column(name = "accreditation_start_date")
	private String accreditationStartDate;

	/*
	 * PN: 9 AccreditationEndDate
	 */
	@CSVAnnotation(name = "AccreditationEndDate", className = String.class)
	@Column(name = "accreditation_end_date")
	private String accreditationEndDate;

	/*
	 * PN: 10 NoOfEmployees
	 */
	@CSVAnnotation(name = "NoOfEmployees", className = String.class)
	@Column(name = "no_of_employees")
	private String noOfEmployees;

	/*
	 * PN: 11 LastDateChanged
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed")
	private String lastDateChanged;

	/*
	 * PN: 12 DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_created")
	private String dateCreated;

	/*
	 * PN: 13 sRefNo
	 */
	@CSVAnnotation(name = "sRefNo", className = String.class)
	@Column(name = "s_ref_no")
	private String sRefNo;

	/*
	 * PN: 13 ApprovalNumber
	 */
	@CSVAnnotation(name = "ApprovalNumber", className = String.class)
	@Column(name = "approval_number")
	private String approvalNumber;

	@Column(name = "processed")
	private Boolean processed;

	public LegacyDTTCApproval() {
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
		LegacyDTTCApproval other = (LegacyDTTCApproval) obj;
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

	public String getDttcId() {
		return dttcId;
	}

	public void setDttcId(String dttcId) {
		this.dttcId = dttcId;
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

	public String getDttcStatus() {
		return dttcStatus;
	}

	public void setDttcStatus(String dttcStatus) {
		this.dttcStatus = dttcStatus;
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

	public String getNoOfEmployees() {
		return noOfEmployees;
	}

	public void setNoOfEmployees(String noOfEmployees) {
		this.noOfEmployees = noOfEmployees;
	}

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getsRefNo() {
		return sRefNo;
	}

	public void setsRefNo(String sRefNo) {
		this.sRefNo = sRefNo;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getLinkedSdl() {
		return linkedSdl;
	}

	public void setLinkedSdl(String linkedSdl) {
		this.linkedSdl = linkedSdl;
	}

	public Boolean getProcessed() {
		return processed;
	}

	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}

}
