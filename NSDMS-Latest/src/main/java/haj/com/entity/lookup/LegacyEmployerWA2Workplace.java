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
import haj.com.entity.SDFCompany;
import haj.com.framework.AbstractLookup;

/**
 * TO BE DELETED
 * 
 * Excel name: Employer WA 2.xlsx Tab Name: WorkPlace
 */
@Entity
@Table(name = "legacy_employer_wa2_workplace")
//qualification info
@LegacyReportingAnnotation(name = "Total Sites Linked to SDL Number", query = "select count(o) from LegacyEmployerWA2Workplace o where o.legacyOrganisationSites = true", key = "count")
@LegacyReportingAnnotation(name = "Total Sites not Linked to SDL Number", query = "select count(o) from LegacyEmployerWA2Workplace o where o.legacyOrganisationSites = false", key = "count")
//accreditation info
@LegacyReportingAnnotation(name = "Total Linked SDL on SARS File", query = "select count(o) from LegacyEmployerWA2Workplace o where o.checkCountByRefNumber = true", key = "count")
@LegacyReportingAnnotation(name = "Total Linked SDL not on SARS File", query = "select count(o) from LegacyEmployerWA2Workplace o where o.checkCountByRefNumber = false", key = "count")
//id info
@LegacyReportingAnnotation(name = "Total Non Levy Paying Orgs Linked to SDL Number", query = "select count(o) from LegacyEmployerWA2Workplace o where o.legacyOrganisationNonLevyPaying = true", key = "count")
@LegacyReportingAnnotation(name = "Total Non Levy Paying Orgs not Linked to SDL Number", query = "select count(o) from LegacyEmployerWA2Workplace o where o.legacyOrganisationNonLevyPaying = false", key = "count")
//tables
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2Workplace o where o.legacyOrganisationSites = false", key = "Sites not Linked to SDL Number", returnType = LegacyEmployerWA2Workplace.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2Workplace o where o.checkCountByRefNumber = false", key = "Linked SDL not on SARS File", returnType = LegacyEmployerWA2Workplace.class, singleResult = false)
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2Workplace o where o.legacyOrganisationNonLevyPaying = false", key = "Non Levy Paying Orgs not Linked to SDL Number", returnType = LegacyEmployerWA2Workplace.class, singleResult = false)
//accreditationStartDate accreditationEndDate
//date info
@LegacyReportingAnnotation(name = "Total Employer WA with Valid End Date", query = "select count(o) from LegacyEmployerWA2Workplace o where date(o.accreditationEndDate) > date(o.accreditationStartDate)", key = "count")
@LegacyReportingAnnotation(name = "Total Employer WA with Invalid End Date", query = "select count(o) from LegacyEmployerWA2Workplace o where date(o.accreditationEndDate) < date(o.accreditationStartDate) or date(o.accreditationEndDate) = date(o.accreditationStartDate) or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2Workplace o where date(o.accreditationEndDate) < date(o.accreditationStartDate)", key = "Employer WA with Invalid End Date", returnType = LegacyEmployerWA2Workplace.class, singleResult = false)
//expired info
@LegacyReportingAnnotation(name = "Total Employer WA with Valid Status", query = "select count(o) from LegacyEmployerWA2Workplace o where date(o.accreditationEndDate) < date(NOW()) and o.waEmployerStatus = 'Expired'", key = "count")
@LegacyReportingAnnotation(name = "Total Employer WA with Invalid Status", query = "select count(o) from LegacyEmployerWA2Workplace o where date(o.accreditationEndDate) < date(NOW()) and o.waEmployerStatus <> 'Expired'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2Workplace o where date(o.accreditationEndDate) < date(NOW()) and o.waEmployerStatus <> 'Expired'", key = "Employer WA with Invalid Status", returnType = LegacyEmployerWA2Workplace.class, singleResult = false)
//accreditation info
@LegacyReportingAnnotation(name = "Total Employer WA with Start and End Date", query = "select count(o) from LegacyEmployerWA2Workplace o where (o.accreditationEndDate is not null and o.accreditationStartDate is not null) and o.accreditationEndDate <> 'NULL' and o.accreditationStartDate <> 'NULL'", key = "count")
@LegacyReportingAnnotation(name = "Total Employer WA without Start or End Date", query = "select count(o) from LegacyEmployerWA2Workplace o where o.accreditationEndDate is null or o.accreditationStartDate is null or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "count")
@LegacyReportingAnnotation(query = "select o from LegacyEmployerWA2Workplace o where o.accreditationEndDate is null or o.accreditationStartDate is null or o.accreditationEndDate = 'NULL' or o.accreditationStartDate = 'NULL'", key = "Employer WA with Start or End Date", returnType = LegacyEmployerWA2Workplace.class, singleResult = false) //
public class LegacyEmployerWA2Workplace extends AbstractLookup {

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
	 * PN: 1 WAEmployerId
	 */
	@CSVAnnotation(name = "WAEmployerId", className = String.class)
	@Column(name = "wa_employer_id")
	private String waEmployerId;

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
	@Column(name = "organisation_name_legal")
	private String organisationNameLegal;

	/*
	 * PN: 5 OrganisationNameTrade
	 */
	@CSVAnnotation(name = "OrganisationNameTrade", className = String.class)
	@Column(name = "organisation_name_trade")
	private String organisationNameTrade;

	/*
	 * PN: 6 WAEmployerStatus
	 */
	@CSVAnnotation(name = "WAEmployerStatus", className = String.class)
	@Column(name = "wa_employer_status")
	private String waEmployerStatus;

	/*
	 * PN: 7 ApplicationRecDate
	 */
	@CSVAnnotation(name = "ApplicationRecDate", className = String.class)
	@Column(name = "application_rec_date")
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
	 * PN: 10 DateCreated
	 */
	@CSVAnnotation(name = "DateCreated", className = String.class)
	@Column(name = "date_created")
	private String dateCreated;

	/*
	 * PN: 11 LastDateChanged
	 */
	@CSVAnnotation(name = "LastDateChanged", className = String.class)
	@Column(name = "last_date_changed")
	private String lastDateChanged;

	@Fetch(FetchMode.JOIN)
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "qualification_id")
	private Qualification qualification;

	@Column(name = "processed")
	private Boolean processed;
	
	@Column(name = "submitted")
	private Boolean submitted;
	
	@Transient
	private List<LegacyEmployerWA2Learnership> legacyEmployerWA2Learnership;
	
	@Transient
	private List<LegacyEmployerWA2Qualification> legacyEmployerWA2Qualification;
	
	
	@Transient
	private List<LegacyEmployerWA2SkillsProgramme> legacyEmployerWA2SkillsProgramme;
	
	@Transient
	private List<LegacyEmployerWA2Trade> legacyEmployerWA2Trade;
	
	@Transient
	private List<LegacyEmployerWA2UnitStandard> legacyEmployerWA2UnitStandard;

	public LegacyEmployerWA2Workplace() {
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
		LegacyEmployerWA2Workplace other = (LegacyEmployerWA2Workplace) obj;
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

	public String getWaEmployerId() {
		return waEmployerId;
	}

	public void setWaEmployerId(String waEmployerId) {
		this.waEmployerId = waEmployerId;
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

	public String getWaEmployerStatus() {
		return waEmployerStatus;
	}

	public void setWaEmployerStatus(String waEmployerStatus) {
		this.waEmployerStatus = waEmployerStatus;
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

	public String getLastDateChanged() {
		return lastDateChanged;
	}

	public void setLastDateChanged(String lastDateChanged) {
		this.lastDateChanged = lastDateChanged;
	}

	public String getLinkedSdl() {
		return linkedSdl;
	}

	public void setLinkedSdl(String linkedSdl) {
		this.linkedSdl = linkedSdl;
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

	public List<LegacyEmployerWA2Learnership> getLegacyEmployerWA2Learnership() {
		return legacyEmployerWA2Learnership;
	}

	public void setLegacyEmployerWA2Learnership(List<LegacyEmployerWA2Learnership> legacyEmployerWA2Learnership) {
		this.legacyEmployerWA2Learnership = legacyEmployerWA2Learnership;
	}

	public List<LegacyEmployerWA2Qualification> getLegacyEmployerWA2Qualification() {
		return legacyEmployerWA2Qualification;
	}

	public void setLegacyEmployerWA2Qualification(List<LegacyEmployerWA2Qualification> legacyEmployerWA2Qualification) {
		this.legacyEmployerWA2Qualification = legacyEmployerWA2Qualification;
	}

	public List<LegacyEmployerWA2SkillsProgramme> getLegacyEmployerWA2SkillsProgramme() {
		return legacyEmployerWA2SkillsProgramme;
	}

	public void setLegacyEmployerWA2SkillsProgramme(
			List<LegacyEmployerWA2SkillsProgramme> legacyEmployerWA2SkillsProgramme) {
		this.legacyEmployerWA2SkillsProgramme = legacyEmployerWA2SkillsProgramme;
	}

	public List<LegacyEmployerWA2Trade> getLegacyEmployerWA2Trade() {
		return legacyEmployerWA2Trade;
	}

	public void setLegacyEmployerWA2Trade(List<LegacyEmployerWA2Trade> legacyEmployerWA2Trade) {
		this.legacyEmployerWA2Trade = legacyEmployerWA2Trade;
	}

	public List<LegacyEmployerWA2UnitStandard> getLegacyEmployerWA2UnitStandard() {
		return legacyEmployerWA2UnitStandard;
	}

	public void setLegacyEmployerWA2UnitStandard(List<LegacyEmployerWA2UnitStandard> legacyEmployerWA2UnitStandard) {
		this.legacyEmployerWA2UnitStandard = legacyEmployerWA2UnitStandard;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}
}